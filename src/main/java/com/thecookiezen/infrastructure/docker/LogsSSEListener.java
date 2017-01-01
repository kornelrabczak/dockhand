package com.thecookiezen.infrastructure.docker;

import com.thecookiezen.bussiness.cluster.boundary.ContainerFetcher;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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

        nodeInstance.logs(containerId)
                .window(200, TimeUnit.MILLISECONDS)
                .flatMap(window -> window.reduce("", (a,b) -> a + b))
                .subscribe(
                        value -> {
                            try {
                                sseEmitter.send(value);
                            } catch (IOException e) {
                                throw new RuntimeException("Sending message failed.");
                            }
                        },
                        sseEmitter::completeWithError,
                        sseEmitter::complete
                );

        return sseEmitter;
    }
}
