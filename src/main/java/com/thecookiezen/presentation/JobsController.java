package com.thecookiezen.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JobsController {
    @RequestMapping("/jobs")
    public String index() {
        return "jobs";
    }
}
