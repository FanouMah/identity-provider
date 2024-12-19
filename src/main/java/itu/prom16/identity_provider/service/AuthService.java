package itu.prom16.identity_provider.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import itu.prom16.identity_provider.entity.Session_users;
import itu.prom16.identity_provider.entity.Token_Inscription;
import itu.prom16.identity_provider.entity.Users;

import itu.prom16.identity_provider.repository.SessionUserRepository;
import itu.prom16.identity_provider.repository.TokenInscriptionRepository;
import itu.prom16.identity_provider.repository.UsersRepository;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    private final SessionUserRepository sessionUserRepository;
    private final TokenInscriptionRepository tokenInscriptionRepository;
    private final UsersRepository usersRepository;  
    private final EmailService emailService;

    public AuthService(PasswordEncoder passwordEncoder, 
                       SessionUserRepository sessionUserRepository, 
                       TokenInscriptionRepository tokenInscriptionRepository,
                       UsersRepository usersRepository,
                       EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.sessionUserRepository = sessionUserRepository;
        this.tokenInscriptionRepository = tokenInscriptionRepository;
        this.usersRepository = usersRepository;
        this.emailService = emailService;
    }

    public void register(Session_users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setdateDemande(LocalDate.now());
        sessionUserRepository.save(user);

        String token = UUID.randomUUID().toString();
        Token_Inscription tokenInscription = new Token_Inscription(token, user);
        tokenInscription.setdateExpiration(LocalDateTime.now().plusSeconds(90));
        tokenInscriptionRepository.save(tokenInscription);

        String validationLink = "http://localhost:8080/api/auth/verify?token=" + token;
        emailService.sendEmail(
            user.getEmail(),
            "Validation de votre compte",
            "Cliquez sur le lien suivant pour valider votre compte : " + validationLink
        );
    }

    public void verifyEmail(String token) {
        Token_Inscription tokenEntity = tokenInscriptionRepository.findByCode(token)
                .orElseThrow(() -> new RuntimeException("Token invalide ou expiré"));
    
        if (tokenEntity.getdateExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expiré");
        }
    
        Session_users user = tokenEntity.getidSessionUser();
            Users newUser = new Users(
                user.getNom(),
                user.getPrenom(),
                user.getEmail(),
                user.getPassword()
        );
        newUser.setdateNaissance(user.getdateNaissance());
        newUser.setdateInscription(LocalDateTime.now());
        newUser.setnombreTentative(0);
    
        usersRepository.save(newUser);
        sessionUserRepository.delete(user);
    }
    
    
}
