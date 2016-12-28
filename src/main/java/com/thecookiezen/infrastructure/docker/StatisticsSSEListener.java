package com.thecookiezen.infrastructure.docker;

import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.model.Statistics;
import com.github.dockerjava.core.async.ResultCallbackTemplate;
import com.google.common.collect.Lists;
import com.thecookiezen.bussiness.cluster.boundary.ContainerFetcher;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@CommonsLog
public class StatisticsSSEListener {

    private final List<SseEmitter> emitters = Lists.newCopyOnWriteArrayList();
    private final String containerId;
    private final ContainerFetcher nodeInstance;

    public StatisticsSSEListener(String containerId, ContainerFetcher nodeInstance) {
        this.containerId = containerId;
        this.nodeInstance = nodeInstance;
    }

    public SseEmitter createNewEmiter() {
        if (!nodeInstance.isContainerRunning(containerId)) {
            throw new IllegalStateException("Container not running.");
        }

        SseEmitter sseEmitter = new SseEmitter();
        emitters.add(sseEmitter);
        nodeInstance.statsCmd(containerId).exec(new ResultCallbackTemplate<ResultCallback<Statistics>, Statistics>() {
            @Override
            public void onNext(Statistics statistics) {
                emitters.removeIf(e -> {
                    try {
                        e.send(new StatisticsLite(statistics));
                    } catch (Exception ex) {
                        log.error("Error during processing statistics event.", ex);
                        return true;
                    }
                    return false;
                });

                if (emitters.isEmpty())
                    try {
                        close();
                    } catch (IOException e) {
                        log.error("Error during closing stream.", e);
                    }
            }
        });
        return sseEmitter;
    }
}
