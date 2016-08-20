package com.thecookiezen.infrastructure.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
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
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("tcp://0.0.0.0:2376")
                .withApiVersion("1.24")
                .build();
        dockerClient = DockerClientBuilder.getInstance(config).build();
    }

    @PreDestroy
    void cleanUp() throws IOException {
        dockerClient.close();
    }

    @Override
    public Collection<Container> list() {
        return dockerClient.listContainersCmd().withShowAll(true).exec();
    }

    @Override
    public Info getInfo() {
        return dockerClient.infoCmd().exec();
    }
}
