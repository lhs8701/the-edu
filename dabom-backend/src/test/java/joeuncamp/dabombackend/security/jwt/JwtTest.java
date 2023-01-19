package joeuncamp.dabombackend.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import static io.jsonwebtoken.Jwts.builder;
import static org.assertj.core.api.Assertions.assertThat;

public class JwtTest {

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Test
    @DisplayName("test")
    void test() {
        String secretString = "hisdafsdafsdafsadfsadfsadfsadfsadfsdafsdafsdafsdafsadfsadfdsfsaf";
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
        long now = (new Date()).getTime();
        String jwt = Jwts.builder()
                .claim("hello", "world")
                .setIssuedAt(new Date())
                .setExpiration(new Date(now + 500000000))
                .signWith(getSigningKey(secretString), SignatureAlgorithm.HS256)
                .compact();

        String value = Jwts.parserBuilder()
                .setSigningKey(getSigningKey(secretString))
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .get("hello", String.class);

        assertThat(value).isEqualTo("world");
    }
}
