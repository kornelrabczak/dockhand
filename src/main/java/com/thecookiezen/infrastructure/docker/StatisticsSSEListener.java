package com.thecookiezen.infrastructure.docker;

import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.model.Statistics;
import com.google.common.collect.Lists;
import com.thecookiezen.bussiness.cluster.boundary.StatisticsProvider;
import lombok.extern.log4j.Log4j;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Log4j
public class StatisticsSSEListener implements ResultCallback<Statistics> {

    private final List<SseEmitter> emitters = Lists.newCopyOnWriteArrayList();
    private final String containerId;
    private final StatisticsProvider statisticsProvider;
    private final AtomicBoolean isWorking = new AtomicBoolean(false);

    public StatisticsSSEListener(String containerId, StatisticsProvider statisticsProvider) {
        this.containerId = containerId;
        this.statisticsProvider = statisticsProvider;
    }

        public SseEmitter createNewEmiter() {
        SseEmitter sseEmitter = new SseEmitter();
        sseEmitter.onCompletion(() -> this.cleanUp(sseEmitter));
        emitters.add(sseEmitter);
        if (!isWorking.get()) {
            statisticsProvider.getStats(containerId, this);
        }
        return sseEmitter;
    }

    private void cleanUp(SseEmitter sseEmitter) {
        emitters.remove(sseEmitter);
        if (emitters.isEmpty()) {
            try {
                close();
                isWorking.set(false);
            } catch (IOException e) {
                log.error("Error during closing statistics events stream.", e);
            }
        }
    }

    @Override
    public void onStart(Closeable closeable) {
        log.info("Statistics events stream started.");
        isWorking.set(true);
    }

    @Override
    public void onNext(Statistics statistics) {
        emitters.forEach(e -> {
            try {
                e.send(statistics.getMemoryStats().get("usage"));
            } catch (IOException ex) {
                log.error("Error during processing statistics event.", ex);
                e.completeWithError(ex);
            }
        });
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("Statistics events stream error.", throwable);
    }

    @Override
    public void onComplete() {
        log.info("Statistics events stream completed.");
        isWorking.set(false);
    }

    @Override
    public void close() throws IOException {
        log.info("Statistics events stream closed.");
        isWorking.set(false);
    }
}
