package com.example.plazoleta_service.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/restaurantes/**").permitAll() // Listar restaurantes público para Clientes (HU-09)
                        .requestMatchers(HttpMethod.POST, "/api/v1/restaurantes").hasAuthority("ROLE_ADMINISTRADOR") // Crear restaurante (HU-02)
                        .requestMatchers(HttpMethod.POST, "/api/v1/platos").hasAuthority("ROLE_PROPIETARIO") // Crear plato (HU-03)
                        .requestMatchers(HttpMethod.PUT, "/api/v1/platos/**").hasAuthority("ROLE_PROPIETARIO") // Modificar plato (HU-04)
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/platos/**").hasAuthority("ROLE_PROPIETARIO") // Habilitar/Deshabilitar (HU-07)
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("BearerAuth", new SecurityScheme()
                                .name("BearerAuth").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }
}