package com.thecookiezen.presentation;

import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Info;
import com.thecookiezen.bussiness.cluster.boundary.ClusterRepository;
import com.thecookiezen.bussiness.containers.boundary.ContainerFetcher;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class IndexController {

    ClusterRepository clusterRepository;

    ContainerFetcher containerFetcher;

    @RequestMapping("/index")
    public String index(Model model) {
        Collection<Container> list = containerFetcher.list();
        Info info = containerFetcher.getInfo();
        model.addAttribute("nodeInfo", info);
        model.addAttribute("containers", list);
        return "index";
    }

    @RequestMapping("/test")
    public String test() {
        return "test";
    }
}
