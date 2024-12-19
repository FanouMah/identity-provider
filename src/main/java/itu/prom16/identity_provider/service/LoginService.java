/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itu.prom16.identity_provider.service;

import itu.prom16.identity_provider.config.ConfigProperties;
import itu.prom16.identity_provider.entity.HistoriqueConnexion;
import itu.prom16.identity_provider.entity.HistoriqueTentative;
import itu.prom16.identity_provider.entity.PinVerification;
import itu.prom16.identity_provider.entity.Users;
import itu.prom16.identity_provider.repository.HistoriqueConnexionRepository;
import itu.prom16.identity_provider.repository.HistoriqueTentativeRepository;
import itu.prom16.identity_provider.repository.PinVerificationRepository;
import itu.prom16.identity_provider.repository.UsersRepository;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Fanou
 */
@Service
public class LoginService {
    private final PasswordEncoder passwordEncoder;
    private final ConfigProperties configProperties;
    private final ResourceLoader resourceLoader;
    private final UsersRepository usersRepository;  
    private final PinVerificationRepository pinVerificationRepository;
    private final HistoriqueTentativeRepository historiqueTentativeRepository;
    private final HistoriqueConnexionRepository historiqueConnexionRepository;
    private final EmailService emailService;

    public LoginService(PasswordEncoder passwordEncoder, ConfigProperties configProperties, ResourceLoader resourceLoader, UsersRepository usersRepository, PinVerificationRepository pinVerificationRepository, HistoriqueTentativeRepository historiqueTentativeRepository,HistoriqueConnexionRepository historiqueConnexionRepository, EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.configProperties = configProperties;
        this.resourceLoader = resourceLoader;
        this.usersRepository = usersRepository;
        this.pinVerificationRepository = pinVerificationRepository;
        this.historiqueTentativeRepository = historiqueTentativeRepository;
        this.historiqueConnexionRepository = historiqueConnexionRepository;
        this.emailService = emailService;

    }
    
    public Users verifyUser(Users userInput) throws Exception {
        // Vérification si l'utilisateur existe dans la base de données
        Users user = usersRepository.findByEmail(userInput.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'email : " + userInput.getEmail()));

        if (user.getnombreTentative() < 0) {
            throw new RuntimeException("Vous avez atteint le nombre de tentative de connexion.");
        }
        // Vérification du mot de passe
        if (!passwordEncoder.matches(userInput.getPassword(), user.getPassword())) {
            handleErrorLogin(user);
            
            throw new RuntimeException("Mot de passe incorrect");
        }
        return user;
    }
    
    public String sendPin(Users userInput) throws Exception  {
        Users user = verifyUser(userInput);
         // Génération d'un PIN aléatoire à 6 chiffres
        Integer pin = (int) (Math.random() * 900000) + 100000;
    
        // Création de l'objet PinVerification avec une date d'expiration
        PinVerification pinVerification = new PinVerification(
                pin,
                LocalDateTime.now().plusSeconds(configProperties.getDelaiPinConnexion()),
                user
        );
    
        // Préparation de l'username pour l'email
        String username = user.getNom() + " " + user.getPrenom();
    
        String htmlEmail;
        try {
            // Chargement du modèle d'email
            Resource resource = resourceLoader.getResource("classpath:templates/validate_connexion.html");
            htmlEmail = new String(
                    Files.readAllBytes(Paths.get(resource.getURI())),
                    StandardCharsets.UTF_8
            );
    
            // Remplacement des placeholders dans le contenu HTML
            htmlEmail = htmlEmail.replace("{{ username }}", username)
                                 .replace("{{ delai }}", configProperties.getDelaiPinConnexion().toString())
                                 .replace("{{ confirmation_code }}", String.valueOf(pin));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement du modèle d'email", e);
        }
    
        // Envoi de l'email avec le code de confirmation
        emailService.sendEmail(userInput.getEmail(), "Vérification de Connexion", htmlEmail);
        pinVerificationRepository.save(pinVerification);
        return htmlEmail;
    }
    
    public boolean verifyPin(Users userInput, String pin) throws Exception  {
        Users user = verifyUser(userInput);
        // Récupération du PIN le plus récent pour l'utilisateur
        PinVerification pinVerification = pinVerificationRepository
                .findTopByIdUsersOrderByDateExpirationDesc(user)
                .orElseThrow(() -> new RuntimeException("Pas de PIN enregistré pour le mail " + user.getEmail()));
    
        // Vérification du PIN
        if (!pinVerification.getPin().equals(Integer.valueOf(pin))) {
            handleErrorLogin(user);
            throw new RuntimeException("PIN incorrect");
        }
    
        // Vérification de l'expiration du PIN
        if (pinVerification.getDateExpiration().isBefore(LocalDateTime.now())) {
            handleErrorLogin(user);
            throw new RuntimeException("PIN expiré");
        }
    
        // Si tout est valide, mise à jour des connexions
        handleSuccessfulLogin(user);
        return true;
    }
    
    private void handleErrorLogin(Users user) {
        // Réduire le nombre de tentatives
        user.setnombreTentative(user.getnombreTentative() - 1);
        usersRepository.save(user);
    
        // Ajouter une historique de tentative
        HistoriqueTentative historiqueTentative = new HistoriqueTentative(LocalDateTime.now(), user);
        historiqueTentativeRepository.save(historiqueTentative);
    }
    
    private void handleSuccessfulLogin(Users user) {
        // Rétablir le nombre de tentatives restantes si nécessaire
        if (user.getnombreTentative() < configProperties.getNombreTentative()) {
            user.setnombreTentative(configProperties.getNombreTentative());
        }
    
        // Enregistrer l'historique de connexion
        HistoriqueConnexion historiqueConnexion = new HistoriqueConnexion(LocalDateTime.now(), user);
        historiqueConnexionRepository.save(historiqueConnexion);
    
        // Sauvegarder l'utilisateur mis à jour
        usersRepository.save(user);
    }
    
}
