package com.example.questlog.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class DashboardController {

    @GetMapping("/ui/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("message", "You are authenticated 🎉");
        return "dashboard";
    }
}