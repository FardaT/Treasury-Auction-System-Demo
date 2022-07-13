package com.greenfox.treasuryauctionsystem.controllers;

import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.models.Auction;
import com.greenfox.treasuryauctionsystem.models.Bid;
import com.greenfox.treasuryauctionsystem.models.dtos.AuctionResponseDTO;
import com.greenfox.treasuryauctionsystem.models.dtos.BidDTO;
import com.greenfox.treasuryauctionsystem.models.dtos.BidsCreationDto;
import com.greenfox.treasuryauctionsystem.services.AppUserService;
import com.greenfox.treasuryauctionsystem.services.AuctionService;
import com.greenfox.treasuryauctionsystem.services.BidService;
import com.greenfox.treasuryauctionsystem.utils.ApplicationDetails;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
    public String read(Model model, Principal principal) {

        // get currently logged in user
        AppUser currentUser = appUserService.getUserByUsername(principal.getName());
        model.addAttribute("user", currentUser);

        // get all the bids
        List<Bid> bids = bidService.getAllBids();
        model.addAttribute("bids", bids);

        return "admin/bids";
    }

    // CREATE - show form
    @GetMapping("bids/create")
    public String create(
            Model model,
            Principal principal,
            @RequestParam Long auction_id,
            RedirectAttributes redirectAttributes) {

        // get currently logged in user
        AppUser currentUser = appUserService.getUserByUsername(principal.getName());
        model.addAttribute("user", currentUser);

        // find the auction
        Auction auction = auctionService.findById(auction_id);
        AuctionResponseDTO auctionResponseDTO = new AuctionResponseDTO(auction);
        model.addAttribute("auction", auctionResponseDTO);

        // As we can see, we passed a list of 3 empty Book objects to the view via the wrapper class.
        BidsCreationDto bidsForm = new BidsCreationDto();
        for (int i = 0; i <= auctionResponseDTO.getTreasurySecurityList().size(); i++) {
            bidsForm.addBid(new BidDTO());
        }

        model.addAttribute("form", bidsForm);
        model.addAttribute("currency", ApplicationDetails.currency);

        // you can only place bids for ONGOING auctions
        if (auction.getAuctionStartDate().isBefore(LocalDateTime.now()) && auction.getAuctionEndDate().isAfter(LocalDateTime.now())) {
            return "admin/bids_create_form";
        } else {
            redirectAttributes.addFlashAttribute("BIDDING_CLOSED", "This auction is either upcoming or finished");
            return "redirect:/auctions";
        }
    }

    // STORE
    @PostMapping("bids/store")
    public String store(
            @ModelAttribute BidsCreationDto form,
            Principal principal,
            RedirectAttributes redirectAttributes,
            @RequestParam String auction_id) {

        List<BidDTO> bids = form.getBidDTOS();

        // get currently logged in user
        AppUser currentUser = appUserService.getUserByUsername(principal.getName());

        Auction auction = auctionService.findById(Long.valueOf(auction_id));
        Map<String, String> saveResultMessage = bidService.saveBid(bids, currentUser, auction);

        // validations
        if (!saveResultMessage.isEmpty()) {

            if (saveResultMessage.containsKey("AT_LEAST_ONE")) {
                redirectAttributes.addFlashAttribute("AT_LEAST_ONE", saveResultMessage.get("AT_LEAST_ONE"));
                return "redirect:/admin/bids/create?auction_id=" + auction_id;
            }

            for (String i : saveResultMessage.keySet()) {

                for (int counter = 0; counter < auction.getTreasurySecurityList().size(); counter++) {
                    if (saveResultMessage.containsKey("ONE_BID_" + counter)) {
                        redirectAttributes.addFlashAttribute("ONE_BID_" + counter, saveResultMessage.get("ONE_BID_" + counter));
                    }
                    if (saveResultMessage.containsKey("AMOUNT_POSITIVE_" + counter)) {
                        redirectAttributes.addFlashAttribute("AMOUNT_POSITIVE_" + counter, saveResultMessage.get("AMOUNT_POSITIVE_" + counter));
                    }
                    if (saveResultMessage.containsKey("AMOUNT_HUNDRED_" + counter)) {
                        redirectAttributes.addFlashAttribute("AMOUNT_HUNDRED_" + counter, saveResultMessage.get("AMOUNT_HUNDRED_" + counter));
                    }
                    if (saveResultMessage.containsKey("AMOUNT_COMPETITIVE_" + counter)) {
                        redirectAttributes.addFlashAttribute("AMOUNT_COMPETITIVE_" + counter, saveResultMessage.get("AMOUNT_COMPETITIVE_" + counter));
                    }
                    if (saveResultMessage.containsKey("RATE_RANGE_" + counter)) {
                        redirectAttributes.addFlashAttribute("RATE_RANGE_" + counter, saveResultMessage.get("RATE_RANGE_" + counter));
                    }
                }

            }

            if (saveResultMessage.containsKey("AMOUNT_NONCOMPETITIVE")) {
                redirectAttributes.addFlashAttribute("AMOUNT_NONCOMPETITIVE", saveResultMessage.get("AMOUNT_NONCOMPETITIVE"));
            }

            return "redirect:/bids/create?auction_id=" + auction_id;
        } else {
            return "redirect:/bids";
        }
    }

    // DESTROY
    @PostMapping("bids/destroy")
    public String destroy(@RequestParam Long bidId) {

        bidService.disableBid(bidId);

        return "redirect:/admin/bids";
    }
}
