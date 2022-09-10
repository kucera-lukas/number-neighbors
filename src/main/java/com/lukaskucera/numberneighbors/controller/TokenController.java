package com.lukaskucera.numberneighbors.controller;

import com.lukaskucera.numberneighbors.response.TokenResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

  @GetMapping("/token")
  public ResponseEntity<TokenResponse> token(
    @NotNull JwtAuthenticationToken jwtToken
  ) {
    return ResponseEntity.ok(
      new TokenResponse(jwtToken.getToken(), jwtToken.getAuthorities())
    );
  }
}
