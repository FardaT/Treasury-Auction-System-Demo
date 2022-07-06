package com.greenfox.treasuryauctionsystem.controllers;

import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.models.Auction;
import com.greenfox.treasuryauctionsystem.models.Bid;
import com.greenfox.treasuryauctionsystem.models.dtos.AuctionResponseDTO;
import com.greenfox.treasuryauctionsystem.models.dtos.BidDTO;
import com.greenfox.treasuryauctionsystem.services.AppUserService;
import com.greenfox.treasuryauctionsystem.services.AuctionService;
import com.greenfox.treasuryauctionsystem.services.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class BidController {

    // DI
    private final AppUserService appUserService;
    private final AuctionService auctionService;
    private final BidService bidService;

    @Autowired
    public BidController(AppUserService appUserService, AuctionService auctionService, BidService bidService) {
        this.appUserService = appUserService;
        this.auctionService = auctionService;
        this.bidService = bidService;
    }

    // READ
    @GetMapping("admin/bids")
    public String read(Model model, HttpServletRequest request) {

        // get currently logged in user
        AppUser currentUser = appUserService.getUserFromRequest(request);
        model.addAttribute("user", currentUser);

        // get all the bids
        List<Bid> bids = bidService.getAllBids();
        model.addAttribute("bids", bids);

        return "admin/bids";
    }

    // CREATE - show form
    @GetMapping("admin/bids/create")
    public String create(Model model, HttpServletRequest request, @RequestParam Long auction_id) {

        // get currently logged in user
        AppUser currentUser = appUserService.getUserFromRequest(request);
        model.addAttribute("user", currentUser);

        // find the auction
        Auction auction = auctionService.findById(auction_id);
        AuctionResponseDTO auctionResponseDTO = new AuctionResponseDTO(auction);
        model.addAttribute("auction", auctionResponseDTO);

        return "admin/bids_create_form";
    }

    // STORE
    @PostMapping("admin/bids/store")
    public String store(BidDTO bidDTO, HttpServletRequest request) {

        // get currently logged in user
        AppUser currentUser = appUserService.getUserFromRequest(request);
        bidDTO.setUser(currentUser);

        bidService.saveBid(new Bid(bidDTO));
        return "redirect:/admin/bids";
    }

    // DESTROY
    @PostMapping("admin/bids/destroy")
    public String destroy(@RequestParam Long bidId) {

        bidService.disableBid(bidId);

        return "redirect:/admin/bids";
    }
}
