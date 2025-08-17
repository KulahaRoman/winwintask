package winwin.auth_api.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import winwin.auth_api.entity.User;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${spring.security.token.jwt.secret}")
    private String secret;

    @Value("${spring.security.token.jwt.expiration}")
    private long expiration;

    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String buildToken(User user, long expiration) {
        try {
            return Jwts
                    .builder()
                    .subject(user.getId().toString())
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + expiration))
                    .signWith(getSecretKey())
                    .compact();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(User user) {
        return buildToken(user, expiration);
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts
                    .parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token);
            return !isTokenExpired(token);
        } catch (Exception exc) {
            return false;
        }
    }
}
