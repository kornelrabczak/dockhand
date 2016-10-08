package com.thecookiezen.presentation;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Info;
import com.thecookiezen.bussiness.cluster.control.ClusterRepository;
import com.thecookiezen.bussiness.cluster.entity.Cluster;
import com.thecookiezen.bussiness.cluster.entity.DockerHost;
import com.thecookiezen.bussiness.containers.boundary.ContainerFetcher;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.NotFoundException;
import java.util.Collection;
import java.util.Optional;

@Controller
@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class NodeController {

    ContainerFetcher containerFetcher;

    ClusterRepository clusterRepository;

    @RequestMapping("/clusters/node")
    public String node(@RequestParam long clusterId, @RequestParam long nodeId, Model model) {
        Optional<Cluster> byId = clusterRepository.getById(clusterId);
        if (!byId.isPresent())
            throw new NotFoundException("CHANGE ME !");

        Cluster cluster = byId.get();
        Optional<DockerHost> nodeOptional = cluster.getHosts().stream().filter(n -> n.getId() == nodeId).findFirst();
        if (!nodeOptional.isPresent())
            throw new NotFoundException("CHANGE ME !");

        DockerClient dockerClient = containerFetcher.getDockerClient(nodeOptional.get().getDockerDaemonUrl(), cluster.getDockerApiVersion());
        Collection<Container> list = containerFetcher.list(dockerClient);
        Info info = containerFetcher.getInfo(dockerClient);

        model.addAttribute("cluster", cluster);
        model.addAttribute("node", nodeOptional.get());
        model.addAttribute("nodeInfo", info);
        model.addAttribute("containers", list);
        return "clusters/node";
    }

}
