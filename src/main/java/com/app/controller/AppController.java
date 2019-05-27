package com.app.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@EnableAutoConfiguration
public class AppController {

    @RequestMapping(value={"/", "/home"})
    public String home(ModelMap model) {
        return "index";
    }

    @RequestMapping(value="/login", method=RequestMethod.GET)
    public String loginPage(){
        return "login";
    }

    @RequestMapping(value="/register", method=RequestMethod.GET)
    public String registerPane(){
        return "register";
    }
}
