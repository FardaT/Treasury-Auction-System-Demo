package com.greenfox.treasuryauctionsystem.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

  @GetMapping("/admin/test")
  public String testController(){
    return "test";
  }

}
