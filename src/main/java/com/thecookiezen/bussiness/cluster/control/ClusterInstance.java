package com.thecookiezen.bussiness.cluster.control;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.DockerCmdExecFactory;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.jaxrs.JerseyDockerCmdExecFactory;
import com.thecookiezen.bussiness.cluster.boundary.ContainerFetcher;
import com.thecookiezen.bussiness.cluster.entity.Cluster;
import com.thecookiezen.infrastructure.docker.NodeInstance;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import java.util.HashMap;
import java.util.Map;

@Data
@Log4j
public class ClusterInstance {

    private final Cluster cluster;

    private Map<Long, ContainerFetcher> nodes = new HashMap<>();

    public ClusterInstance(Cluster cluster) {
        this.cluster = cluster;
        cluster.getHosts().forEach(h -> {
            DockerClient dockerClient = getDockerClient(h.getDockerDaemonUrl(), cluster.getDockerApiVersion());
            nodes.put(h.getId(), new NodeInstance(h.getId(), h.getName(), dockerClient));
        });
    }

    public Object getName() {
        return cluster.getName();
    }

    private DockerClient getDockerClient(final String dockerHost, final String apiVersion) {
        DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(dockerHost)
                .withApiVersion(apiVersion)
                .build();

        DockerCmdExecFactory dockerCmdExecFactory = new JerseyDockerCmdExecFactory()
                .withMaxTotalConnections(200)
                .withMaxPerRouteConnections(20);

        return DockerClientBuilder.getInstance(config)
                .withDockerCmdExecFactory(dockerCmdExecFactory)
                .build();
    }

    public void stop() {
        nodes.values().forEach(ContainerFetcher::close);
    }

    public ContainerFetcher getNode(long nodeId) {
        return nodes.get(nodeId);
    }
}
