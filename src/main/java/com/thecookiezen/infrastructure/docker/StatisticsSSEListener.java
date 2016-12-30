package com.thecookiezen.infrastructure.docker;

import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.model.Statistics;
import com.github.dockerjava.core.async.ResultCallbackTemplate;
import com.thecookiezen.bussiness.cluster.boundary.ContainerFetcher;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@CommonsLog
public class StatisticsSSEListener {

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

        final SseEmitter sseEmitter = new SseEmitter();
        ResultCallbackTemplate<ResultCallback<Statistics>, Statistics> resultCallback = new ResultCallbackTemplate<ResultCallback<Statistics>, Statistics>() {
            @Override
            public void onNext(Statistics statistics) {
                try {
                    sseEmitter.send(new StatisticsLite(statistics));
                } catch (Exception ex) {
                    throw new IllegalStateException("Error during stream closing.", ex);
                }
            }
        };
        nodeInstance.statsCmd(containerId).exec(resultCallback);
        sseEmitter.onTimeout(resultCallback::onComplete);
        sseEmitter.onCompletion(resultCallback::onComplete);
        return sseEmitter;
    }
}
