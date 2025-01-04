/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itu.prom16.identity_provider.controller;

import itu.prom16.identity_provider.config.JwtTokenUtil;
import itu.prom16.identity_provider.entity.Users;
import itu.prom16.identity_provider.service.LoginService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 *
 * @author Fanou
 */

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Operation(summary = "Envoyer un code PIN", description = "Génère et envoie un code PIN à l'utilisateur pour l'authentification.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Code PIN envoyé avec succès"),
        @ApiResponse(responseCode = "400", description = "Erreur lors de l'envoi du code PIN")
    })
    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody Users user) {
        try {
            String response = loginService.sendPin(user);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la connexion : " + e.getMessage());
        }
    }
    
    @Operation(summary = "Vérifier le code PIN", description = "Vérifie le code PIN fourni et génère un token JWT en cas de succès.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Code PIN vérifié, connexion réussie"),
        @ApiResponse(responseCode = "400", description = "Échec de la vérification du code PIN")
    })
    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestBody Users user, @RequestParam String pin, HttpSession session) {
        try {
            boolean isVerified = loginService.verifyPin(user, pin);
            if (isVerified) {
                // Retirer le mot de passe avant de stocker l'utilisateur dans la session
                String token = jwtTokenUtil.generateToken(user);
                // Ajout du token dans les headers de la réponse
                return ResponseEntity.ok()
                        .header("Authorization", token) // Ajouter le token dans le header
                        .body("Connexion réussie, token généré : "+token); // Message de confirmation dans le corps
            } else {
                return ResponseEntity.badRequest().body("Échec de la vérification du PIN.");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la vérification du PIN : " + e.getMessage());
        }
    }
    
}
