package com.taskagile.domain.common.security;

import com.taskagile.domain.model.user.UserId;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class TokenManager {

  private Key secretKey;

  public TokenManager(@Value("${app.token-secret-key}") String secretKey) {
    this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
  }

  /**
   * Generate a JWT with user's id as its subject
   *
   * @param userId the id of the user
   * @return a JWT value
   */
  public String jwt(UserId userId) {
    return Jwts.builder()
      .setSubject(String.valueOf(userId.value()))
      .signWith(secretKey).compact();
  }

  /**
   * Get user id out of a JWT value
   *
   * @param jws the jwt string
   * @return user id
   */
  public UserId verifyJwt(String jws) {
    String userIdValue = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jws).getBody().getSubject();
    return new UserId(Long.valueOf(userIdValue));
  }
}
