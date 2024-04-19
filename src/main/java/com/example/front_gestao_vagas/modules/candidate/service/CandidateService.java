package com.example.front_gestao_vagas.modules.candidate.service;

import com.example.front_gestao_vagas.modules.candidate.dto.TokenDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.tokens.Token;

import java.util.HashMap;
import java.util.Map;

@Service
public class CandidateService {
    public TokenDTO signIn(String username, String password) {
        RestTemplate restTemplate = new RestTemplate ();

        HttpHeaders headers = new HttpHeaders ();
        headers.setContentType (MediaType.APPLICATION_JSON);

        Map<String, String> data = new HashMap<> ();
        data.put ("username", username);
        data.put ("password", password);

        HttpEntity<Map<String, String>> request = new HttpEntity<> (data, headers);

        var result = restTemplate.postForObject ("http://localhost:8080/candidate/auth", request, String.class);
        return new TokenDTO (result);
    }
}
