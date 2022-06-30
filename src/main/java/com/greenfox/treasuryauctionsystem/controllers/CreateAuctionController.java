package com.greenfox.treasuryauctionsystem.controllers;

import com.greenfox.treasuryauctionsystem.models.Auction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CreateAuctionController {

  @GetMapping("/admin/auction")
  public String renderAuctionCreationPage(){
    return "return create-auction";
  }


  @PostMapping("/admin/auction")
  public String createAuction(@ModelAttribute Auction auction){

    return "redirect:/";
  }

}
