/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itu.prom16.identity_provider.service;

import itu.prom16.identity_provider.config.ConfigProperties;
import itu.prom16.identity_provider.entity.TokenResetPin;
import itu.prom16.identity_provider.entity.Users;
import itu.prom16.identity_provider.repository.TokenResetPinRepository;
import itu.prom16.identity_provider.repository.UsersRepository;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Fanou
 */
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final ConfigProperties configProperties;
    private final ResourceLoader resourceLoader;
    private final UsersRepository usersRepository;
    private final TokenResetPinRepository tokenResetPinRepository;
    private final EmailService emailService;

    public UserService(PasswordEncoder passwordEncoder, ConfigProperties configProperties, ResourceLoader resourceLoader, UsersRepository usersRepository, TokenResetPinRepository tokenResetPinRepository, EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.configProperties = configProperties;
        this.resourceLoader = resourceLoader;
        this.usersRepository = usersRepository;
        this.tokenResetPinRepository = tokenResetPinRepository;
        this.emailService = emailService;
    }

    public String resetPin(Users userInput) throws Exception{
        Users user = verifyUser(userInput);
        String token = UUID.randomUUID().toString();
        TokenResetPin tokenResetPin = new TokenResetPin(token,
                                                        LocalDateTime.now().plusSeconds(configProperties.getDelaiTokenResetTentative()),
                                                        user);
        
        String ip = configProperties.getServerIp();
        String port = configProperties.getServerPort();
        String validationLink = "http://"+ip+":"+port+"/api/user/resettentative/verify?token=" + token; 
        String username = user.getNom()+" "+user.getPrenom();
        String htmlEmail = "";
        
        try {
            Resource resource = resourceLoader.getResource("classpath:templates/reset_tentative.html");
            htmlEmail = new String(
                Files.readAllBytes(Paths.get(resource.getURI())),
                StandardCharsets.UTF_8
            );
            htmlEmail = htmlEmail.replace("{{ username }}", username)
                                 .replace("{{ validationLink }}", validationLink);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement du modèle d'email", e);
        }
            
        emailService.sendEmail(user.getEmail(), "Reinitialisation du tentative de connexion", htmlEmail);
        tokenResetPinRepository.save(tokenResetPin);
        return htmlEmail;
    }
    
    public  void verifyEmailResetPin(String token) {
        TokenResetPin tokenResetPin = tokenResetPinRepository.findByCode(token)
                .orElseThrow(() -> new RuntimeException("Token invalide"));
        
        if (tokenResetPin.getDateExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expiré");
        }
        Users user = tokenResetPin.getIdUsers();
        user.setnombreTentative(3);
        usersRepository.save(user);
        
    }
    
    private Users verifyUser(Users userInput) {
        // Vérification si l'utilisateur existe dans la base de données
        Users user = usersRepository.findByEmail(userInput.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'email : " + userInput.getEmail()));

        // Vérification du mot de passe
        if (!passwordEncoder.matches(userInput.getPassword(), user.getPassword())) {
            throw new RuntimeException("Mot de passe incorrect");
        }
        return user;
    }

    public Users updateUserDetails(String email, Users userUpdates) {
        Users existingUser = usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'email : " + email));

        // Mise à jour des informations utilisateur sauf l'email
        if (userUpdates.getNom() != null) {
            existingUser.setNom(userUpdates.getNom());
        }
        if (userUpdates.getPrenom() != null) {
            existingUser.setPrenom(userUpdates.getPrenom());
        }
        
        if (userUpdates.getdateNaissance() != null) {
            userUpdates.setdateNaissance(userUpdates.getdateNaissance());
        }
        if (userUpdates.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(userUpdates.getPassword()));
        }

        usersRepository.save(existingUser);
        return existingUser;
    }
}
