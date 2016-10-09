package com.thecookiezen.presentation;

import com.thecookiezen.bussiness.cluster.boundary.ClustersManager;
import com.thecookiezen.bussiness.cluster.control.ClusterInstance;
import com.thecookiezen.bussiness.cluster.control.NodeInstance;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class NodeController {

    ClustersManager clustersManager;

    @RequestMapping("/clusters/node")
    public String node(@RequestParam long clusterId, @RequestParam long nodeId, Model model) {
        ClusterInstance instance = clustersManager.getInstance(clusterId);
        NodeInstance nodeInstance = instance.getNodes().get(nodeId);
        model.addAttribute("clusterName", instance.getName());
        model.addAttribute("node", nodeInstance);
        return "clusters/node";
    }

}
