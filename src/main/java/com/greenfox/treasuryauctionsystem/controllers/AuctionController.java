package com.greenfox.treasuryauctionsystem.controllers;

import com.greenfox.treasuryauctionsystem.exceptions.NoSuchAuctionException;
import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.models.dtos.AuctionResponseDTO;
import com.greenfox.treasuryauctionsystem.services.AppUserService;
import com.greenfox.treasuryauctionsystem.services.AuctionService;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auctions")
public class AuctionController {


  private final AuctionService auctionService;
  private final AppUserService appUserService;

  @Autowired
  public AuctionController(AuctionService auctionService, AppUserService appUserService) {
    this.auctionService = auctionService;
    this.appUserService = appUserService;
  }

  @GetMapping()
  public String showAllAuctions(Model model, Principal principal) {

    // get currently logged in user
    AppUser currentUser = appUserService.getUserByUsername(principal.getName());
    model.addAttribute("user", currentUser);

    Map<String, List<AuctionResponseDTO>> auctions = auctionService.getAllAuctionsByStatus();
    model.addAttribute("auctions", auctions);

    return "/admin/auctionslist";
  }

  @PostMapping("/disable")
  public String deleteAuctionPost(@RequestParam Long id) {
    auctionService.disable(id);
    return "redirect:/auctions";
  }

//    @GetMapping("/process/{id}")
//    public String processFinishedAuction(@PathVariable Long id) {
//        auctionService.process(id);
//        return "redirect:/auctions";
//    }

  @PostMapping("/process")
  public String processFinishedAuctionPost(@RequestParam Long id) throws NoSuchAuctionException {
    auctionService.process(id);
    return "redirect:/auctions";
  }
}
