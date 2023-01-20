package joeuncamp.dabombackend.global.security.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Value("${spring.jwt.secret}")
    private String secretKey;

    private final static long ACCESS_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 365;
    private final static long REFRESH_TOKEN_EXPIRATION = 2000L * 60 * 60 * 24 * 365;
    private final UserDetailsService userDetailsService;

    private final JwtValidator jwtValidator;

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenForm generateToken(Member member) {

        String accessToken = createToken(member, ACCESS_TOKEN_EXPIRATION);
        String refreshToken = createToken(member, REFRESH_TOKEN_EXPIRATION);

        return TokenForm.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private String createToken(Member member, Long expireTime) {
        Claims claims = injectValues(member, expireTime);

        return Jwts.builder()
                .setHeaderParam("type","jwt")
                .setClaims(claims)
                .signWith(getSigningKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims injectValues(Member member, Long expireTime) {
        long now = (new Date()).getTime();
        String authorities = member.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Claims claims = Jwts.claims();
        claims.setSubject(member.getAccount());
        claims.setIssuedAt(new Date());
        claims.setExpiration(new Date(now + expireTime));
        claims.setId(UUID.randomUUID().toString());
        claims.put("authorities", authorities);

        return claims;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        if (claims.get("authorities") == null) {
            throw new AccessDeniedException("권한이 없습니다.");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private Claims getClaims(String token) {
        Claims claims = jwtValidator.validateAccessToken(token);
        if (claims == null){
            log.error("null claims");
            throw new JwtException(String.valueOf(ErrorCode.JWT_INVALID.getCode()));
        }
        return claims;
    }
}