package com.thecookiezen.presentation;

import com.thecookiezen.bussiness.cluster.boundary.ClusterRepository;
import com.thecookiezen.bussiness.cluster.entity.Cluster;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
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
@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ClusterController {

    ClusterRepository clusterRepository;

    @ModelAttribute(name = "clustersList")
    public Collection<Cluster> getAll() {
        return clusterRepository.getAll();
    }

    @RequestMapping(value="clusters/form", method=RequestMethod.GET)
    public String showForm(@RequestParam(required = false) Long clusterId, Model model) {
        if (clusterId != null) {
            model.addAttribute("cluster", clusterRepository.getById(clusterId).orElse(new Cluster()));
        } else {
            model.addAttribute("cluster", new Cluster());
        }
        return "clusters/form";
    }

    @RequestMapping(value = "clusters/all", method = RequestMethod.GET)
    public String page() {
        return "clusters/all";
    }

    @RequestMapping(value = "clusters", method = RequestMethod.POST)
    public String saveOrUpdate(@Valid Cluster cluster, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "clusters/form";
        }

        clusterRepository.save(cluster);
        return "redirect:/clusters/all";
    }

    @RequestMapping(value = "clusters", method = RequestMethod.DELETE)
    public String delete(@RequestParam Long clusterId) {
        clusterRepository.remove(clusterId);
        return "redirect:/clusters/all";
    }

}
