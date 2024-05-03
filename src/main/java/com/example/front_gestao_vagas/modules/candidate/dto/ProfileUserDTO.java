package com.example.front_gestao_vagas.modules.candidate.dto;

import java.util.UUID;

public record ProfileUserDTO(
        UUID id,
        String name,
        String username,
        String email,
        String description
) {
}
