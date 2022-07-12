package com.greenfox.treasuryauctionsystem.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    // Testing authorization
    @GetMapping("/admin/test")
    public String testController() {
        return "test";
    }

    @GetMapping("/admin_skeleton")
    public String adminSkeleton() {
        return "admin/admin_skeleton";
    }
    @GetMapping("/admin_dashboard")
    public String adminDashboard() {
        return "admin/admin_dashboard";
    }
}
