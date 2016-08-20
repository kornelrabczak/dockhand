package com.thecookiezen.presentation;

import com.thecookiezen.bussiness.cluster.boundary.ClusterRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class IndexController {

    ClusterRepository clusterRepository;

    @RequestMapping("/index")
    public String index(Model model) {
        return "index";
    }
}
