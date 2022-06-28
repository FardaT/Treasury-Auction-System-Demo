package com.greenfox.treasuryauctionsystem.controllers;

import com.greenfox.treasuryauctionsystem.services.AuctionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auction")
public class AuctionController {


  private AuctionService auctionService;

  public AuctionController(AuctionService auctionService) {
    this.auctionService = auctionService;
  }



}
