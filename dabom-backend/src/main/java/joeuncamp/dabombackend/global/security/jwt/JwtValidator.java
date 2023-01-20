package joeuncamp.dabombackend.global.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import joeuncamp.dabombackend.domain.auth.repository.TokenRedisRepository;
import joeuncamp.dabombackend.global.error.ErrorCode;
import joeuncamp.dabombackend.global.error.exception.CRefreshTokenExpiredException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtValidator {
    @Value("${spring.jwt.secret}")
    private String secretKey;

    private final TokenRedisRepository tokenRedisRepository;

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims validateAccessToken(String accessToken) {
        try {
            if (tokenRedisRepository.doesTokenBlocked(accessToken)){
                log.error("사용 중지된 토큰");
                throw new JwtException(String.valueOf(ErrorCode.JWT_INVALID.getCode()));
            }
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(secretKey))
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (SecurityException e) {
            log.error("잘못된 시그니처");
            throw new JwtException(String.valueOf(ErrorCode.JWT_INVALID.getCode()));
        } catch (MalformedJwtException e) {
            log.error("유효하지 않은 JWT 토큰");
            throw new JwtException(String.valueOf(ErrorCode.JWT_INVALID.getCode()));
        } catch (ExpiredJwtException e) {
            log.error("Jwt 만료");
            throw new JwtException(String.valueOf(ErrorCode.JWT_EXPIRED.getCode()));
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 토큰 형식");
        } catch (IllegalArgumentException e) {
            log.error("JWT token compact of handler are invalid.");
            throw new JwtException(String.valueOf(ErrorCode.JWT_INVALID.getCode()));
        }
        return null;
    }

    public Claims validateAccessTokenForReissue(String accessToken) {
        try {
            if (tokenRedisRepository.doesTokenBlocked(accessToken)){
                log.error("사용 중지된 토큰");
                throw new JwtException(String.valueOf(ErrorCode.JWT_INVALID.getCode()));
            }
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(secretKey))
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (SecurityException e) {
            log.error("잘못된 시그니처");
            throw new JwtException(String.valueOf(ErrorCode.JWT_INVALID.getCode()));
        } catch (MalformedJwtException e) {
            log.error("유효하지 않은 JWT 토큰");
            throw new JwtException(String.valueOf(ErrorCode.JWT_INVALID.getCode()));
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 토큰 형식");
        } catch (IllegalArgumentException e) {
            log.error("JWT token compact of handler are invalid.");
            throw new JwtException(String.valueOf(ErrorCode.JWT_INVALID.getCode()));
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
        return null;
    }

    public Claims validateRefreshTokenForReissue(String refreshToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(secretKey))
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody();
        } catch (SecurityException e) {
            log.error("잘못된 시그니처");
            throw new JwtException(String.valueOf(ErrorCode.JWT_INVALID.getCode()));
        } catch (MalformedJwtException e) {
            log.error("유효하지 않은 JWT 토큰");
            throw new JwtException(String.valueOf(ErrorCode.JWT_INVALID.getCode()));
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 토큰 형식");
        } catch (IllegalArgumentException e) {
            log.error("JWT token compact of handler are invalid.");
            throw new JwtException(String.valueOf(ErrorCode.JWT_INVALID.getCode()));
        } catch (ExpiredJwtException e) {
            throw new CRefreshTokenExpiredException();
        }
        return null;
    }

}
