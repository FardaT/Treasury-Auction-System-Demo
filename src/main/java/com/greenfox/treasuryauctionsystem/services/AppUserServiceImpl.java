package com.greenfox.treasuryauctionsystem.services;

import com.greenfox.treasuryauctionsystem.models.AppUser;
import com.greenfox.treasuryauctionsystem.repositories.AppUserRepository;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl implements AppUserService, UserDetailsService {

  private final AppUserRepository appUserRepository;

  public AppUserServiceImpl(AppUserRepository appUserRepository) {
    this.appUserRepository = appUserRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String loginDetail) throws UsernameNotFoundException {
     if(loginDetail == null){
       throw new  IllegalArgumentException("Username or email cannot be null.");
     }

    AppUser appUser = appUserRepository.findByUsernameOrEmail(loginDetail, loginDetail);
     if(appUser == null){
       throw new UsernameNotFoundException("No username or email can be found in the database");
     }
     Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
     authorities.add(new SimpleGrantedAuthority(appUser.isAdmin() ? "ADMIN" : "USER"));
    return new User(appUser.getUsername(),appUser.getPassword(), authorities);
  }
}
