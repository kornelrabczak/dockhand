package com.thecookiezen.presentation.streaming;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thecookiezen.bussiness.cluster.boundary.ContainerFetcher;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@CommonsLog
public class StatisticsSSEListener {

    private static final ObjectMapper objectMapper = new ObjectMapper();

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

        nodeInstance.stats(containerId)
                .sample(500, TimeUnit.MILLISECONDS)
                .subscribe(
                        value -> {
                            try {
                                sseEmitter.send(objectMapper.writeValueAsString(value), APPLICATION_JSON);
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
