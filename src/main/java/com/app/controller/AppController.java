package com.app.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableAutoConfiguration
public class AppController {

    @RequestMapping(value={"/", "/home"})
    public String home(ModelMap model) {
        return "index.html";
    }
}
