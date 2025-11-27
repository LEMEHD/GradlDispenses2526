package org.isfce.pid.GradleDispenses2526.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService users() {
        // On définit des utilisateurs "en dur" pour tester facilement
        UserDetails etu = User.withUsername("etudiant@isfce.be")
                .password("{noop}pass") // {noop} signifie "pas d'encodage" (juste pour le dev)
                .roles("ETUDIANT")
                .build();
        
        UserDetails prof = User.withUsername("prof@isfce.be")
                .password("{noop}pass")
                .roles("PROFESSEUR")
                .build();
                
        UserDetails admin = User.withUsername("admin@isfce.be")
                .password("{noop}pass")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(etu, prof, admin);
    }

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {
        // Désactiver CSRF (pour faciliter les tests API via Postman/React au début)
        http.csrf(csrf -> csrf.disable());
        
        http.authorizeHttpRequests(auth -> auth
            // Autoriser l'accès à la console H2
            .requestMatchers("/h2-console/**").permitAll()
            // AUTORISER SWAGGER EXPLICITEMENT
            .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
            .requestMatchers("/api/**").authenticated()
            // L'API nécessite une authentification
            .requestMatchers("/api/**").authenticated()
            // Le reste est public (si tu as d'autres pages)
            .anyRequest().permitAll()
        );
        
        // Nécessaire pour afficher la console H2 (qui utilise des frames HTML)
        http.headers(h -> h.frameOptions(fr -> fr.disable())); 
        
        // Activer l'authentification Basic (login/password dans les headers HTTP)
        // C'est ce que ton React utilisera probablement au début
        http.httpBasic(Customizer.withDefaults());
        
        return http.build();
    }
}