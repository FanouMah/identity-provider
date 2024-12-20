/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itu.prom16.identity_provider.controller;

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
    
    @PostMapping("/resettentative/send")
    public ResponseEntity<String> sendResetTentative(@RequestBody Users user) {
        try {
            String response = userService.resetPin(user);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la reinitialisation du nombre de tentative : " + e.getMessage());
        }
    }
    
    @GetMapping("/resettentative/verify")
    public ResponseEntity<String> verifyResetTentative(@RequestParam("token") String token) {
        try {
            userService.verifyEmailResetPin(token);
            return ResponseEntity.ok("Votre nombre de tentative de connexion a été réinitialisé avec succès.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la validation du token : " + e.getMessage());
        }
    }
        
    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestHeader("Authorization") String token, @RequestBody Users userUpdates) {
        try {
            String email = jwtTokenUtil.getUsernameFromToken(token);
            Users updatedUser = userService.updateUserDetails(email, userUpdates);
            return ResponseEntity.ok("Informations mises à jour avec succès pour l'utilisateur : " + updatedUser.getEmail());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la mise à jour des informations utilisateur : " + e.getMessage());
        }
    }
    
}
