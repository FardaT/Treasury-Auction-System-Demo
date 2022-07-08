package com.greenfox.treasuryauctionsystem.controllers;

import com.greenfox.treasuryauctionsystem.exceptions.InvalidAuctionException;
import com.greenfox.treasuryauctionsystem.models.Auction;
import com.greenfox.treasuryauctionsystem.models.TreasurySecurity;
import com.greenfox.treasuryauctionsystem.models.dtos.AuctionDateDTO;
import com.greenfox.treasuryauctionsystem.models.dtos.TempSecurityDTO;
import com.greenfox.treasuryauctionsystem.services.AuctionService;
import java.security.Principal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("admin")
@Scope("session")
public class CreateAuctionController {
  private final AuctionService auctionService;
  private Auction tempAuction = new Auction();
  @Autowired
  public CreateAuctionController(AuctionService auctionService) {
    this.auctionService = auctionService;
  }
  @GetMapping("/auctions/create")
  public String renderAuctionCreationPage(Model model, Principal principal){
    model.addAttribute("user", principal);
    model.addAttribute("newAuction", tempAuction);
    return "admin/create-auction";
  }

  @PostMapping("/auctions/add-security")
  public String addSecurityToTempAuction(@ModelAttribute TempSecurityDTO tempSecurityDTO, RedirectAttributes redirectAttributes){
    Map<String, String> addSecurityResultMessage = auctionService.validateSecurityForAuction(
        tempAuction,tempSecurityDTO);
    if(!addSecurityResultMessage.isEmpty()){
      if(addSecurityResultMessage.containsKey("ISSUE_DATE_ERROR")){
        redirectAttributes.addFlashAttribute("ISSUE_DATE_ERROR",addSecurityResultMessage.get("ISSUE_DATE_ERROR"));
      }
      if(addSecurityResultMessage.containsKey("TOTALAMOUNT_ERROR")){
        redirectAttributes.addFlashAttribute("TOTALAMOUNT_ERROR",addSecurityResultMessage.get("TOTALAMOUNT_ERROR"));
      }
      if(addSecurityResultMessage.containsKey("INVALID_SECURITY_ERROR")){
        redirectAttributes.addFlashAttribute("INVALID_SECURITY_ERROR",addSecurityResultMessage.get("INVALID_SECURITY_ERROR"));
      }
      if(addSecurityResultMessage.containsKey("SECURITY_TERM_ERROR")){
        redirectAttributes.addFlashAttribute("SECURITY_TERM_ERROR",addSecurityResultMessage.get("SECURITY_TERM_ERROR"));
      }
      redirectAttributes.addFlashAttribute("errorSecurityValue", tempSecurityDTO);
      return "redirect:/admin/auctions/create";
    } else {
      TreasurySecurity treasurySecurity = new TreasurySecurity(tempSecurityDTO);
      tempAuction.addTreasurySecurity(treasurySecurity);
      return "redirect:/admin/auctions/create";
    }
  }

  @PostMapping("/auctions/create")
  public String createAuction(@ModelAttribute(name="auctionTime")AuctionDateDTO auctionDateDTO, RedirectAttributes redirectAttributes){
    try{
      tempAuction = auctionService.setDateToAuction(tempAuction,auctionDateDTO);
      auctionService.create(tempAuction);
      tempAuction = new Auction();
    }catch (InvalidAuctionException ex){
      redirectAttributes.addFlashAttribute("INVALID_AUCTION",ex.getMessage());
      return "redirect:/admin/auctions/create";
    }
    return "redirect:/auctions";
  }
  @PostMapping("/auctions/cancel-creation")
  public String createAuction(){
    tempAuction = new Auction();
    return "redirect:/auctions";
  }

  @PostMapping("/auctions/remove-security/{securityName}")
  public String deleteSecurityfromTempAuction(@PathVariable("securityName")String securityName){
    for (TreasurySecurity sec: tempAuction.getTreasurySecurityList()) {
      if(sec.getSecurityName().replace(' ','_').equals(securityName)){
        tempAuction.removeTreasurySecurity(sec);
        return "redirect:/admin/auctions/create";
      }
    }
    return "redirect:/admin/auctions/create";
  }
}
