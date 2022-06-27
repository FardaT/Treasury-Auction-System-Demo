package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.exceptions.AppUserNotFoundException;
import com.greenfox.treasuryauctionsystem.exceptions.IllegalArgumentException;
import com.greenfox.treasuryauctionsystem.exceptions.IllegalRegexException;
import com.greenfox.treasuryauctionsystem.exceptions.TokenExpiredException;
import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.repositories.AppUserRepository;
import com.greenfox.treasuryauctionsystem.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AppUserServiceImpl implements AppUserService {

    // DI
    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    // STORE
    @Override
    public AppUser saveAppUser(AppUser appUser) {

        // check if fields are empty
        if (
                appUser.getUsername().equals("") ||
                        appUser.getEmail().equals("") ||
                        appUser.getInstitution().equals("") ||
                        appUser.getPassword().equals("")) {
            throw new IllegalArgumentException("Registration fields are all required!");
        } else if (!Utility.validatePassword(appUser.getPassword())) {
            // password checking (regex)
            throw new IllegalRegexException("Password regex failed.");
        } else {
            // if validation is ok, then save user
            return appUserRepository.save(appUser);
        }
    }

    // ACTIVATE ACCOUNT BY TOKEN
    @Override
    public void activateAccount(String activationToken) {

        // current DateTime
        Date currentDateTime = new Date(System.currentTimeMillis());

        // we need to get the user by the token from the email
        AppUser appUser = appUserRepository.findByActivationToken(activationToken);

        // if user is not found
        if (appUser == null) {

            throw new AppUserNotFoundException("There is no user with this token in the db.");

            // if token is expired
        } else if (appUser.getActivationTokenExpiration().before(currentDateTime)) {

            throw new TokenExpiredException("This activationToken is already expired");

        } else {

            // if everything is ok, then activate acc and save
            appUser.setActivated(true);
            appUserRepository.save(appUser);
        }
    }
}
