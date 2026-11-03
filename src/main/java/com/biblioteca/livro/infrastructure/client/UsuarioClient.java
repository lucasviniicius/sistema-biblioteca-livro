package com.biblioteca.livro.infrastructure.client;

import com.biblioteca.livro.business.dtos.UsuarioResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "usuario",
        url = "${usuario.service.url}"
)
public interface UsuarioClient {

    @GetMapping("/usuario/me")
    UsuarioResponseDTO buscarMeuPerfil(
            @RequestHeader("Authorization") String authorization
    );
}