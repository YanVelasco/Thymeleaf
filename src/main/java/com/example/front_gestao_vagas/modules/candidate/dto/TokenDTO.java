package com.example.front_gestao_vagas.modules.candidate.dto;

import java.util.List;

public record TokenDTO(String access_token , List<String> roles, Long expires_in) {
}