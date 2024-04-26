package com.example.front_gestao_vagas.modules.candidate.controller;

import com.example.front_gestao_vagas.modules.candidate.service.CandidateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;

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
    public String signIn(RedirectAttributes redirectAttributes, HttpSession httpSession, String username, String password) {

        try {
            var token = candidateService.signIn (username, password);
            var grantedAuthorities = new HashSet<GrantedAuthority> ();
            for (String role : token.roles ()) {
                GrantedAuthority grantedAuthority = () -> "ROLE_" + role.toString ().toUpperCase ();
                grantedAuthorities.add (grantedAuthority);
            }

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken (null, null, grantedAuthorities);
            auth.setDetails (token);

            SecurityContextHolder.getContext ().setAuthentication (auth);
            SecurityContext context = SecurityContextHolder.getContext ();
            httpSession.setAttribute ("SPRING_SECURITY_CONTEXT", context);
            httpSession.setAttribute ("token", token.access_token ());

            return "candidate/profile";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute ("error_message", "Usuário/senha inválidos!");
            return "redirect:/candidate/login";
        }
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('CANDIDATE')")
    public String profile() {
        return "candidate/profile";
    }

}
