package com.example.bookstorebe.security.jwt;

import com.example.bookstorebe.models.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${bookstore.app.jwtSecret}")
    private String jwtSecret;

    @Value("${bookstore.app.jwtExpirationMs}")
    private int jwtExpirationMs;

//    public Claims generateClaims(User user) throws UnsupportedEncodingException {
//        Map<String, Object> claims = new HashMap<>() {
//            {
//                put("id", user.getUserId());
//                put("email", user.getEmail());
//            }
//        };
//    }

    public String generateJwtToken(Authentication authentication) throws UnsupportedEncodingException {

        String userPrincipal = authentication.getPrincipal().toString();

        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(userPrincipal)
//                .setClaims()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, key());

        return jwtBuilder.compact();
    }

//    public String generateJwtToken(Authentication authentication) {
//
//        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
//
//        return Jwts.builder()
//                .setSubject(userPrincipal.getEmail())
//                .claim("id", userPrincipal.getId())
//                .claim("email", userPrincipal.getEmail())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
//                .signWith(SignatureAlgorithm.HS256, key())
//                .compact();
//    }


    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
