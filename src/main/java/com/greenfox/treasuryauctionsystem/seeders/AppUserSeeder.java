package com.greenfox.treasuryauctionsystem.seeders;

import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.repositories.AppUserRepository;
import com.greenfox.treasuryauctionsystem.utils.ApplicationDetails;
import com.greenfox.treasuryauctionsystem.utils.PasswordResetTokenGenerator;
import com.greenfox.treasuryauctionsystem.utils.Utility;
import org.springframework.stereotype.Service;

@Service
public class AppUserSeeder {

    // DI
    private final AppUserRepository appUserRepository;

    public AppUserSeeder(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public void saveAppUsers() {

        // ADMIN
        AppUser admin = new AppUser();
        admin.setUsername("admin");
        admin.setEmail("lukacs.dvid@gmail.com");
        admin.setPassword("$2a$10$beHgCKv8GmR2l3.V0bMSQeiY7Ifx2uuM2ed7NmDAU4UsOccegAgaC"); //Auction0123!?#
        admin.setInstitution("Morgan Stanley");
        admin.setActivationToken(PasswordResetTokenGenerator.generatePasswordResetToken());
        admin.setActivationTokenExpiration(Utility.setExpiration(ApplicationDetails.expiration));
        admin.setAdmin(true);
        admin.setActivated(true);
        admin.setApproved(true);
        admin.setDisabled(false);

        // USER
        AppUser user = new AppUser();
        user.setUsername("user");
        user.setEmail("lukacs.dvid@gmail.com");
        user.setPassword("$2a$10$beHgCKv8GmR2l3.V0bMSQeiY7Ifx2uuM2ed7NmDAU4UsOccegAgaC"); //Auction0123!?#
        user.setInstitution("Morgan Stanley");
        user.setActivationToken(PasswordResetTokenGenerator.generatePasswordResetToken());
        user.setActivationTokenExpiration(Utility.setExpiration(ApplicationDetails.expiration));
        user.setAdmin(false);
        user.setActivated(false);
        user.setApproved(false);
        user.setDisabled(false);

        // SAVE
        appUserRepository.save(admin);
        appUserRepository.save(user);
    }
}