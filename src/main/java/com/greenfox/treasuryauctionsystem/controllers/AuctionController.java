package com.greenfox.treasuryauctionsystem.controllers;

import com.greenfox.treasuryauctionsystem.models.dtos.AuctionResponseDTO;
import com.greenfox.treasuryauctionsystem.services.AuctionService;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auctions")
public class AuctionController {


  private AuctionService auctionService;

  public AuctionController(AuctionService auctionService) {
    this.auctionService = auctionService;
  }

  @GetMapping()
  public String showAllAuctions(Model model) {
    Map<String, List<AuctionResponseDTO>> auctions = auctionService.getAllAuctionsByStatus();
    model.addAttribute("auctions", auctions);
    // TODO: 2022. 06. 29. add template

    System.out.println(auctions.get("ongoing").get(0).getId());
    return "auctionslist";
  }
}
