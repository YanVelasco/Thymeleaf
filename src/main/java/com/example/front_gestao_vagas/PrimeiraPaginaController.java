package com.example.front_gestao_vagas;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PrimeiraPaginaController {
    @GetMapping("/home")
    public  String primeiraPagina(Model model){
        model.addAttribute("mensagemDaController", "Primeira mensagem da controller!");
        return "primeiraPagina";
    }

    @GetMapping("/login")
    public  String login(Model model){
        return "candidate/login";
    }

    @PostMapping("/create")
    public String cadastroCandidate(@RequestParam("name") String name){
        System.out.printf("Nome do candidato: %s\n", name);
        return "/candidate/login";
    }
}
