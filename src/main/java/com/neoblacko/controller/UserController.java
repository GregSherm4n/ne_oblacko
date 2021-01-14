package com.neoblacko.controller;

import com.neoblacko.model.User;
import com.neoblacko.service.TariffService;
import com.neoblacko.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
public class UserController {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Autowired
    @Qualifier("tariffServiceImpl")
    private TariffService tariffService;

    @Autowired
    private PasswordEncoder bcryptPasswordEncoder;

    @Value("${cloud.folder.root}")
    private String cloudFolder;

    @GetMapping("/login")
    public String loginPage(Model model, @RequestParam(name = "error", required = false) Boolean error) {
        model.addAttribute("form", new User());
        if (error != null && error) {
            model.addAttribute("error", error);
        }
        return "/login";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model, @RequestParam(name = "error", required = false) Boolean error) {
        model.addAttribute("form", new User());

        if (error != null && error)
            model.addAttribute("error", error);

        return "/registration";
    }

    @PostMapping("/registration")
    public String registrationHandlerPage(Model model, @ModelAttribute User user) throws IOException {

        if (userService.getUser(user.getUserLogin()) == null) {
            user.setUserRole("user");

            user.setUserPassword(bcryptPasswordEncoder.encode(user.getUserPassword()));

            var tariffList = tariffService.getAllTariffs();
            tariffList.sort((t1, t2) -> t2.getTariffId() > t1.getTariffId() ? -1 : 1);
            user.setTariff(tariffList.get(0));

            userService.addUser(user);
            Files.createDirectories(Paths.get(cloudFolder + File.separator + user.getUserId()));
            System.out.println("Добавлен пользователь " + user.getUserLogin());
            return "redirect:/login";
        } else
            return "redirect:/registration?error=true";

    }

}
