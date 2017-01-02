package com.thecookiezen.presentation;

import com.thecookiezen.bussiness.cluster.boundary.ClustersManager;
import com.thecookiezen.bussiness.cluster.entity.Cluster;
import com.thecookiezen.bussiness.cluster.entity.DockerHost;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@CommonsLog
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ClusterController {

    ClustersManager clustersManager;

    @ModelAttribute(name = "clustersList")
    public Collection<Cluster> getAll() {
        return clustersManager.clustersList();
    }

    @RequestMapping(value = "clusters/form", method = RequestMethod.GET)
    public String showForm(@RequestParam(required = false) Long clusterId, Model model) {
        if (clusterId != null) {
            model.addAttribute("cluster", clustersManager.getClusterById(clusterId).orElse(new Cluster()));
        } else {
            model.addAttribute("cluster", new Cluster());
        }
        return "clusters/form";
    }

    @RequestMapping(value = "clusters/all", method = RequestMethod.GET)
    public String page() {
        return "clusters/all";
    }

    @RequestMapping(value = "clusters", method = RequestMethod.DELETE)
    public String delete(@RequestParam Long clusterId) {
        clustersManager.remove(clusterId);
        return "redirect:/clusters/all";
    }

    @RequestMapping(value = "clusters/newhost", method = RequestMethod.GET)
    public String newHostForm(@RequestParam Long clusterId, Model model) {
        model.addAttribute("clusterId", clusterId);
        model.addAttribute("host", new DockerHost());
        return "clusters/addhost";
    }

    @RequestMapping(value = "clusters", params = {"addHost"}, method = RequestMethod.POST)
    public String addHost(@Valid DockerHost dockerHost, @RequestParam long clusterId, BindingResult bindingResult) {
        log.info("addHost");

        if (bindingResult.hasErrors()) {
            return "clusters/addhost";
        }

        Cluster cluster = clustersManager.getClusterById(clusterId).get();
        cluster.getHosts().add(dockerHost);
        clustersManager.save(cluster);
        return "redirect:/clusters/all";
    }

    @RequestMapping(value = "clusters/node", method = RequestMethod.DELETE)
    public String deleteHost(@RequestParam Long clusterId, @RequestParam Long hostId) {
        Cluster cluster = clustersManager.getClusterById(clusterId).get();
        cluster.getHosts().removeIf(h -> h.getId() == hostId);
        clustersManager.save(cluster);
        return "redirect:/clusters/all";
    }

    @RequestMapping(value = "clusters", method = RequestMethod.POST)
    public String saveOrUpdate(@Valid Cluster cluster, BindingResult bindingResult) {
        log.info("saveOrUpdate");
        if (bindingResult.hasErrors()) {
            return "clusters/form";
        }

        clustersManager.save(cluster);
        return "redirect:/clusters/all";
    }
}
