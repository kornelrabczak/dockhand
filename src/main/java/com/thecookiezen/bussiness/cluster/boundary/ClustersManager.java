package com.thecookiezen.bussiness.cluster.boundary;

import com.thecookiezen.bussiness.cluster.control.ClusterInstance;
import com.thecookiezen.bussiness.cluster.control.ClusterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Log4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClustersManager {

    private final ClusterRepository clusterRepository;

    private Map<Long, ClusterInstance> instances = new ConcurrentHashMap<>();

    @PostConstruct
    private void onInit() {
        clusterRepository.getAll().forEach(cluster -> instances.put(cluster.getId(), new ClusterInstance(cluster)));
    }

    public ClusterInstance getInstance(long clusterId) {
        return instances.get(clusterId);
    }

    @PreDestroy
    private void cleanup() {
        instances.values().forEach(ClusterInstance::stop);
    }
}
