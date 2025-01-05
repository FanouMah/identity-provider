package itu.prom16.identity_provider.controller;

import itu.prom16.identity_provider.DTO.UserRequest;
import itu.prom16.identity_provider.entity.Session_users;
import itu.prom16.identity_provider.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Enregistrer un utilisateur", description = "Permet d'enregistrer un nouvel utilisateur dans le système.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilisateur enregistré avec succès"),
        @ApiResponse(responseCode = "400", description = "Erreur lors de l'enregistrement")
    })
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRequest request) {
        try {
            String response = authService.register(new Session_users(request));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de l'enregistrement : " + e.getMessage());
        }
    }

    @Operation(summary = "Vérifier un email", description = "Valide un email à l'aide d'un token envoyé par email.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Validation réussie"),
        @ApiResponse(responseCode = "400", description = "Erreur lors de la validation")
    })
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
