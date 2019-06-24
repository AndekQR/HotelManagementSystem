package com.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {

    @RequestMapping(value={"/", "/home"})
    public String home() {
        return "index";
    }

    @RequestMapping(value={"/contact", "/contact"})
    public String contact() {
        return "contact";
    }


}
