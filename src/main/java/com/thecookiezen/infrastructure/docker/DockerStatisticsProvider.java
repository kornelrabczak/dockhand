package com.thecookiezen.infrastructure.docker;

import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.model.Statistics;
import com.thecookiezen.bussiness.cluster.boundary.StatisticsProvider;
import com.thecookiezen.bussiness.containers.boundary.ContainerFetcher;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DockerStatisticsProvider implements StatisticsProvider {

    private ContainerFetcher containerFetcher;

    @Override
    public void getStats(String containerId, ResultCallback<Statistics> resultCallback) {
        containerFetcher.statsCmd(containerId).exec(resultCallback);
    }
}
