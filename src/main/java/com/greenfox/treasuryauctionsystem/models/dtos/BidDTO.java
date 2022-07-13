package com.greenfox.treasuryauctionsystem.models.dtos;

import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.models.TreasurySecurity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BidDTO {

    // FIELDS
    private Long id;

    private TreasurySecurity treasurySecurity;

    private AppUser user;

    private String isCompetitive;
    private long amount;
    private float rate;
    private boolean isAccepted = false;
    private boolean isArchived = false;
    private boolean isDisabled = false; // for soft delete/cancel
}
