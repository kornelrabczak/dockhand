package com.thecookiezen.bussiness.cluster.control;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.thecookiezen.bussiness.cluster.boundary.ContainerFetcher;
import com.thecookiezen.bussiness.cluster.entity.Cluster;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Data
@Log4j
public class ClusterInstance {

    private final Cluster cluster;

    private Map<Long, NodeInstance> nodes = new HashMap<>();

    public ClusterInstance(Cluster cluster) {
        this.cluster = cluster;
        cluster.getHosts().forEach(h -> {
            DockerClient dockerClient = getDockerClient(h.getDockerDaemonUrl(), cluster.getDockerApiVersion());
            nodes.put(h.getId(), new NodeInstance(h.getName(), dockerClient));
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
        return DockerClientBuilder.getInstance(config).build();
    }

    public void stop() {
        nodes.values().forEach(node -> {
            try {
                node.getDockerClient().close();
            } catch (IOException e) {
                log.error("Error occurred during closing docker client for node [" + node.getName() + "]");
            }
        });
    }

    public ContainerFetcher getNode(long nodeId) {
        return nodes.get(nodeId);
    }
}
