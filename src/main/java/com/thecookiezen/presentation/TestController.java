package com.thecookiezen.presentation;

import com.thecookiezen.bussiness.cluster.boundary.ClusterRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TestController {

    ClusterRepository clusterRepository;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("count", clusterRepository.getAll().size());
        return "greeting";
    }
}
