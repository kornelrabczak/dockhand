package com.thecookiezen.presentation;

import com.thecookiezen.bussiness.cluster.boundary.ClusterFetcher;
import com.thecookiezen.bussiness.cluster.boundary.ClustersManager;
import com.thecookiezen.bussiness.cluster.boundary.ContainerFetcher;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class NodeController {

    ClustersManager clustersManager;

    @RequestMapping("/cluster/{clusterId}/node/{nodeId}")
    public String node(@PathVariable("clusterId") long clusterId, @PathVariable("nodeId") long nodeId, Model model) {
        ClusterFetcher instance = clustersManager.getInstance(clusterId);
        ContainerFetcher nodeInstance = instance.getNode(nodeId);
        model.addAttribute("clusterName", instance.getName());
        model.addAttribute("clusterId", clusterId);
        model.addAttribute("node", nodeInstance);
        return "clusters/node";
    }

}
