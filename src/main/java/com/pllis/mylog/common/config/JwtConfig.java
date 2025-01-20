package com.pllis.mylog.common.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.HttpStatus;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtConfig {

    @Value("${jwt.iss}")
    private String iss;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access_token_exp}")
    private long accessTokenExpTime;

    /**
     * Key 생성
     * @return SecretKey Key
     */
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Access Token 생성
     * @return Access Token String
     */
    public String createAccessToken(String loginId, String userType) {
        return createToken(loginId, userType, accessTokenExpTime * 7);
    }

    /**
     * Refresh Token 생성
     * @return Access Token String
     */
    public String createRefreshToken(String loginId, String userType) {
        return createToken(loginId, userType, accessTokenExpTime * 14);
    }

    /**
     * JWT 생성
     * @return JWT String
     */
    private String createToken(String loginId, String userType, long expireTime) {
        Claims claims = Jwts.claims();
        claims.put("loginId", loginId);
        claims.put("userType", userType);

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(expireTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(iss)
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(this.getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Token 에서 Login Id 추출
     * @return  Login Id (Long)
     */
    public String getLoginId(String token) {
        return parseClaims(token).get("loginId", String.class);
    }

    /**
     * Token 에서 User Type 추출
     * @return User Type (Long)
     */
    public String getUserType(String token) {
        return parseClaims(token).get("userType", String.class);
    }

    /**
     * JWT 검증
     * @return IsValidate
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(this.getKey()).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "토큰 정보가 유효하지 않습니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "만료된 토큰 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "잘못된 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    /**
     * JWT Claims 추출
     * @return JWT Claims
     */
    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(this.getKey())
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
