package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.exceptions.InvalidAuctionException;
import com.greenfox.treasuryauctionsystem.exceptions.NoSuchAuctionException;
import com.greenfox.treasuryauctionsystem.models.Auction;
import com.greenfox.treasuryauctionsystem.models.Bid;
import com.greenfox.treasuryauctionsystem.models.NonCompetitiveBidComparator;
import com.greenfox.treasuryauctionsystem.models.TreasurySecurity;
import com.greenfox.treasuryauctionsystem.models.dtos.AuctionApiResponseDTO;
import com.greenfox.treasuryauctionsystem.models.dtos.AuctionDateDTO;
import com.greenfox.treasuryauctionsystem.models.dtos.AuctionResponseDTO;
import com.greenfox.treasuryauctionsystem.models.dtos.TempSecurityDTO;
import com.greenfox.treasuryauctionsystem.repositories.AuctionRepository;
import com.greenfox.treasuryauctionsystem.repositories.BidRepository;
import com.greenfox.treasuryauctionsystem.repositories.TreasurySecurityRepository;
import com.greenfox.treasuryauctionsystem.utils.TreasurySecurityTermConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.*;

@Service

public class AuctionServiceImpl implements AuctionService {


	private final AuctionRepository auctionRepository;
	private final TreasurySecurityRepository treasurySecurityRepository;

	private final BidRepository bidRepository;

	@Value("${highrate}")
	String systemHighRate;


	@Autowired
	public AuctionServiceImpl (AuctionRepository auctionRepository,
	                           TreasurySecurityRepository treasurySecurityRepository,
	                           BidRepository bidRepository) {
		this.auctionRepository = auctionRepository;
		this.treasurySecurityRepository = treasurySecurityRepository;
		this.bidRepository = bidRepository;
	}

