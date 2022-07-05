package com.greenfox.treasuryauctionsystem.models;

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
    private boolean isAccepted;
    private boolean isArchived;
    private boolean isDisabled; // for soft delete/cancel
}
