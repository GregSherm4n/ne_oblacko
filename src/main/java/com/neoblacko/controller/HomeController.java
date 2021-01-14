package com.neoblacko.controller;

import com.neoblacko.service.FileService;
import com.neoblacko.service.UserService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;

@Controller
@Secured({"ROLE_admin", "ROLE_user"})
public class HomeController {

    @Autowired
    @Qualifier("fileServiceImpl")
    private FileService fileService;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Value("${cloud.folder.root}")
    private String cloudFolder;

    @GetMapping({"", "/", "home"})
    public ModelAndView homePage(@RequestParam(name = "error", required = false) String error, Authentication authentication) {
        var modelAndView = new ModelAndView();

        var currentUser = userService.getUser(authentication.getName());
        var userDirectory = new File(cloudFolder + File.separator + currentUser.getUserId());
        userDirectory.mkdir();

        modelAndView.setViewName("/home");
        var y = FileUtils.sizeOfDirectory(userDirectory);
        var x = currentUser.getTariff().getTariffLimit();
        var result = y * 1.0 / (x * 1048576) * 100;
        if (result > 100) {
            result = 100;
        }
        modelAndView.addObject("count", Math.ceil(result) + "%");
        modelAndView.addObject("files", currentUser.getFiles());
        modelAndView.addObject("tariff", currentUser.getTariff().getTariffName());
        modelAndView.addObject("error", error);

        return modelAndView;
    }


}
