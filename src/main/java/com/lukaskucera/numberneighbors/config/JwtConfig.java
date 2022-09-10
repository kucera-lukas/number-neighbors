package com.lukaskucera.numberneighbors.config;

public class JwtConfig {
  public static final String SECRET_KEY_ALGORITHM = "HmacSHA256";
  public static final String ISSUER = "https://numberneighbors.lukaskucera.com";
  public static final String AUDIENCE = "numberneighbors";
  public static final String AUTHORITIES_CLAIM_NAME = "authorities";
  public static final String AUTHORITY_PREFIX = "ROLE_";

  private JwtConfig() {}
}
