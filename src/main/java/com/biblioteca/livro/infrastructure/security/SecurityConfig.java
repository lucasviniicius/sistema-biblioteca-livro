
package com.biblioteca.livro.infrastructure.security;

import com.biblioteca.livro.infrastructure.client.UsuarioClient;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@SecurityScheme(
        name = SecurityConfig.SECURITY_SCHEME,
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SecurityConfig {

    public static final String SECURITY_SCHEME = "bearerAuth";

    private final JwtUtil jwtUtil;
    private final UsuarioClient usuarioClient;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            CorsConfigurationSource corsConfigurationSource
    ) throws Exception {

        JwtRequestFilter jwtRequestFilter =
                new JwtRequestFilter(jwtUtil, usuarioClient);

        http
                .cors(cors -> cors.configurationSource(
                        corsConfigurationSource
                ))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )
                .authorizeHttpRequests(authorize -> authorize

                        // Rotas públicas
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // Consultas: ADMIN e USER
                        .requestMatchers(
                                HttpMethod.GET,
                                "/livros",
                                "/livros/**"
                        ).hasAnyRole("ADMIN", "USER")

                        // Cadastro: somente ADMIN
                        .requestMatchers(
                                HttpMethod.POST,
                                "/livros",
                                "/livros/**"
                        ).hasRole("ADMIN")

                        // Atualização: somente ADMIN
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/livros",
                                "/livros/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/livros",
                                "/livros/**"
                        ).hasRole("ADMIN")

                        // Exclusão: somente ADMIN
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/livros",
                                "/livros/**"
                        ).hasRole("ADMIN")

                        // Demais rotas exigem autenticação
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtRequestFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
