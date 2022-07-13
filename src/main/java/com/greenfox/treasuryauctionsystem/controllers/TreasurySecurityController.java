package com.greenfox.treasuryauctionsystem.controllers;

import com.greenfox.treasuryauctionsystem.models.dtos.TreasurySecurityResponseDTO;
import com.greenfox.treasuryauctionsystem.services.TreasurySecurityService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/securities")
public class TreasurySecurityController {


  private final TreasurySecurityService treasurySecurityService;

  @Autowired
  public TreasurySecurityController(TreasurySecurityService treasurySecurityService) {
    this.treasurySecurityService = treasurySecurityService;
  }

  @GetMapping
  public String getAllTreasurySecurities(Model model,
                                         @RequestParam(defaultValue = "type") String ongoingSortBy,
                                         @RequestParam(defaultValue = "asc") String ongoingOrder,
                                         @RequestParam(defaultValue = "type") String upcomingSortBy,
                                         @RequestParam(defaultValue = "asc") String upcomingOrder) {

    List<TreasurySecurityResponseDTO> ongoingAuctionSecurityList =
        treasurySecurityService.getTreasurySecurities(true, ongoingSortBy, ongoingOrder);

    List<TreasurySecurityResponseDTO> upcomingAuctionSecurityList =
        treasurySecurityService.getTreasurySecurities(false, upcomingSortBy, upcomingOrder);

    model.addAttribute("ongoing", ongoingAuctionSecurityList);
    model.addAttribute("upcoming", upcomingAuctionSecurityList);

    return "admin/securities";
  }
}
