package com.thecookiezen.infrastructure.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.DockerCmdExecFactory;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.jaxrs.JerseyDockerCmdExecFactory;
import com.google.common.collect.Iterables;
import com.thecookiezen.bussiness.cluster.boundary.ClusterFetcher;
import com.thecookiezen.bussiness.cluster.boundary.ContainerFetcher;
import com.thecookiezen.bussiness.cluster.entity.Cluster;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Data
@Log4j
public class DockerClusterInstance implements ClusterFetcher {

    private final Cluster cluster;

    private Map<Long, ContainerFetcher> nodes = new HashMap<>();

    public DockerClusterInstance(Cluster cluster) {
        this.cluster = cluster;
        cluster.getHosts().forEach(h -> {
            DockerClient dockerClient = getDockerClient(h.getDockerDaemonUrl(), cluster.getDockerApiVersion());
            nodes.put(h.getId(), new NodeInstance(h.getId(), h.getName(), dockerClient));
        });
    }

    @Override
    public String getName() {
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

    @Override
    public void stop() {
        nodes.values().forEach(ContainerFetcher::close);
    }

    @Override
    public ContainerFetcher getNode(long nodeId) {
        return nodes.get(nodeId);
    }

    @Override
    public Iterator<ContainerFetcher> roundRobinHosts() {
        return Iterables.cycle(nodes.values()).iterator();
    }
}
