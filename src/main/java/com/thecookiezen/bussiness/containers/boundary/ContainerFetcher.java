package com.thecookiezen.bussiness.containers.boundary;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.StatsCmd;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Info;

import java.util.Collection;

public interface ContainerFetcher {
    Collection<Container> list(DockerClient dockerClient);

    Info getInfo(DockerClient dockerClient);

    DockerClient getDockerClient(final String dockerHost, final String apiVersion);

    StatsCmd statsCmd(String containerId);
}
