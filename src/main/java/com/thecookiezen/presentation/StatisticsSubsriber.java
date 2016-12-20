package com.thecookiezen.presentation;

import com.thecookiezen.bussiness.cluster.boundary.ClustersManager;
import com.thecookiezen.bussiness.cluster.boundary.ContainerFetcher;
import com.thecookiezen.bussiness.cluster.control.ClusterInstance;
import com.thecookiezen.infrastructure.docker.StatisticsSSEListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class StatisticsSubsriber {

    private Map<String, StatisticsSSEListener> listenerMap = new ConcurrentHashMap<>();

    private ClustersManager clustersManager;

    @Autowired
    public StatisticsSubsriber(ClustersManager clustersManager) {
        this.clustersManager = clustersManager;
    }

    @RequestMapping("/statistics/subscribe/{clusterId}/{nodeId}/{containerId}")
    public SseEmitter subscribeUpdates(@PathVariable long clusterId, @PathVariable long nodeId, @PathVariable String containerId) {
        ClusterInstance instance = clustersManager.getInstance(clusterId);
        ContainerFetcher node = instance.getNode(nodeId);
        listenerMap.putIfAbsent(containerId, new StatisticsSSEListener(containerId, node));
        return listenerMap.get(containerId).createNewEmiter();
    }
}
