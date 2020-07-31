package com.thumbing.shared.jwt;

import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.jwt.exception.JwtExpiredTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/13 21:14
 */
@Component
public class JwtTokenFactory {
    @Autowired
    private JwtSettings jwtSettings;
    private final Logger logger = LoggerFactory.getLogger(JwtTokenFactory.class);

    private final static String CLAIM_NAME = "name";
    private final static String CLAIM_ROLE = "role";

    /**
     * 创建jwt token
     *
     * @param userContext
     * @return
     */
    public String createJwtToken(UserContext userContext) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(userContext.getId()));
        claims.put(CLAIM_NAME, userContext.getName());
        claims.put(CLAIM_ROLE, userContext.getAuthorities().stream().map(s -> s.toString()).collect(Collectors.toList()));
        LocalDateTime currentTime = LocalDateTime.now();
        SecretKey key = Keys.hmacShaKeyFor(jwtSettings.getSigningKey().getBytes(StandardCharsets.UTF_8));
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(jwtSettings.getIssuer())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setId(UUID.randomUUID().toString())
                .setExpiration(Date.from(currentTime
                        .plusMinutes(jwtSettings.getExpirationTime())
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        return token;
    }

    /**
     * 反序列化token
     *
     * @param token
     * @return
     */
    public UserContext parseJwtToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSettings.getSigningKey().getBytes(StandardCharsets.UTF_8));
            Jws<Claims> jwsClaims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            UserContext userContext = new UserContext();
            userContext.setName(jwsClaims.getBody().get(CLAIM_NAME).toString());
            userContext.setId(Long.parseLong(jwsClaims.getBody().getSubject()));
            List<String> roles = jwsClaims.getBody().get(CLAIM_ROLE, List.class);
            List<GrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            userContext.setAuthorities(authorities);
            return userContext;

        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            logger.error("Invalid JWT Token", ex);
            throw new UnsupportedJwtException("Invalid JWT token: ", ex);
        } catch (ExpiredJwtException expiredEx) {
            logger.info("JWT Token is expired", expiredEx);
            throw new JwtExpiredTokenException("JWT Token expired", expiredEx);
        }
    }
}
