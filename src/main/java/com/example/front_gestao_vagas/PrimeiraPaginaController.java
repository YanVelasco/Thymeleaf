package com.example.front_gestao_vagas;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PrimeiraPaginaController {
    @GetMapping("/home")
    public  String primeiraPagina() {
        return "primeiraPagina";
    }
}