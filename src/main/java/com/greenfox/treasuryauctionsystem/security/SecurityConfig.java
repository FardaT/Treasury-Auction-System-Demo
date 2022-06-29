package com.greenfox.treasuryauctionsystem.security;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import com.greenfox.treasuryauctionsystem.security.filters.CustomAuthenticationFilter;
import com.greenfox.treasuryauctionsystem.security.filters.CustomAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;

  public SecurityConfig(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }
/*  @Bean
  PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }*/

  @SuppressWarnings("deprecation")
  @Bean
  public static NoOpPasswordEncoder passwordEncoder() {
    return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
  }
  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.csrf().disable();
    http.sessionManagement().sessionCreationPolicy(STATELESS);
    http.authorizeRequests()
        .antMatchers("/admin/**").hasRole("ADMIN")
        .antMatchers("/register").permitAll()
        .antMatchers("/confirm").permitAll()
        .antMatchers("/confirm_token").permitAll()
        .antMatchers("/confirm_token/**").permitAll()
        .antMatchers("/store").permitAll()
        .antMatchers("/resetpassword").permitAll()
        .antMatchers("/resetpassword/**").permitAll()
        .antMatchers("/login")
        .permitAll().and()
        .formLogin()
        .loginPage("/login").and()
        .logout()
        .deleteCookies("jwtoken")
        .logoutSuccessUrl("/login").and()
        .authorizeRequests().anyRequest().authenticated().and()
        .addFilter(new CustomAuthenticationFilter(authenticationManagerBean()))
        .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
/*
    http.csrf().disable();
    http.sessionManagement().sessionCreationPolicy(STATELESS);
    http.authorizeRequests().anyRequest().permitAll();

 */

  }
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }
  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

/*  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("resources/static/*.css");
  }*/
}
