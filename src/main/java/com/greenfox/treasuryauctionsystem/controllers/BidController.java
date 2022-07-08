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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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

        // As we can see, we passed a list of 3 empty Book objects to the view via the wrapper class.
        BidsCreationDto bidsForm = new BidsCreationDto();
        for (int i = 0; i <= auctionResponseDTO.getTreasurySecurityList().size(); i++) {
            bidsForm.addBid(new BidDTO());
        }

        model.addAttribute("form", bidsForm);

        return "admin/bids_create_form";
    }

    // STORE
    @PostMapping("admin/bids/store")
    public String store(
            @ModelAttribute BidsCreationDto form,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes,
            @RequestParam String auction_id) {

        List<BidDTO> bids = form.getBidDTOS();

        // get currently logged in user
        AppUser currentUser = appUserService.getUserFromRequest(request);

        Map<String, String> saveResultMessage = bidService.saveBid(bids, currentUser);

         // validations
        if (!saveResultMessage.isEmpty()) {

            if (saveResultMessage.containsKey("AT_LEAST_ONE")) {
                redirectAttributes.addFlashAttribute("AT_LEAST_ONE", saveResultMessage.get("AT_LEAST_ONE"));
                return "redirect:/admin/bids/create?auction_id=" + auction_id;
            }

            int index = 0;
            for (String i : saveResultMessage.keySet()) {

                if (saveResultMessage.containsKey("AMOUNT_POSITIVE_" + index)) {
                    redirectAttributes.addFlashAttribute("AMOUNT_POSITIVE_" + index, saveResultMessage.get("AMOUNT_POSITIVE_" + index));
                }
                if (saveResultMessage.containsKey("AMOUNT_HUNDRED_" + index)) {
                    redirectAttributes.addFlashAttribute("AMOUNT_HUNDRED_" + index, saveResultMessage.get("AMOUNT_HUNDRED_" + index));
                }
                if (saveResultMessage.containsKey("AMOUNT_COMPETITIVE_" + index)) {
                    redirectAttributes.addFlashAttribute("AMOUNT_COMPETITIVE_" + index, saveResultMessage.get("AMOUNT_COMPETITIVE_" + index));
                }
                if (saveResultMessage.containsKey("RATE_RANGE_" + index)) {
                    redirectAttributes.addFlashAttribute("RATE_RANGE_" + index, saveResultMessage.get("RATE_RANGE_" + index));
                }

                index++;
            }

            if (saveResultMessage.containsKey("AMOUNT_NONCOMPETITIVE")) {
                redirectAttributes.addFlashAttribute("AMOUNT_NONCOMPETITIVE", saveResultMessage.get("AMOUNT_NONCOMPETITIVE"));
            }

            return "redirect:/admin/bids/create?auction_id=" + auction_id;
        } else {
            return "redirect:/admin/bids";
        }
    }

    // DESTROY
    @PostMapping("admin/bids/destroy")
    public String destroy(@RequestParam Long bidId) {

        bidService.disableBid(bidId);

        return "redirect:/admin/bids";
    }
}
