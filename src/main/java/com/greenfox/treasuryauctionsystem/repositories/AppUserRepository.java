package com.greenfox.treasuryauctionsystem.repositories;

import com.greenfox.treasuryauctionsystem.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

	// login method
	AppUser findByUsernameOrEmail (String username, String email);

	// registration activation token
	AppUser findByActivationToken (String activationToken);

	// is the username already taken?
	AppUser findByUsername (String username);

	AppUser findAppUserByEmail (String email);

	AppUser findAppUserByReactivationToken (String token);

	AppUser findAppUserByUserName (String userName);
}
