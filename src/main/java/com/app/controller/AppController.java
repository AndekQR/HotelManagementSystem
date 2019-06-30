package com.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AppController {

    @RequestMapping(value={"/", "/home"})
    public String home() {
        return "index";
    }

    @RequestMapping(value={"/contact"})
    public String contact() {
        return "contact";
    }

    @RequestMapping(value={"/prices"})
    public String prices() {
        return "prices";
    }

    @RequestMapping(value="/roomManage", method=RequestMethod.GET)
    public String roomManage(){
        return "roomManagement";
    }


}
