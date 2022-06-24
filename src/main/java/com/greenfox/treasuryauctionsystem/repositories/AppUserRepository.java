package com.greenfox.treasuryauctionsystem.repositories;

import com.greenfox.treasuryauctionsystem.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
  // registration activation token
  AppUser findByActivationToken(String activationToken);

  AppUser findAppUserByEmail(String email);

  AppUser findAppUserByReactivationToken(String token);

}
