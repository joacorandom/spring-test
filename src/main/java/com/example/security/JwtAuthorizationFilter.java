package com.example.security;

import java.io.IOException;
import java.time.Instant;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.exceptions.TokenNotProvidedException;
import com.example.services.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

  @Autowired
  private TokenService tokenService;

  private final String HEADER = "Authorization";
  private final String PREFIX = "Bearer ";
  private final String SECRET = "56a7010456b474aeee111f3b7336581fb0a99129d426cf51903efbdfd629f008";

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    try {
      if (!"/token/".equals(request.getRequestURI())) {
        String token = getTokenFromHeader(request);
        if (tokenIsValid(token, request)) {
          Claims claims = tokenService.getClaims(token);

          UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null);
          SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
          SecurityContextHolder.clearContext();
        }
      }
      chain.doFilter(request, response);
    } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | TokenNotProvidedException e) {
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
      return;
    }
  }

  private String getTokenFromHeader(HttpServletRequest req) throws TokenNotProvidedException {
    String authenticationHeader = req.getHeader(HEADER);
    if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
      throw new TokenNotProvidedException();
    return authenticationHeader.replace(PREFIX, "");
  }

  public Claims getClaims(String token) {
    return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
  }

  private boolean tokenIsValid(String token, HttpServletRequest request) {
    Claims claims = getClaims(token);
    String subject = claims.getSubject();
    Instant expiration = claims.getExpiration().toInstant();

    String remoteAddress = getRemoteAddress(request);
    return remoteAddress.equals(subject) && Instant.now().compareTo(expiration) > 0;
  }

  private String getRemoteAddress(HttpServletRequest request) {
    String remoteAddr = "";
    if (request != null) {
      remoteAddr = request.getHeader("X-FORWARDED-FOR");
      if (remoteAddr == null || "".equals(remoteAddr)) {
        remoteAddr = request.getRemoteAddr();
      }
    }
    return remoteAddr;
  }

}