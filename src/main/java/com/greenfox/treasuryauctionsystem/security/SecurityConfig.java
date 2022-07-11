package com.greenfox.treasuryauctionsystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;

	public SecurityConfig (UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void configure (HttpSecurity http) throws Exception {

		http.csrf().disable();
		http.authorizeRequests()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/register").permitAll()
				.antMatchers("/confirm").permitAll()
				.antMatchers("/confirm_token").permitAll()
				.antMatchers("/confirm_token/**").permitAll()
				.antMatchers("/store").permitAll()
				.antMatchers("/store/**").permitAll()
				.antMatchers("/resetpassword").permitAll()
				.antMatchers("/resetpassword/**").permitAll()
				.antMatchers("/api/**").permitAll()
				.antMatchers("/login")
				.permitAll().and()
				.formLogin()
				.loginPage("/login")
				.successForwardUrl("/auctions")
				.defaultSuccessUrl("/auctions").and()
				.logout()
				.logoutSuccessUrl("/login").and()
				.authorizeRequests().anyRequest().authenticated();

		// To bypass authentication & authorization for development, comment out the code above and use the one below
/*   	http.csrf().disable();
    http.sessionManagement().sessionCreationPolicy(STATELESS);
    http.authorizeRequests().anyRequest().permitAll();*/

	}

	@Override
	protected void configure (AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder);
		return authProvider;
	}

	@Override
	public void configure (WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/**/*.css");
	}
	@Bean
	public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
		StrictHttpFirewall firewall = new StrictHttpFirewall();
		firewall.setAllowUrlEncodedSlash(true);
		firewall.setAllowSemicolon(true);
		return firewall;
	}
}
