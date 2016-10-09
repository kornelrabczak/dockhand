package com.thecookiezen.presentation;

import com.thecookiezen.infrastructure.docker.StatisticsSSEListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class StatisticsSubsriber {

    private Map<String, StatisticsSSEListener> listenerMap = new ConcurrentHashMap<>();

    @RequestMapping("/statistics/subscribe/{containerId}")
    public SseEmitter subscribeUpdates(@PathVariable String containerId) {
        if (!listenerMap.containsKey(containerId)) {
            StatisticsSSEListener listener = new StatisticsSSEListener(containerId);
            listenerMap.put(containerId, listener);
        }

        StatisticsSSEListener statisticsSSEListener = listenerMap.get(containerId);
        return statisticsSSEListener.createNewEmiter();
    }
}
