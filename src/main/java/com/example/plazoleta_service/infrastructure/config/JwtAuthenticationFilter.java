package com.example.plazoleta_service.infrastructure.config;

import com.example.plazoleta_service.infrastructure.adapters.output.JwtTokenValidatorAdapter;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenValidatorAdapter jwtValidator;

    public JwtAuthenticationFilter(JwtTokenValidatorAdapter jwtValidator) {
        this.jwtValidator = jwtValidator;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtValidator.validarToken(token)) {
                Claims claims = jwtValidator.extraerClaims(token);
                String correo = claims.getSubject();
                String rol = claims.get("rol", String.class);
                Long idUsuario = claims.get("idUsuario", Long.class);

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        correo, null, Collections.singletonList(new SimpleGrantedAuthority(rol))
                );
                auth.setDetails(idUsuario); // Guardamos el ID real del usuario autenticado
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }
}