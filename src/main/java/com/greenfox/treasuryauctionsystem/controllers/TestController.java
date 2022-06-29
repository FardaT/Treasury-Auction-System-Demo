package com.greenfox.treasuryauctionsystem.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
  // Testing authorization
  @GetMapping("/admin/test")
  public String testController(){
    return "test";
  }

}
