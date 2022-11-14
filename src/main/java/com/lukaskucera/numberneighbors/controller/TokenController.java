package com.lukaskucera.numberneighbors.controller;

import com.lukaskucera.numberneighbors.response.TokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

  private static final Logger logger = LoggerFactory.getLogger(
    TokenController.class
  );

  @GetMapping("/token")
  public ResponseEntity<TokenResponse> token(JwtAuthenticationToken jwtToken) {
    logger.info("Token info requested by player {}", jwtToken.getName());

    return ResponseEntity.ok(
      new TokenResponse(jwtToken.getToken(), jwtToken.getAuthorities())
    );
  }
}
