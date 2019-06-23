package com.app.controller;

import com.app.helpers.AuthorityType;
import com.app.service.AuthorityService;
import com.app.service.FacebookService;
import com.app.service.SecurityService;
import com.app.service.UserService;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SpringFacebookController {
    private FacebookService facebookService;
    private AuthorityService authorityService;
    private UserService userService;
    private final SecurityService securityService;

    public SpringFacebookController(FacebookService facebookService, AuthorityService authorityService, UserService userService, SecurityService securityService) {
        this.facebookService=facebookService;
        this.authorityService=authorityService;
        this.userService=userService;
        this.securityService=securityService;
    }

    @RequestMapping(value="/facebooklogin", method=RequestMethod.GET)
    public RedirectView facebookLogin(){
        RedirectView redirectView = new RedirectView();
        String url = facebookService.facebookLogin();
        redirectView.setUrl(url);
        return redirectView;
    }

    @RequestMapping(value="/facebook", method=RequestMethod.GET)
    public String facebook(@RequestParam("code") String code){
        String accessToken = facebookService.getFacebookAccessToken(code);
        return "redirect:/facebookprofiledata/"+accessToken;
    }
    @RequestMapping(value="facebookprofiledata/{accessToken:.+}", method=RequestMethod.GET)
    public String facebookprofiledata(@PathVariable String accessToken, Model model, HttpServletRequest request){
        User user = facebookService.getFacebookUserProfile(accessToken);
        com.app.model.User socialUserInDb = userService.findByEmail(user.getEmail());
        if (socialUserInDb == null){
            socialUserInDb = new com.app.model.User();
            socialUserInDb.setEmail(user.getEmail());
            socialUserInDb.setLastName(user.getLastName());
            socialUserInDb.setFirstName(user.getFirstName());
            socialUserInDb.setUniqueName(user.getFirstName());
            userService.save(socialUserInDb);
        }
        else {
            socialUserInDb.setFirstName(user.getFirstName());
            socialUserInDb.setLastName(user.getLastName());
            userService.update(socialUserInDb);
        }
        String role = authorityService.createOrGetAuthority(AuthorityType.ROLE_USER).getName().name();
        securityService.autoLogin(socialUserInDb.getEmail(), socialUserInDb.getPassword(), role, request);

        return "redirect:/booking";
    }
}
