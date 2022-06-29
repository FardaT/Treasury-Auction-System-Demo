package com.greenfox.treasuryauctionsystem.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class HelloController {

  @GetMapping()
  @ResponseBody
  public String helloController(){
    return "Hello world!";
  }
}
