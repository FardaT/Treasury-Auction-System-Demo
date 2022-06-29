package com.greenfox.treasuryauctionsystem.controllers;

import com.greenfox.treasuryauctionsystem.models.dtos.AuctionResponseDTO;
import com.greenfox.treasuryauctionsystem.services.AuctionService;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    return "auctionslist";
  }

  //disable auction by id
  @GetMapping("/disable/{id}")
  public String deleteAuction(@PathVariable Long id){
    auctionService.disable(id);
    return "redirect:/auctions";
  }
}
