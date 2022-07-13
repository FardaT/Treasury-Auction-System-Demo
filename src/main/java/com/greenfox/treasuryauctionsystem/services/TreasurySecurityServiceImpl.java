package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.models.Auction;
import com.greenfox.treasuryauctionsystem.models.TreasurySecurity;
import com.greenfox.treasuryauctionsystem.models.comparators.TreasurySecurityIssueDateAscComparator;
import com.greenfox.treasuryauctionsystem.models.comparators.TreasurySecurityIssueDateDescComparator;
import com.greenfox.treasuryauctionsystem.models.comparators.TreasurySecurityMaturityDateAscComparator;
import com.greenfox.treasuryauctionsystem.models.comparators.TreasurySecurityMaturityDateDescComparator;
import com.greenfox.treasuryauctionsystem.models.comparators.TreasurySecurityNameAscComparator;
import com.greenfox.treasuryauctionsystem.models.comparators.TreasurySecurityNameDescComparator;
import com.greenfox.treasuryauctionsystem.models.comparators.TreasurySecurityTotalAmountAscComparator;
import com.greenfox.treasuryauctionsystem.models.comparators.TreasurySecurityTotalAmountDescComparator;
import com.greenfox.treasuryauctionsystem.models.comparators.TreasurySecurityTypeAscComparator;
import com.greenfox.treasuryauctionsystem.models.comparators.TreasurySecurityTypeDescComparator;
import com.greenfox.treasuryauctionsystem.models.dtos.TreasurySecurityResponseDTO;
import com.greenfox.treasuryauctionsystem.repositories.AuctionRepository;
import com.greenfox.treasuryauctionsystem.repositories.TreasurySecurityRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TreasurySecurityServiceImpl implements TreasurySecurityService {

  private TreasurySecurityRepository treasurySecurityRepository;
  private AuctionRepository auctionRepository;

  @Autowired
  public TreasurySecurityServiceImpl(TreasurySecurityRepository treasurySecurityRepository,
                                     AuctionRepository auctionRepository) {
    this.treasurySecurityRepository = treasurySecurityRepository;
    this.auctionRepository = auctionRepository;
  }


  @Override
  public List<TreasurySecurityResponseDTO> getTreasurySecurities(boolean isOngoing,
                                                                 String sortBy,
                                                                 String order) {

    LocalDateTime now = LocalDateTime.now();

    List<Auction> auctionList;

    //fill auctionList with the corresponding auction data
    if (isOngoing) {
      auctionList =
          auctionRepository.findAuctionByAuctionStartDateLessThanAndAuctionEndDateGreaterThan(now,
              now);
    } else {
      auctionList = auctionRepository.findAuctionByAuctionStartDateGreaterThan(now);
    }

    //fill treasurySecurityList with all securities
    List<TreasurySecurity> treasurySecurityList = new ArrayList<>();
    for (Auction auction : auctionList) {
      treasurySecurityList.addAll(auction.getTreasurySecurityList());
    }

    Comparator<TreasurySecurity> comparator = new TreasurySecurityTypeAscComparator();


    if (order.equals("asc")) {
      if (sortBy.equals("type")) {
        comparator = new TreasurySecurityTypeAscComparator();
      }
      if (sortBy.equals("name")) {
        comparator = new TreasurySecurityNameAscComparator();
      }
      if (sortBy.equals("issueDate")) {
        comparator = new TreasurySecurityIssueDateAscComparator();
      }
      if (sortBy.equals("maturityDate")) {
        comparator = new TreasurySecurityMaturityDateAscComparator();
      }
      if (sortBy.equals("totalAmount")) {
        comparator = new TreasurySecurityTotalAmountAscComparator();
      }
    } else {
      if (sortBy.equals("type")) {
        comparator = new TreasurySecurityTypeDescComparator();
      }
      if (sortBy.equals("name")) {
        comparator = new TreasurySecurityNameDescComparator();
      }
      if (sortBy.equals("issueDate")) {
        comparator = new TreasurySecurityIssueDateDescComparator();
      }
      if (sortBy.equals("maturityDate")) {
        comparator = new TreasurySecurityMaturityDateDescComparator();
      }
      if (sortBy.equals("totalAmount")) {
        comparator = new TreasurySecurityTotalAmountDescComparator();
      }
    }

    Collections.sort(treasurySecurityList, comparator);

    List<TreasurySecurityResponseDTO> treasurySecurityResponseDTOList = new ArrayList<>();
    for (TreasurySecurity treasurySecurity : treasurySecurityList) {
      treasurySecurityResponseDTOList.add(new TreasurySecurityResponseDTO(treasurySecurity));
    }
    return treasurySecurityResponseDTOList;
  }
}
