/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itu.prom16.identity_provider.controller;

import itu.prom16.identity_provider.DTO.LoginRequest;
import itu.prom16.identity_provider.DTO.UpdateUserRequest;
import itu.prom16.identity_provider.config.JwtTokenUtil;
import itu.prom16.identity_provider.entity.Users;
import itu.prom16.identity_provider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 *
 * @author Fanou
 */
@RestController
@RequestMapping("/api/user")
public class UsersController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Operation(summary = "Réinitialiser les tentatives", description = "Envoie un lien pour réinitialiser le nombre de tentatives de connexion.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lien envoyé avec succès"),
        @ApiResponse(responseCode = "400", description = "Erreur lors de l'envoi du lien")
    })
    @PostMapping("/resettentative/send")
    public ResponseEntity<String> sendResetTentative(@RequestBody LoginRequest request) {
        try {
            String response = userService.resetPin(new Users(request));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la reinitialisation du nombre de tentative : " + e.getMessage());
        }
    }
    
    @Operation(summary = "Vérifier la réinitialisation", description = "Valide le lien de réinitialisation pour les tentatives de connexion.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Réinitialisation validée"),
        @ApiResponse(responseCode = "400", description = "Erreur lors de la validation")
    })
    @GetMapping("/resettentative/verify")
    public ResponseEntity<String> verifyResetTentative(@RequestParam("token") String token) {
        try {
            userService.verifyEmailResetPin(token);
            return ResponseEntity.ok("Votre nombre de tentative de connexion a été réinitialisé avec succès.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la validation du token : " + e.getMessage());
        }
    }
       
    @Operation(summary = "Mettre à jour les informations utilisateur", description = "Met à jour les détails d'un utilisateur authentifié.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Informations mises à jour avec succès"),
        @ApiResponse(responseCode = "400", description = "Erreur lors de la mise à jour")
    })
    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestHeader("authorization") String token, @RequestBody UpdateUserRequest request) {
        try {
            String email = jwtTokenUtil.getUsernameFromToken(token);
            Users updatedUser = userService.updateUserDetails(email, new Users(request));
            return ResponseEntity.ok("Informations mises à jour avec succès pour l'utilisateur : " + updatedUser.getEmail());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la mise à jour des informations utilisateur : " + e.getMessage());
        }
    }
    
}
