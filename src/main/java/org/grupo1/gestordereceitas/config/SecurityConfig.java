package org.grupo1.gestordereceitas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // desabilita CSRF para o H2 Console
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) // permite H2 em iframe
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll() // libera H2 Console
                        .requestMatchers("/receitas/**").permitAll()  // libera endpoints de receitas
                        .requestMatchers("/categorias/**").permitAll()  // libera endpoints de categorias
                        .requestMatchers("/ingredientes/**").permitAll()  // libera endpoints de ingredientes
                        .anyRequest().authenticated() // outros endpoints precisam de autenticação
                )
                .httpBasic(Customizer.withDefaults()); // habilita HTTP Basic, mais adequado para APIs e Postman

        return http.build();
    }
}