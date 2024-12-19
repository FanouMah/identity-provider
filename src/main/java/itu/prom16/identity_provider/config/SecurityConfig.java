package itu.prom16.identity_provider.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(csrf -> csrf.disable())  // Désactive CSRF (utile avec JWT)
            .authorizeRequests(requests -> requests
                    .requestMatchers("/api/auth/register", "/api/auth/verify").permitAll()  // Permet l'accès à l'inscription et à la vérification sans authentification
                    .anyRequest().authenticated()  // Nécessite l'authentification pour toutes les autres requêtes
            )
            .httpBasic().disable()  // Désactive l'authentification HTTP de base
            .formLogin().disable();  // Désactive l'authentification via formulaire
    return http.build();
}

}
