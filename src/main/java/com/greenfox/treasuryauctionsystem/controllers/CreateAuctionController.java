package com.greenfox.treasuryauctionsystem.controllers;

import com.greenfox.treasuryauctionsystem.models.Auction;
import com.greenfox.treasuryauctionsystem.models.TreasurySecurity;
import com.greenfox.treasuryauctionsystem.models.dtos.AuctionDateDTO;
import com.greenfox.treasuryauctionsystem.services.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller("/admin")
public class CreateAuctionController {
  private final AuctionService auctionService;
  private Auction newAuction = new Auction();
  @Autowired
  public CreateAuctionController(AuctionService auctionService) {
    this.auctionService = auctionService;
  }

  @GetMapping("/auctions/create")
  public String renderAuctionCreationPage(Model model){
    model.addAttribute("newAuction", newAuction);
    return "create-auction";
  }

  @PostMapping("/auctions/set-time")
  public String setAuctionDate(@ModelAttribute(name="auctionTime")AuctionDateDTO auctionDateDTO){
    auctionService.setDateToAuction(newAuction,auctionDateDTO);
    return "redirect:/admin/auctions/create";
  }

  @PostMapping("/auctions/add-security")
  public String addSecurityToAuction(@ModelAttribute TreasurySecurity treasurySecurity){
    auctionService.addSecurityToAuction(newAuction,treasurySecurity);
    return "redirect:/admin/auctions/create";
  }

  @PostMapping("/auctions/create")
  public String createAuction(){
    auctionService.create(newAuction);
    return "redirect:/auctions";
  }

}
