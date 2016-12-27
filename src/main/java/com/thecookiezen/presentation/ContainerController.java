package com.thecookiezen.presentation;

import com.thecookiezen.bussiness.cluster.boundary.ClustersManager;
import com.thecookiezen.bussiness.cluster.boundary.ContainerFetcher;
import com.thecookiezen.bussiness.cluster.control.ClusterInstance;
import com.thecookiezen.bussiness.cluster.control.NodeInstance;
import com.thecookiezen.infrastructure.docker.LogsSSEListener;
import com.thecookiezen.infrastructure.docker.StatisticsSSEListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@Log4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContainerController {

    private Map<String, StatisticsSSEListener> statisticsSSEListenerMap = new ConcurrentHashMap<>();

    private Map<String, LogsSSEListener> logsSSEListenerMap = new ConcurrentHashMap<>();

    private final ClustersManager clustersManager;

    @RequestMapping("/cluster/{clusterId}/node/{nodeId}/container/{containerId}/logs")
    public SseEmitter subscribeOnLogs(@PathVariable long clusterId, @PathVariable long nodeId, @PathVariable String containerId) {
        ClusterInstance instance = clustersManager.getInstance(clusterId);
        ContainerFetcher node = instance.getNode(nodeId);
        logsSSEListenerMap.putIfAbsent(containerId, new LogsSSEListener(containerId, node));
        return logsSSEListenerMap.get(containerId).createNewEmiter();
    }

    @RequestMapping("/cluster/{clusterId}/node/{nodeId}/container/{containerId}/statistics")
    public SseEmitter subscribeOnStatistics(@PathVariable long clusterId, @PathVariable long nodeId, @PathVariable String containerId) {
        ClusterInstance instance = clustersManager.getInstance(clusterId);
        ContainerFetcher node = instance.getNode(nodeId);
        statisticsSSEListenerMap.putIfAbsent(containerId, new StatisticsSSEListener(containerId, node));
        return statisticsSSEListenerMap.get(containerId).createNewEmiter();
    }

    @RequestMapping("/cluster/{clusterId}/node/{nodeId}/container/{containerId}")
    public String node(@PathVariable("clusterId") long clusterId, @PathVariable("nodeId") long nodeId,
                       @PathVariable("containerId") String containerId, Model model) {
        ClusterInstance instance = clustersManager.getInstance(clusterId);
        NodeInstance nodeInstance = instance.getNodes().get(nodeId);
        model.addAttribute("clusterId", clusterId);
        model.addAttribute("node", nodeInstance);
        model.addAttribute("container", nodeInstance.getContainer(containerId));
        return "clusters/container";
    }
}
