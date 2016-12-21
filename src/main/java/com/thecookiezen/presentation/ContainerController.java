package com.thecookiezen.presentation;

import com.thecookiezen.bussiness.cluster.boundary.ClustersManager;
import com.thecookiezen.bussiness.cluster.control.ClusterInstance;
import com.thecookiezen.bussiness.cluster.control.NodeInstance;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ContainerController {

    ClustersManager clustersManager;

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
