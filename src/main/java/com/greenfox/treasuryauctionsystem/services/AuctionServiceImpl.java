package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.exceptions.InvalidAuctionException;
import com.greenfox.treasuryauctionsystem.models.Auction;
import com.greenfox.treasuryauctionsystem.models.TreasurySecurity;
import com.greenfox.treasuryauctionsystem.models.dtos.AuctionDateDTO;
import com.greenfox.treasuryauctionsystem.models.dtos.AuctionResponseDTO;
import com.greenfox.treasuryauctionsystem.models.dtos.TempSecurityDTO;
import com.greenfox.treasuryauctionsystem.repositories.AuctionRepository;
import com.greenfox.treasuryauctionsystem.utils.TreasurySecurityTermConstraint;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class AuctionServiceImpl implements AuctionService {


  private AuctionRepository auctionRepository;

  @Autowired
  public AuctionServiceImpl(AuctionRepository auctionRepository) {
    this.auctionRepository = auctionRepository;
  }

  @Override
  public Map<String, List<AuctionResponseDTO>> getAllAuctionsByStatus()
      throws NullPointerException {

    Map<String, List<AuctionResponseDTO>> auctionsMap = new HashMap<>();
    auctionsMap.put("finished", null);
    auctionsMap.put("upcoming", null);
    auctionsMap.put("ongoing", null);

    List<Auction> allAuctions = auctionRepository.findAll();

    if (allAuctions == null) {
      throw new NullPointerException();
    }
    for (Auction auction : allAuctions) {
      if (!auction.isDisabled()) {
        if (auction.getAuctionEndDate().isBefore(LocalDateTime.now())) {
          if (auctionsMap.get("finished") == null) {
            auctionsMap.put("finished", new ArrayList<>());
          }
          List<AuctionResponseDTO> finishedList = auctionsMap.get("finished");
          finishedList.add(new AuctionResponseDTO(auction));
          auctionsMap.put("finished", finishedList);
        } else if (auction.getAuctionStartDate().isAfter(LocalDateTime.now())) {
          if (auctionsMap.get("upcoming") == null) {
            auctionsMap.put("upcoming", new ArrayList<>());
          }
          List<AuctionResponseDTO> upcomingList = auctionsMap.get("upcoming");
          upcomingList.add(new AuctionResponseDTO(auction));
          auctionsMap.put("upcoming", upcomingList);
        } else {
          if (auctionsMap.get("ongoing") == null) {
            auctionsMap.put("ongoing", new ArrayList<>());
          }
          List<AuctionResponseDTO> ongoingList = auctionsMap.get("ongoing");
          ongoingList.add(new AuctionResponseDTO(auction));
          auctionsMap.put("ongoing", ongoingList);
        }
      }
    }
    return auctionsMap;
  }

  @Override
  public void disable(Long id) {
    Optional<Auction> auction = auctionRepository.findById(id);
    if (auction.isPresent()) {
      Auction currentAuction = auction.get();
      currentAuction.setDisabled(true);
      auctionRepository.save(currentAuction);
    }
  }

  @Override
  public void process(Long id) {
    // TODO: 2022. 06. 30. function must be coded
    Optional<Auction> auction = auctionRepository.findById(id);
    if (auction.isPresent() && auction.get().getAuctionEndDate().isBefore(LocalDateTime.now())) {
      Auction currentAuction = auction.get();
      currentAuction.setProcessed(true);
      auctionRepository.save(currentAuction);
    }
  }
  @Override
  public void create(Auction auction) {
    Map<String, String> errors = new HashMap<>();
    if (auction.getTreasurySecurityList().isEmpty()){
      throw new InvalidAuctionException("Auction must contain security");
    }
    auctionRepository.save(auction);
  }

  @Override
  public Map<String, String> validateSecurityForAuction(Auction auction, TempSecurityDTO treasurySecurity) {
    Map<String, String> errors = new HashMap<>();
    List<TreasurySecurity> securityList = auction.getTreasurySecurityList();
    for (TreasurySecurity ts : securityList) {
      if (ts.getSecurityName().equals(treasurySecurity.getSecurityName())){
        errors.put("INVALID_SECURITY_ERROR", "Auction cannot contain duplicate securities");
      }
    }
    if(treasurySecurity.getIssueDate() == null){
      errors.put("ISSUE_DATE_ERROR","Issue date cannot be null");
      return errors;
    }
    if(treasurySecurity.getIssueDate().isBefore(ChronoLocalDate.from(auction.getAuctionEndDate().plusDays(1)))){
      errors.put("ISSUE_DATE_ERROR","Issue date must take place after the auction");
    }
    if(treasurySecurity.getTotalAmount() < 1000000){
      errors.put("TOTALAMOUNT_ERROR","Total amount must be at least 10.000");
    }
    if(treasurySecurity.getTotalAmount() > 100000000){
      errors.put("TOTALAMOUNT_ERROR","Total amount must not exceed 1.000.000");
    }
    if(treasurySecurity.getSecurityType() == null){
      errors.put("INVALID_SECURITY_ERROR","Security type cannot be null");
      return errors;
    }
    if(treasurySecurity.getMaturityDate() == null){
      errors.put("INVALID_SECURITY_ERROR","Maturity date cannot be null");
      return errors;
    }
    if(!TreasurySecurityTermConstraint.validSecurities.contains(treasurySecurity.getSecurityType())){
      errors.put("INVALID_SECURITY_ERROR","Invalid treasury security");
    }
    if(treasurySecurity.getSecurityTerm() == null){
      errors.put("SECURITY_TERM_ERROR","Security term cannot be null");
      return errors;
    }
    if(treasurySecurity.getSecurityType().equals("T-Bill")){
      if (!TreasurySecurityTermConstraint.validBillTerm.contains(treasurySecurity.getSecurityTerm())){
        errors.put("SECURITY_TERM_ERROR","Bill term out of bound");
      }
    }
    if(treasurySecurity.getSecurityType().equals("T-Note")){
      if(!TreasurySecurityTermConstraint.validNoteTerm.contains(treasurySecurity.getSecurityTerm())){
        errors.put("SECURITY_TERM_ERROR","Note term out of bound");
      }
    }
    if(treasurySecurity.getSecurityType().equals("T-Bond")){
      if (!TreasurySecurityTermConstraint.validBondTerm.contains(treasurySecurity.getSecurityTerm())){
        errors.put("SECURITY_TERM_ERROR","Bond term out of bound");
      }
    }
    return errors;
  }
  @Override
  public Auction setDateToAuction(Auction auction, AuctionDateDTO auctionDateDTO) {
    if(auctionDateDTO.getAuctionStartDate() == null){
      throw new InvalidAuctionException("Auction start time cannot be null");
    }
    if(auctionDateDTO.getAuctionEndDate() == null){
      throw new InvalidAuctionException("Auction end time cannot be null");
    }
    if(auctionDateDTO.getAuctionStartDate().isBefore(LocalDateTime.now())){
      throw new InvalidAuctionException("Auction start date out of bound");
    }
    if(auctionDateDTO.getAuctionEndDate().isBefore(LocalDateTime.now())){
      throw new InvalidAuctionException("Auction end date out of bound");
    }
    if(auctionDateDTO.getAuctionEndDate().isBefore(auctionDateDTO.getAuctionStartDate())){
      throw new InvalidAuctionException("Invalid auction end time");
    }
    auction.setAuctionStartDate(auctionDateDTO.getAuctionStartDate());
    auction.setAuctionEndDate(auctionDateDTO.getAuctionEndDate());
    return auction;
  }
}
