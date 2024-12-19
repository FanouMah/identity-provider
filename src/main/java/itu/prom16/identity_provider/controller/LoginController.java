/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itu.prom16.identity_provider.controller;

import itu.prom16.identity_provider.entity.Users;
import itu.prom16.identity_provider.service.LoginService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Fanou
 */

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody Users user) {
        try {
            String response = loginService.sendPin(user);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la connexion : " + e.getMessage());
        }
    }
    
    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestBody Users user, @RequestParam String pin, HttpSession session) {
        try {
            boolean isVerified = loginService.verifyPin(user, pin);
            if (isVerified) {
                // Retirer le mot de passe avant de stocker l'utilisateur dans la session
                user.nullPassword();
                session.setAttribute("user", user);
                return ResponseEntity.ok("PIN vérifié avec succès. Session créée.");
            } else {
                return ResponseEntity.badRequest().body("Échec de la vérification du PIN.");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la vérification du PIN : " + e.getMessage());
        }
    }

}
