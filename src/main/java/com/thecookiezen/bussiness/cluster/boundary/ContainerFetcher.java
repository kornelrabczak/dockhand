package com.thecookiezen.bussiness.cluster.boundary;

import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.command.StatsCmd;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Info;
import rx.Observable;

import java.util.Collection;

public interface ContainerFetcher {
    Collection<Container> getContainers();

    Info getInfo();

    InspectContainerResponse getContainer(String containerId);

    StatsCmd statsCmd(String containerId);

    boolean isContainerRunning(String containerId);

    Observable<String> logs(String containerId);
}
