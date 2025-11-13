package com.mycast.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String helloName(Model model){
        model.addAttribute("name", "Heitor");
        return "index";
    }
}
