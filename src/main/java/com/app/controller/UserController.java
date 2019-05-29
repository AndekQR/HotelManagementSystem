package com.app.controller;

import com.app.model.User;
import com.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService=userService;
    }

    @RequestMapping(value="/login", method=RequestMethod.GET)
    public String loginPage(){
        return "login";
    }

    @RequestMapping(value="/register", method=RequestMethod.GET)
    public String registerPane(Model model){
        User user = userService.newUser();
        model.addAttribute("user", user);
        return "register";
    }

    @RequestMapping(value="/register", method=RequestMethod.POST)
    public String saveUser(@ModelAttribute User user, RedirectAttributes redirectAttributes){
        String result = "fail";

        if (userService.isEmailUnique(user.getEmail()))
            if (userService.isUniqueNameIsUnique(user.getUniqueName())){
                userService.saveUser(user);
                result = "success";
                redirectAttributes.addFlashAttribute("registerResult", result);
                return "redirect:/login";
            }

        redirectAttributes.addFlashAttribute("registerResult", result);
        return "redirect:/login";
    }
}
