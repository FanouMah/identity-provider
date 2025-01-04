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

    @SuppressWarnings("deprecation")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(requests -> requests
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/error",
                                "/api/auth/register",
                                "/api/auth/verify",
                                "/api/login/send",
                                "/api/login/verify",
                                "/api/user/resettentative/send",
                                "/api/user/resettentative/verify",
                                "/api/user/update"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(basic -> basic.disable()) 
                .formLogin(login -> login.disable());
        return http.build();
    }
}
