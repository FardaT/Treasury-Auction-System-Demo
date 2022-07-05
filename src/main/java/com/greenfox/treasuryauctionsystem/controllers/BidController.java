package com.greenfox.treasuryauctionsystem.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class BidController {

    // CREATE - show form
    @GetMapping("admin/bids/create")
    public String create() {
        return "";
    }
}
