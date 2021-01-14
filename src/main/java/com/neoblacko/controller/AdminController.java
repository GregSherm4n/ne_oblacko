package com.neoblacko.controller;

import com.neoblacko.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Secured("ROLE_admin")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @GetMapping("")
    public ModelAndView admin() {
        var modelAndView = new ModelAndView();

        var usersList = userService.getAllUsers();
        usersList.sort((o1, o2) -> o1.getUserId() > o2.getUserId() ? 1 : -1);

        modelAndView.setViewName("/admin");
        modelAndView.addObject("users", usersList);

        return modelAndView;
    }

}
