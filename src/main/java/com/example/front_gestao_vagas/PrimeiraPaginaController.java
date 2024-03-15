package com.example.front_gestao_vagas;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "login";
    }
}
