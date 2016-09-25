package com.thecookiezen.presentation;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j
public class IndexController {

    @RequestMapping("/index")
    public String index() {
        return "index";
    }
}
