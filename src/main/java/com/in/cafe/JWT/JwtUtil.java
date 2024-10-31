package com.in.cafe.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private String secret = "HashSecret" ;

    public String extractUserName(String token){
        return extractClaims(token, Claims::getSubject);
    }

    public Date extractExpirationDate(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    public <T> T extractClaims(String token, Function<Claims,T> claimsResolver){
        final  Claims claims = extractAllClaims(token);
        return  claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser() // Use parserBuilder() for creating a JwtParser
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())) // Ensure secret is in bytes and appropriately sized
                .build() // Build the JwtParser
                .parseClaimsJws(token) // Parse the JWT
                .getBody();
    }
    public String generateToken(String username, String roles, boolean persistent){
        Map<String,Object> claims = new HashMap<>();
        claims.put("roles",roles);
        return createToken(claims,username, persistent);
    }

    //create token
    // Modify the createToken method to accept a persistent flag
    private String createToken(Map<String, Object> claims, String subject, boolean persistent){
        long expirationTime = persistent ? 1000 * 60 * 60 * 24 * 30 : 1000 * 60 * 60 * 12; // 30 days for persistent
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    private boolean isTokenExpired(String token){
        return extractExpirationDate(token).before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails){
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

}
