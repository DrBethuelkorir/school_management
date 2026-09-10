package com.trialweb.school_management.Utils;


import com.trialweb.school_management.Security.SchoolUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration}")
    private int expirationTime;

    public String generateToken(Authentication authentication) {
        SchoolUserDetails userPrinciple = (SchoolUserDetails) authentication.getPrincipal();

        return  Jwts.builder().setSubject(userPrinciple.getEmail())
                .claim("id",userPrinciple.getId())
                .claim("roles",userPrinciple.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ expirationTime) )
                .signWith(key(), SignatureAlgorithm.HS256).compact();
    }
    public Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public  boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException |
                 IllegalArgumentException e) {
            throw new JwtException(e.getMessage());

        }
    }
}