	@Override
	public Map<String, List<AuctionResponseDTO>> getAllAuctionsByStatus () {

		Map<String, List<AuctionResponseDTO>> auctionsMap = new HashMap<>();
		auctionsMap.put("finished", null);
		auctionsMap.put("upcoming", null);
		auctionsMap.put("ongoing", null);

		List<Auction> allAuctions = auctionRepository.findAll();

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
	public List<AuctionApiResponseDTO> getAllUpcomingAuctions () {
		List<AuctionApiResponseDTO> returnResponseDTO = new ArrayList<>();
		List<Auction> allAuctions = auctionRepository.findAll();

		for (Auction auction : allAuctions) {
			if (!auction.isDisabled()) {
				if (auction.getAuctionStartDate().isAfter(LocalDateTime.now())) {
					returnResponseDTO.add(new AuctionApiResponseDTO(auction));
				}
			}
		}
		return returnResponseDTO;
	}

	@Override
	public List<AuctionApiResponseDTO> getAllFinishedAuctions () {
		List<AuctionApiResponseDTO> returnResponseDTO = new ArrayList<>();
		List<Auction> allAuctions = auctionRepository.findAll();

		for (Auction auction : allAuctions) {
			if (!auction.isDisabled()) {
				if (auction.getAuctionEndDate().isBefore(LocalDateTime.now())) {
					returnResponseDTO.add(new AuctionApiResponseDTO(auction));
				}
			}
		}
		return returnResponseDTO;
	}

	@Override
	public List<AuctionApiResponseDTO> getAllOngoingAuctions () {
		List<AuctionApiResponseDTO> returnResponseDTO = new ArrayList<>();
		List<Auction> allAuctions = auctionRepository.findAll();

		for (Auction auction : allAuctions) {
			if (!auction.isDisabled()) {
				if (auction.getAuctionEndDate().isAfter(LocalDateTime.now())
						&& auction.getAuctionStartDate().isBefore(LocalDateTime.now())){
					returnResponseDTO.add(new AuctionApiResponseDTO(auction));
				}
			}
		}
		return returnResponseDTO;
	}


	@Override
	public void disable (Long id) throws NoSuchAuctionException {
		Optional<Auction> auction = auctionRepository.findById(id);
		//todo check if it is upcoming auction
		if (auction.isPresent()) {
			Auction currentAuction = auction.get();
			if (currentAuction.getAuctionStartDate().isBefore(LocalDateTime.now())) {
				throw new NoSuchAuctionException("No upcoming auctions in the database");
			}
			currentAuction.setDisabled(true);
			auctionRepository.save(currentAuction);
		}
	}

	@Override
	@Transactional
	public void process (Long id) throws NoSuchAuctionException {

		//get auction by id
		Optional<Auction> auctionOptional = auctionRepository.findById(id);
		Auction currentAuction;
		if (auctionOptional.isEmpty()) {
			throw new NoSuchAuctionException("No auction found with the provided Id.");
		} else {
			currentAuction = auctionOptional.get();
		}

		//get securities of the auction
		List<TreasurySecurity> treasurySecurityList = currentAuction.getTreasurySecurityList();

		//get total amount of the securities
		Map<Long, Long> totalAmountsBySecurities = new HashMap<>();
		for (TreasurySecurity treasurySecurity : treasurySecurityList) {
			totalAmountsBySecurities.put(treasurySecurity.getId(), treasurySecurity.getTotalAmount());
		}

		//map bids to treasurySecurity ids
		Map<Long, List<Bid>> bidListMap = new HashMap<>();
		for (TreasurySecurity treasurySecurity : treasurySecurityList) {
			List<Bid> bidList = treasurySecurity.getBidList();
			bidListMap.put(treasurySecurity.getId(), bidList);
		}

		// split to competitive and non-competitive maps
		Map<Long, List<Bid>> competitiveBidListMap = new HashMap<>();
		Map<Long, List<Bid>> nonCompetitiveBidListMap = new HashMap<>();

		for (Map.Entry<Long, List<Bid>> entry : bidListMap.entrySet()) {
			for (Bid bid : entry.getValue()) {
				// TODO: 2022. 07. 12. check if bid is not disabled HERE
				if (!bid.isDisabled()) {
					if (bid.isCompetitive()) {
						if (!competitiveBidListMap.containsKey(bid.getTreasurySecurity().getId())) {
							competitiveBidListMap.put(bid.getTreasurySecurity().getId(), new ArrayList<>());
						}
						competitiveBidListMap.get(entry.getKey()).add(bid);
					} else {
						if (!nonCompetitiveBidListMap.containsKey(bid.getTreasurySecurity().getId())) {
							nonCompetitiveBidListMap.put(bid.getTreasurySecurity().getId(), new ArrayList<>());
						}
						nonCompetitiveBidListMap.get(entry.getKey()).add(bid);
					}
				}
			}
		}

		//counts non-competitive offer amounts
		Map<Long, Long> totalNonCompetitiveBidAmount = new HashMap<>();

		for (Map.Entry<Long, List<Bid>> entry : nonCompetitiveBidListMap.entrySet()) {
			for (Bid bid : entry.getValue()) {
				if (!totalNonCompetitiveBidAmount.containsKey(bid.getTreasurySecurity().getId())) {
					totalNonCompetitiveBidAmount.put(bid.getTreasurySecurity().getId(), 0L);
				}
				Long currentAmount = bid.getAmount();
				totalNonCompetitiveBidAmount.put(entry.getKey(),
						totalNonCompetitiveBidAmount.get(entry.getKey()) + currentAmount);
			}
		}

		//sort the lists of the competitiveBidListMap and the nonCompetitiveBidListMap
		for (Map.Entry<Long, List<Bid>> entry : competitiveBidListMap.entrySet()) {
			List<Bid> currentBidList = entry.getValue();
			Collections.sort(currentBidList);
		}
		for (Map.Entry<Long, List<Bid>> entry : nonCompetitiveBidListMap.entrySet()) {
			List<Bid> currentBidList = entry.getValue();
			NonCompetitiveBidComparator nonCompetitiveBidComparator = new NonCompetitiveBidComparator();
			currentBidList.sort(nonCompetitiveBidComparator);
		}

		// checks non-competitive bids
		for (Map.Entry<Long, Long> entry : totalNonCompetitiveBidAmount.entrySet()) {
			//set all noncompetitive auctions setAcceptedValue and setAcceptedFlag
			if (entry.getValue() <= totalAmountsBySecurities.get(entry.getKey())) {
				for (Map.Entry<Long, List<Bid>> bidEntry : nonCompetitiveBidListMap.entrySet()) {
					for (Bid bid : bidEntry.getValue()) {
						bid.setAcceptedValue(bid.getAmount());
						bid.setAccepted(true);
						bid.setArchived(true);
						bidRepository.save(bid);
						long remainingAmount = totalAmountsBySecurities.get(entry.getKey()) - bid.getAmount();
						totalAmountsBySecurities.put(entry.getKey(), remainingAmount);
					}
				}
				//if non-competitve exceeds total amount this else block will handle the case
			} else {
				for (Map.Entry<Long, List<Bid>> bidEntry : nonCompetitiveBidListMap.entrySet()) {
					long remainingAmount = totalAmountsBySecurities.get(entry.getKey());
					for (Bid bid : bidEntry.getValue()) {
						if (remainingAmount > 0) {
							if (bid.getAmount() < remainingAmount) {
								bid.setAcceptedValue(bid.getAmount());
							} else {
								bid.setAcceptedValue(remainingAmount);
							}
							bid.setAccepted(true);
							bid.setArchived(true);
							bidRepository.save(bid);
							remainingAmount = remainingAmount - bid.getAmount();
						} else {
							bid.setAccepted(false);
							bid.setArchived(true);
							bid.setAcceptedValue(0);
							bidRepository.save(bid);
							remainingAmount = remainingAmount - bid.getAmount();
						}
					}
					totalAmountsBySecurities.put(entry.getKey(), remainingAmount);
				}
			}
		}
		//calculate security's high rate, set bid's isAccepted & acceptedValue
		Map<Long, Float> highRateMap =
				getHighRateMap(totalAmountsBySecurities, competitiveBidListMap);
		//set highRate of all treasurySecurities
		for (TreasurySecurity treasurySecurity : treasurySecurityList) {
			if (highRateMap.containsKey(treasurySecurity.getId())) {
				float finalHighRate = highRateMap.get(treasurySecurity.getId());
				treasurySecurity.setHighRate(finalHighRate);
				treasurySecurityRepository.save(treasurySecurity);
			}
		}
		//sets auction's isProcessed flag
		currentAuction.setProcessed(true);
		auctionRepository.save(currentAuction);
	}


	private Map<Long, Float> getHighRateMap (Map<Long, Long> totalAmountsBySecurities,
	                                         Map<Long, List<Bid>> competitiveBidListMap) {
		Map<Long, Float> highRateMap = new HashMap<>();

		for (Map.Entry<Long, List<Bid>> entry : competitiveBidListMap.entrySet()) {

			long remainingTotalAmountAfterNonCompetitiveBidsDeducted =
					totalAmountsBySecurities.get(entry.getKey());

			//if non-competitive exceeds the total amount all competitive bids will be refused
			if (remainingTotalAmountAfterNonCompetitiveBidsDeducted <= 0) {
				for (Bid bid : entry.getValue()) {
					bid.setAccepted(false);
					bid.setAcceptedValue(0);
				}
				//highrate is set to an arbitrary float rate
				highRateMap.put(entry.getKey(), Float.valueOf(systemHighRate));

			} else {

				boolean isExceeded = false;

				//check high rate & save bids & treasurySecurity & auction
				for (Bid bid : entry.getValue()) {
					if (remainingTotalAmountAfterNonCompetitiveBidsDeducted - bid.getAmount() > 0) {
						remainingTotalAmountAfterNonCompetitiveBidsDeducted -= bid.getAmount();
						bid.setAccepted(true);
						bid.setAcceptedValue(bid.getAmount());
						highRateMap.put(entry.getKey(), bid.getRate());
					} else if (remainingTotalAmountAfterNonCompetitiveBidsDeducted - bid.getAmount() == 0) {
						remainingTotalAmountAfterNonCompetitiveBidsDeducted -= bid.getAmount();
						bid.setAccepted(true);
						bid.setAcceptedValue(bid.getAmount());
						highRateMap.put(entry.getKey(), bid.getRate());
						isExceeded = true;
					} else {
						remainingTotalAmountAfterNonCompetitiveBidsDeducted -= bid.getAmount();
						if (!isExceeded) {
							bid.setAccepted(true);
							highRateMap.put(entry.getKey(), bid.getRate());
							long currentAcceptedValue =
									bid.getAmount() + remainingTotalAmountAfterNonCompetitiveBidsDeducted;
							bid.setAcceptedValue(currentAcceptedValue);
							isExceeded = true;
						} else {
							bid.setAccepted(false);
							bid.setAcceptedValue(0);
						}
					}
					bid.setArchived(true);
					bidRepository.save(bid);
				}
			}
		}
		return highRateMap;
	}

	// find auction by id
	@Override
	public Auction findById (Long auction_id) {
		return auctionRepository.findById(auction_id).orElse(null);
	}

	@Override
	public void create (Auction auction) {
		if (auction.getTreasurySecurityList().isEmpty()) {
			throw new InvalidAuctionException("Auction must contain security");
		}
		auctionRepository.save(auction);
	}

	@Override
	public Map<String, String> validateSecurityForAuction (Auction auction,
	                                                       TempSecurityDTO treasurySecurity) {
		Map<String, String> errors = new HashMap<>();
		List<TreasurySecurity> securityList = auction.getTreasurySecurityList();
		for (TreasurySecurity ts : securityList) {
			if (ts.getSecurityName().equals(treasurySecurity.getSecurityName())) {
				errors.put("INVALID_SECURITY_ERROR", "Auction cannot contain duplicate securities");
			}
		}
		if (auction.getTreasurySecurityList().size() > 10) {
			errors.put("INVALID_SECURITY_ERROR",
					"Auction cannot contain more than 10 securities at a time");
		}
		if (treasurySecurity.getIssueDate() == null) {
			errors.put("ISSUE_DATE_ERROR", "Issue date cannot be null");
			return errors;
		}
		if (auction.getAuctionEndDate() != null) {
			if (treasurySecurity.getIssueDate()
					.isBefore(ChronoLocalDate.from(auction.getAuctionEndDate().plusDays(1)))) {
				errors.put("ISSUE_DATE_ERROR", "Issue date must take place after the auction");
			}
		}
		if (treasurySecurity.getTotalAmount() == null) {
			errors.put("TOTALAMOUNT_ERROR", "Total amount must not be null");
		}
		if (treasurySecurity.getTotalAmount() < 10000000) {
			errors.put("TOTALAMOUNT_ERROR", "Total amount must be at least $10.000.000");
		}
		if (treasurySecurity.getTotalAmount() % 100 != 0) {
			errors.put("TOTALAMOUNT_ERROR",
					"Total amount must be a product of $100 security denominations");
		}
		if (treasurySecurity.getTotalAmount() > 100000000) {
			errors.put("TOTALAMOUNT_ERROR", "Total amount must not exceed $100.000.000");
		}
		if (treasurySecurity.getSecurityType() == null) {
			errors.put("INVALID_SECURITY_ERROR", "Security type cannot be null");
			return errors;
		}
		if (treasurySecurity.getMaturityDate() == null) {
			errors.put("INVALID_SECURITY_ERROR", "Maturity date cannot be null");
			return errors;
		}
		if (!TreasurySecurityTermConstraint.validSecurities.contains(
				treasurySecurity.getSecurityType())) {
			errors.put("INVALID_SECURITY_ERROR", "Invalid treasury security");
		}
		if (treasurySecurity.getSecurityTerm() == null) {
			errors.put("SECURITY_TERM_ERROR", "Security term cannot be null");
			return errors;
		}
		if (treasurySecurity.getSecurityType().equals("T-Bill")) {
			if (!TreasurySecurityTermConstraint.validBillTerm.contains(
					treasurySecurity.getSecurityTerm())) {
				errors.put("SECURITY_TERM_ERROR", "Bill term out of bound");
			}
		}
		if (treasurySecurity.getSecurityType().equals("T-Note")) {
			if (!TreasurySecurityTermConstraint.validNoteTerm.contains(
					treasurySecurity.getSecurityTerm())) {
				errors.put("SECURITY_TERM_ERROR", "Note term out of bound");
			}
		}
		if (treasurySecurity.getSecurityType().equals("T-Bond")) {
			if (!TreasurySecurityTermConstraint.validBondTerm.contains(
					treasurySecurity.getSecurityTerm())) {
				errors.put("SECURITY_TERM_ERROR", "Bond term out of bound");
			}
		}
		return errors;
	}

	@Override
	public Auction setDateToAuction (Auction auction, AuctionDateDTO auctionDateDTO) {
		if (auctionDateDTO.getAuctionStartDate() == null) {
			throw new InvalidAuctionException("Auction start time cannot be null");
		}
		if (auctionDateDTO.getAuctionEndDate() == null) {
			throw new InvalidAuctionException("Auction end time cannot be null");
		}
		for (TreasurySecurity ts : auction.getTreasurySecurityList()) {
			if (ts.getIssueDate().isBefore(ChronoLocalDate.from(auctionDateDTO.getAuctionEndDate()))) {
				throw new InvalidAuctionException("Security Issue date cannot take place before auction.");
			}
		}
		if (auctionDateDTO.getAuctionStartDate().isBefore(LocalDateTime.now())) {
			throw new InvalidAuctionException("Auction start date out of bound");
		}
		if (auctionDateDTO.getAuctionEndDate().isBefore(LocalDateTime.now())) {
			throw new InvalidAuctionException("Auction end date out of bound");
		}
		if (auctionDateDTO.getAuctionEndDate().isBefore(auctionDateDTO.getAuctionStartDate())) {
			throw new InvalidAuctionException("Invalid auction end time");
		}
		auction.setAuctionStartDate(auctionDateDTO.getAuctionStartDate());
		auction.setAuctionEndDate(auctionDateDTO.getAuctionEndDate());
		return auction;
	}
}
