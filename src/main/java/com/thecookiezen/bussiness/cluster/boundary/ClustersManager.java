package com.thecookiezen.bussiness.cluster.boundary;

import com.thecookiezen.infrastructure.docker.DockerClusterInstance;
import com.thecookiezen.bussiness.cluster.control.ClusterRepository;
import com.thecookiezen.bussiness.cluster.entity.Cluster;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClustersManager {

    private final ClusterRepository clusterRepository;

    private Map<Long, ClusterFetcher> instances = new ConcurrentHashMap<>();

    @PostConstruct
    private void onInit() {
        clusterRepository.getAll().forEach(cluster -> instances.put(cluster.getId(), new DockerClusterInstance(cluster)));
    }

    public Collection<Cluster> clustersList() {
        return clusterRepository.getAll();
    }

    public Optional<Cluster> getClusterById(long clusterId) {
        return clusterRepository.getById(clusterId);
    }

    public ClusterFetcher getInstance(long clusterId) {
        return instances.get(clusterId);
    }

    public void remove(Long clusterId) {
        clusterRepository.remove(clusterId);
        instances.remove(clusterId);
    }

    public void save(Cluster cluster) {
        clusterRepository.save(cluster);
        instances.compute(cluster.getId(), (k, v) -> new DockerClusterInstance(clusterRepository.getById(k).get()));
    }

    @PreDestroy
    private void cleanup() {
        instances.values().forEach(ClusterFetcher::stop);
    }
}
