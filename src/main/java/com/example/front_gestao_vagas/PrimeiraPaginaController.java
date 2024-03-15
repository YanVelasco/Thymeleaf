package com.example.front_gestao_vagas;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PrimeiraPaginaController {
    @GetMapping("/home")
    public  String primeiraPagina() {
        return "primeiraPagina";
    }

    @GetMapping("/login")
    public  String login() {
        return "candidate/login";
    }

    @PostMapping("/create")
    public String cadastroCandidate(Model model,Pessoa pessoa) {
        System.out.printf("%s, %s, %s", pessoa.nome, pessoa.email, pessoa.usuario);
        model.addAttribute("Pessoa", pessoa);
        return "candidate/info";
    }

    private record Pessoa(String usuario, String email, String nome) {}
}