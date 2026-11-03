
package com.biblioteca.livro.infrastructure.security;

import com.biblioteca.livro.business.dtos.UsuarioResponseDTO;
import com.biblioteca.livro.infrastructure.client.UsuarioClient;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UsuarioClient usuarioClient;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {

        String authorizationHeader =
                request.getHeader("Authorization");

        if (authorizationHeader != null
                && authorizationHeader.startsWith("Bearer ")) {

            String token = authorizationHeader.substring(7);

            try {
                if (jwtUtil.validateToken(token)) {

                    String email = jwtUtil.extractUsername(token);

                    UsuarioResponseDTO usuario =
                            usuarioClient.buscarMeuPerfil(
                                    authorizationHeader
                            );

                    String role = usuario.role();

                    if (!"ADMIN".equals(role)
                            && !"USER".equals(role)) {
                        response.sendError(
                                HttpServletResponse.SC_FORBIDDEN,
                                "Permissão de usuário inválida."
                        );
                        return;
                    }

                    SimpleGrantedAuthority authority =
                            new SimpleGrantedAuthority("ROLE_" + role);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    email,
                                    null,
                                    List.of(authority)
                            );

                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);
                }

            } catch (JwtException | IllegalArgumentException ex) {
                SecurityContextHolder.clearContext();
            } catch (feign.FeignException.Unauthorized
                     | feign.FeignException.Forbidden ex) {
                SecurityContextHolder.clearContext();
            } catch (feign.FeignException ex) {
                response.sendError(
                        HttpServletResponse.SC_SERVICE_UNAVAILABLE,
                        "Não foi possível consultar o serviço de usuários."
                );
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
