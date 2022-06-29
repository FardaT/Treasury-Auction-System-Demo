package com.greenfox.treasuryauctionsystem.security.filters;

import static java.util.Arrays.stream;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    if(request.getServletPath().equals("/login") || request.getServletPath().equals("/register")) {
      filterChain.doFilter(request, response);
  } else {
      Optional<Cookie> authorizationCookie = Arrays.stream(request.getCookies())
          .filter(cookie->"jwtoken".equals(cookie.getName())).findAny();
      if(authorizationCookie.isPresent()){
        try {
          String token = authorizationCookie.get().getValue();
          Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
          JWTVerifier verifier = JWT.require(algorithm).build();
          DecodedJWT decodedJWT = verifier.verify(token);
          String username = decodedJWT.getSubject();
          String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
          Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
          stream(roles).forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role));
          });

          UsernamePasswordAuthenticationToken authenticationToken =
              new UsernamePasswordAuthenticationToken(username, null, authorities);
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);

          filterChain.doFilter(request, response);

        } catch (Exception exception){
          //TODO add error page to filter authorization exception
          response.sendError(403);
        }
      } else {
        filterChain.doFilter(request, response);
      }
    }
  }
}
