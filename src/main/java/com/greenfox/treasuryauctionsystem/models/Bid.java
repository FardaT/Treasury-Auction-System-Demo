package com.greenfox.treasuryauctionsystem.models;

import com.greenfox.treasuryauctionsystem.models.dtos.BidDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bids")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Bid {

    // FIELDS
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private TreasurySecurity treasurySecurity;

    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser user;

    private boolean isCompetitive;
    private long amount;
    private float rate;
    private boolean isAccepted = false;
    private boolean isArchived = false;
    private boolean isDisabled = false; // for soft delete/cancel

    public Bid(BidDTO bidDTO) {
        this.id = bidDTO.getId();
        this.treasurySecurity = bidDTO.getTreasurySecurity();
        this.user = bidDTO.getUser();
        this.isCompetitive = bidDTO.getIsCompetitive().equals("1");
        this.amount = bidDTO.getAmount();
        this.rate = bidDTO.getRate();
        this.isAccepted = bidDTO.isAccepted();
        this.isArchived = bidDTO.isArchived();
        this.isDisabled = bidDTO.isDisabled();
    }
}
