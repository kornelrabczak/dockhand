package com.thecookiezen.infrastructure.docker;

import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import com.thecookiezen.bussiness.cluster.boundary.ContainerFetcher;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@CommonsLog
public class LogsSSEListener {

    private final String containerId;
    private final ContainerFetcher nodeInstance;

    public LogsSSEListener(String containerId, ContainerFetcher nodeInstance) {
        this.containerId = containerId;
        this.nodeInstance = nodeInstance;
    }

    public SseEmitter createNewEmiter() {
        if (!nodeInstance.isContainerRunning(containerId)) {
            throw new IllegalStateException("Container not running.");
        }

        final SseEmitter sseEmitter = new SseEmitter();
        nodeInstance.logs(containerId).exec(new LogContainerResultCallback(){
            @Override
            public void onNext(Frame item) {
                try {
                    sseEmitter.send(new String(item.getPayload()));
                } catch (IOException e) {
                    throw new IllegalStateException("Error during stream closing.");
                }
            }
        });
        return sseEmitter;
    }
}
