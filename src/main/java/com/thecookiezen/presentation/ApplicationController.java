package com.thecookiezen.presentation;

import com.thecookiezen.bussiness.application.boundary.ApplicationRepository;
import com.thecookiezen.bussiness.application.entity.Application;
import com.thecookiezen.bussiness.cluster.control.ClusterRepository;
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
public class ApplicationController {

    ApplicationRepository applicationRepository;

    @ModelAttribute(name = "applications")
    public Collection<Application> getAll() {
        return applicationRepository.getAll();
    }

    @RequestMapping(value = "application/form", method = RequestMethod.GET)
    public String showForm(@RequestParam(required = false) Long applicationId, Model model) {
        if (applicationId != null) {
            model.addAttribute("application", applicationRepository.getById(applicationId).orElse(new Application()));
        } else {
            model.addAttribute("application", new Application());
        }
        return "clusters/form";
    }

    @RequestMapping(value = "clusters", method = RequestMethod.DELETE)
    public String delete(@RequestParam Long clusterId) {
        clusterRepository.remove(clusterId);
        return "redirect:/clusters/all";
    }

    @RequestMapping(value = "clusters", method = RequestMethod.POST)
    public String saveOrUpdate(@Valid Cluster cluster, BindingResult bindingResult) {
        log.info("saveOrUpdate");
        if (bindingResult.hasErrors()) {
            return "application/form";
        }

        applicationRepository.save(cluster);
        return "redirect:/applications";
    }
}
