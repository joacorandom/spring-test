package com.example.services;

import java.time.Instant;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expirationTimeInSeconds}")
  private int expirationTimeInSeconds;

  @Autowired
  private HttpServletRequest request;

  public String generateToken() {
    String remoteAddress = getRemoteAddress();
    return Jwts.builder().setSubject(remoteAddress).setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expirationTimeInSeconds * 1000))
        .signWith(SignatureAlgorithm.HS512, secret).compact();
  }

  public Claims getClaims(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

  public boolean tokenIsValid(String token) {
    Claims claims = getClaims(token);
    String subject = claims.getSubject();
    Instant expiration = claims.getExpiration().toInstant();

    String remoteAddress = getRemoteAddress();
    return remoteAddress.equals(subject) && Instant.now().compareTo(expiration) > 0;
  }

  private String getRemoteAddress() {
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