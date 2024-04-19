package com.example.front_gestao_vagas.modules.candidate.controller;

import com.example.front_gestao_vagas.modules.candidate.service.CandidateService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/candidate")
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping("/login")
    public String login() {
        return "candidate/login";
    }

    @PostMapping("/signIn")
    public String signIn(RedirectAttributes redirectAttributes, String username, String password) {

        try {
            var token = candidateService.signIn(username, password);

            if (token != null) {
                return "candidate/profile";
            }

            redirectAttributes.addFlashAttribute("error_message", "Usuário/senha inválidos!");
            return "redirect:/candidate/login";
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("error_message", "Erro ao tentar logar!");
            return "redirect:/candidate/login";
        }
    }
}
