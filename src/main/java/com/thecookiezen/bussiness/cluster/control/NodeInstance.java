package com.thecookiezen.bussiness.cluster.control;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.StatsCmd;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Info;
import com.thecookiezen.bussiness.cluster.boundary.ContainerFetcher;
import lombok.Data;

import java.util.Collection;

@Data
public class NodeInstance implements ContainerFetcher {

    private final String name;

    private final DockerClient dockerClient;

    @Override
    public Collection<Container> getContainers() {
        return dockerClient.listContainersCmd().withShowAll(true).exec();
    }

    @Override
    public Info getInfo() {
        return dockerClient.infoCmd().exec();
    }

    @Override
    public StatsCmd statsCmd(String containerId) {
        return dockerClient.statsCmd(containerId);
    }
}
