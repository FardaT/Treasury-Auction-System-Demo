package com.greenfox.treasuryauctionsystem.controllers;

import com.greenfox.treasuryauctionsystem.exceptions.NoSuchAuctionException;
import com.greenfox.treasuryauctionsystem.models.dtos.AuctionResponseDTO;
import com.greenfox.treasuryauctionsystem.services.AuctionService;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    return "/admin/auctionslist";
  }

  @PostMapping("/disable")
  public String deleteAuctionPost(@RequestParam Long id){
    auctionService.disable(id);
    return "redirect:/auctions";
  }

  @PostMapping("/process")
  public String processFinishedAuctionPost(@RequestParam Long id) throws NoSuchAuctionException {
    auctionService.process(id);
    return "redirect:/auctions";
  }
}
