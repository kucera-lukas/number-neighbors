package com.lukaskucera.numberneighbors.response;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public record TokenResponse(
  Jwt token,
  Collection<GrantedAuthority> authorities
) {}
