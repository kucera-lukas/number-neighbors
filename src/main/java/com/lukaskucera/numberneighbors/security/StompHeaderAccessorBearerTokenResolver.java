package com.lukaskucera.numberneighbors.security;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.BearerTokenErrors;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * {@link StompHeaderAccessor} {@link BearerTokenResolver} implementation
 * based on RFC 6750.
 * For some reason {@link BearerTokenResolver} only accepts
 * {@link HttpServletRequest} objects and not {@link String}.
 * It's final preventing it from being subclassed.
 * Member variables are private so not even wrapping it would help.
 */
@Service
public class StompHeaderAccessorBearerTokenResolver {

  private static final Pattern authorizationPattern = Pattern.compile(
    "^Bearer (?<token>[a-zA-Z0-9-._~+/]+=*)$",
    Pattern.CASE_INSENSITIVE
  );

  public String resolve(StompHeaderAccessor accessor) {
    return resolveFromAuthorizationHeader(accessor);
  }

  private String resolveFromAuthorizationHeader(StompHeaderAccessor accessor) {
    final List<String> nativeAuthorizationValues = accessor.getNativeHeader(
      HttpHeaders.AUTHORIZATION
    );

    if (nativeAuthorizationValues == null) {
      final BearerTokenError error = BearerTokenErrors.invalidRequest(
        "Missing native header in the accessor"
      );
      throw new OAuth2AuthenticationException(error);
    } else if (nativeAuthorizationValues.size() != 1) {
      final BearerTokenError error = BearerTokenErrors.invalidRequest(
        "Invalid number of bearer tokens in the accessor"
      );
      throw new OAuth2AuthenticationException(error);
    }

    final String authorization = nativeAuthorizationValues.get(0);

    if (!StringUtils.startsWithIgnoreCase(authorization, "bearer")) {
      final BearerTokenError error = BearerTokenErrors.invalidToken(
        "Invalid bearer token"
      );
      throw new OAuth2AuthenticationException(error);
    }

    final Matcher matcher = authorizationPattern.matcher(authorization);
    if (!matcher.matches()) {
      BearerTokenError error = BearerTokenErrors.invalidToken(
        "Bearer token is malformed"
      );
      throw new OAuth2AuthenticationException(error);
    }

    return matcher.group("token");
  }
}
