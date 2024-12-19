package itu.prom16.identity_provider.controller;

import itu.prom16.identity_provider.entity.Session_users;
import itu.prom16.identity_provider.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Session_users user) {
        try {
            String response = authService.register(user);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de l'enregistrement : " + e.getMessage());
        }
    }
    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        try {
            authService.verifyEmail(token);
            return ResponseEntity.ok("Votre compte a été validé et enregistrer avec succès.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la validation du token : " + e.getMessage());
        }
    }
}
