package com.test.product.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * responsible for manipulating jwt token
 *
 * @author Oleksandr Myronenko
 */
@Service

public class JwtService {
    private static final String SECRET_KEY = "6D5A7134743777397A24432646294A404E635266556A586E3272357538782F41";

    /**
     * using for extract username by jwt token
     *
     * @param jwtToken jwt token
     * @return user name
     */
    public String extractUserName(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    /**
     * extract claims
     *
     * @param jwtToken       jwt token for extraction
     * @param claimsResolver function for convert claims to T object
     * @param <T>            object
     * @return T object
     */
    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    /**
     * using for generate token
     *
     * @param userDetails user details for generation
     * @return jwt token
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * checking is jwt token is valid
     *
     * @param jwtToken    jwt token for validation
     * @param userDetails user details for validation
     * @return boolean value, where true - valid, false - is not valid
     */
    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        final String userName = extractUserName(jwtToken);
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    private Claims extractAllClaims(String jwtToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
