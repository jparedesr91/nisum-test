package com.nisum.interviewtest.user.security;

import com.nisum.interviewtest.user.errors.InvalidTokenException;
import com.nisum.interviewtest.user.model.Role;
import com.nisum.interviewtest.user.model.UserTokenSession;
import com.nisum.interviewtest.user.service.UserTokenSessionService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

  @Value("${security.jwt.token.secret-key:secret-key}")
  private String secretKey;

  @Value("${security.jwt.token.expire-length:3600000}")
  private static final long VALIDTY_TIME = 36000000;

  private final UserDetailsUtil userDetailsUtil;

  private final UserTokenSessionService userTokenSessionService;

  public JwtTokenProvider(UserDetailsUtil userDetailsUtil, UserTokenSessionService userTokenSessionService) {
    this.userDetailsUtil = userDetailsUtil;
    this.userTokenSessionService = userTokenSessionService;
  }

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public String createToken(String username, List<Role> roles) {

    Claims claims = Jwts.claims().setSubject(username);
    claims.put("auth", roles.stream().map(s -> new SimpleGrantedAuthority(s.getAuthority())).filter(Objects::nonNull).collect(Collectors.toList()));

    Date now = new Date();
    Date validity = new Date(now.getTime() + VALIDTY_TIME);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(validity)
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = userDetailsUtil.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUsername(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public boolean validateToken(String token, String sessionId) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      String username = getUsername(token);
      UserTokenSession userTokenSession = new UserTokenSession(username, token, sessionId, (long)3600);
      return userTokenSessionService.isValidUserTokenSessionMapping(userTokenSession);
    } catch (JwtException | IllegalArgumentException e) {
      throw new InvalidTokenException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
