package com.greenfox.treasuryauctionsystem.controllers;

import static org.apache.coyote.http11.Constants.a;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/hello")
public class HelloController {

  @GetMapping()
  @ResponseBody
  public String helloController(){
    return "Hello world!";
  }
}
