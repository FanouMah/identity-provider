/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itu.prom16.identity_provider.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import itu.prom16.identity_provider.entity.Users;
import itu.prom16.identity_provider.repository.UsersRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Fanou
 */
@Component
public class JwtTokenUtil {

    @Value("${key.token.session}")
    private String SECRET_KEY;

    @Value("${delai.token.session}")
    private long EXPIRATION_TIME;

    private final UsersRepository usersRepository;

    public JwtTokenUtil(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @SuppressWarnings("deprecation")
    public String generateToken(Users userInput) {
        Users user = usersRepository.findByEmail(userInput.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'email : " + userInput.getEmail()));
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getidUsers());
        claims.put("email", user.getEmail());
        claims.put("nom", user.getNom());
        claims.put("prenom", user.getPrenom());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail()) // Le champ "subject" contiendra l'email
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject(); // Le sujet contient l'email
    }

    @SuppressWarnings("deprecation")
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}