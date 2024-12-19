package itu.prom16.identity_provider.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import itu.prom16.identity_provider.config.ConfigProperties;
import itu.prom16.identity_provider.entity.Session_users;
import itu.prom16.identity_provider.entity.Token_Inscription;
import itu.prom16.identity_provider.entity.Users;

import itu.prom16.identity_provider.repository.SessionUserRepository;
import itu.prom16.identity_provider.repository.TokenInscriptionRepository;
import itu.prom16.identity_provider.repository.UsersRepository;
import java.util.Optional;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    private final SessionUserRepository sessionUserRepository;
    private final TokenInscriptionRepository tokenInscriptionRepository;
    private final UsersRepository usersRepository;  
    private final EmailService emailService;
    private final ConfigProperties configProperties;
    private final ResourceLoader resourceLoader;

    public AuthService(PasswordEncoder passwordEncoder, 
                       SessionUserRepository sessionUserRepository, 
                       TokenInscriptionRepository tokenInscriptionRepository,
                       UsersRepository usersRepository,
                       EmailService emailService,
                       ConfigProperties configProperties,
                       ResourceLoader resourceLoader) {
        this.passwordEncoder = passwordEncoder;
        this.sessionUserRepository = sessionUserRepository;
        this.tokenInscriptionRepository = tokenInscriptionRepository;
        this.usersRepository = usersRepository;
        this.emailService = emailService;
        this.configProperties = configProperties;
        this.resourceLoader = resourceLoader;
    }

    public String register(Session_users user) {
        Optional<Users> userOptional = usersRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new RuntimeException("Cette adresse e-mail est déjà utilisée par une autre compte");
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setdateDemande(LocalDate.now());

            String token = UUID.randomUUID().toString();
            Token_Inscription tokenInscription = new Token_Inscription(token, user);
            tokenInscription.setdateExpiration(
                LocalDateTime.now().plusSeconds(configProperties.getDelaiTokenInscription())
            );

            String validationLink = "http://192.168.43.16:8080/api/auth/verify?token=" + token;
            String username = user.getNom()+" "+user.getPrenom();
            String htmlEmail = "";
            try {
                Resource resource = resourceLoader.getResource("classpath:templates/validate_inscription.html");
                htmlEmail = new String(
                    Files.readAllBytes(Paths.get(resource.getURI())),
                    StandardCharsets.UTF_8
                );
                htmlEmail = htmlEmail.replace("{{ username }}", username)
                                     .replace("{{ validationLink }}", validationLink);
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors du chargement du modèle d'email", e);
            }

            emailService.sendEmail(user.getEmail(),"Validation de votre compte",htmlEmail);
            sessionUserRepository.save(user);
            tokenInscriptionRepository.save(tokenInscription);
            return htmlEmail;
        }
    }

    public void verifyEmail(String token) {
        Token_Inscription tokenEntity = tokenInscriptionRepository.findByCode(token)
                .orElseThrow(() -> new RuntimeException("Token invalide ou expiré"));
    
        Session_users user = tokenEntity.getidSessionUser();
        
        if (tokenEntity.getdateExpiration().isBefore(LocalDateTime.now())) {
            tokenInscriptionRepository.delete(tokenEntity);
            sessionUserRepository.delete(user);
            throw new RuntimeException("Token expiré");
        }
    
        Users newUser = new Users(
            user.getNom(),
            user.getPrenom(),
            user.getEmail(),
            user.getPassword()
        );
        newUser.setdateNaissance(user.getdateNaissance());
        newUser.setdateInscription(LocalDateTime.now());
        newUser.setnombreTentative(configProperties.getNombreTentative());
    
        usersRepository.save(newUser);
        tokenInscriptionRepository.delete(tokenEntity);
        sessionUserRepository.delete(user);
    }
    
    
}
