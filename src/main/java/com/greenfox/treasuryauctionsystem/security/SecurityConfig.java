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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {

    http.csrf().disable();
    http.sessionManagement().sessionCreationPolicy(STATELESS);
    http.authorizeRequests()
        .antMatchers("/admin/**").hasRole("ADMIN")
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

    // To bypass authentication & authorization for development, comment out the code above and use the one below
   /*http.csrf().disable();
    http.sessionManagement().sessionCreationPolicy(STATELESS);
    http.authorizeRequests().anyRequest().permitAll();*/

  }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/register")
        .antMatchers("/confirm")
        .antMatchers("/confirm_token")
        .antMatchers("/confirm_token/**")
        .antMatchers("/store")
        .antMatchers("/store/**")
        .antMatchers("/resetpassword")
        .antMatchers("/resetpassword/**")
        .antMatchers("/**/*.css");
  }
  @Bean
  public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
    StrictHttpFirewall firewall = new StrictHttpFirewall();
    firewall.setAllowUrlEncodedSlash(true);
    firewall.setAllowSemicolon(true);
    return firewall;
  }
}
