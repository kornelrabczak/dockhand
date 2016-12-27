package com.thecookiezen.infrastructure.docker;

import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.model.Frame;
import com.google.common.collect.Lists;
import com.thecookiezen.bussiness.cluster.boundary.ContainerFetcher;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@CommonsLog
public class LogsSSEListener implements ResultCallback<Frame> {

    private final List<SseEmitter> emitters = Lists.newCopyOnWriteArrayList();
    private final String containerId;
    private final AtomicBoolean isWorking = new AtomicBoolean(false);
    private final ContainerFetcher nodeInstance;
    private Closeable closeable;

    public LogsSSEListener(String containerId, ContainerFetcher nodeInstance) {
        this.containerId = containerId;
        this.nodeInstance = nodeInstance;
    }

    public SseEmitter createNewEmiter() {
        if (!nodeInstance.isContainerRunning(containerId)) {
            throw new IllegalStateException("Container not running.");
        }

        SseEmitter sseEmitter = new SseEmitter();
        emitters.add(sseEmitter);
        if (isWorking.compareAndSet(false, true)) {
            nodeInstance.logs(containerId).exec(this);
        }
        return sseEmitter;
    }

    @Override
    public void onStart(Closeable closeable) {
        log.info("Statistics events stream started.");
        this.closeable = closeable;
    }

    @Override
    public void onNext(Frame frame) {
        emitters.removeIf(e -> {
            try {
                e.send(new String(frame.getPayload()));
            } catch (Exception ex) {
                log.error("Error during processing event.", ex);
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

    @Override
    public void onError(Throwable throwable) {
        log.error("Events stream error.", throwable);
    }

    @Override
    public void onComplete() {
        log.info("Events stream completed.");
        isWorking.set(false);
    }

    @Override
    public void close() throws IOException {
        log.info("Events stream closed.");
        isWorking.set(false);
        closeable.close();
    }
}
