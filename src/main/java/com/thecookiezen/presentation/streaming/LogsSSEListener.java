package com.thecookiezen.presentation.streaming;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thecookiezen.bussiness.cluster.boundary.ContainerFetcher;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@CommonsLog
public class LogsSSEListener {
    private static final JsonNodeFactory factory = JsonNodeFactory.instance;
    private static final ObjectMapper objectMapper = new ObjectMapper();

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
                .window(1000, TimeUnit.MILLISECONDS)
                .flatMap(window -> window.reduce("", String::concat))
                .subscribe(
                        value -> {
                            try {
                                ObjectNode message = factory.objectNode().put("message", value);
                                String s = objectMapper.writeValueAsString(message);
                                sseEmitter.send(s, APPLICATION_JSON);
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
