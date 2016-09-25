package com.thecookiezen.infrastructure.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.StatsCmd;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.thecookiezen.bussiness.containers.boundary.ContainerFetcher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Collection;

@Component
public class DockerFetcher implements ContainerFetcher {

    private DockerClient dockerClient;

    @PostConstruct
    void init() {
        dockerClient = getDockerClient("tcp://0.0.0.0:2375", "1.24");
    }

    public DockerClient getDockerClient(final String dockerHost, final String apiVersion) {
        DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(dockerHost)
                .withApiVersion(apiVersion)
                .build();
        return DockerClientBuilder.getInstance(config).build();
    }

    @PreDestroy
    void cleanUp() throws IOException {
        dockerClient.close();
    }

    @Override
    public Collection<Container> list(DockerClient dockerClient) {
        return dockerClient.listContainersCmd().withShowAll(true).exec();
    }

    @Override
    public Info getInfo(DockerClient dockerClient) {
        return dockerClient.infoCmd().exec();
    }

    @Override
    public StatsCmd statsCmd(String containerId) {
        return dockerClient.statsCmd(containerId);
    }
}
