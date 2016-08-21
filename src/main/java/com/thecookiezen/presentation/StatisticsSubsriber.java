package com.thecookiezen.presentation;

import com.thecookiezen.bussiness.cluster.boundary.StatisticsProvider;
import com.thecookiezen.infrastructure.docker.StatisticsSSEListener;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StatisticsSubsriber {

    private final StatisticsProvider statisticsProvider;

    private Map<String, StatisticsSSEListener> listenerMap = new ConcurrentHashMap<>();

    @RequestMapping("/statistics/subscribe/{containerId}")
    public SseEmitter subscribeUpdates(@PathVariable String containerId) {
        if (!listenerMap.containsKey(containerId)) {
            StatisticsSSEListener listener = new StatisticsSSEListener(containerId, statisticsProvider);
            listenerMap.put(containerId, listener);
        }

        StatisticsSSEListener statisticsSSEListener = listenerMap.get(containerId);
        return statisticsSSEListener.createNewEmiter();
    }
}
