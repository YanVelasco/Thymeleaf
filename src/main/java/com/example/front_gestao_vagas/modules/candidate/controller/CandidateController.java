package com.example.front_gestao_vagas.modules.candidate.controller;

import com.example.front_gestao_vagas.modules.candidate.service.CandidateService;
import com.example.front_gestao_vagas.modules.candidate.service.ProfileCandidateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/candidate")
public class CandidateController {

    private final CandidateService candidateService;

    private final ProfileCandidateService profileCandidateService;

    public CandidateController(CandidateService candidateService, ProfileCandidateService profileCandidateService) {
        this.candidateService = candidateService;
        this.profileCandidateService = profileCandidateService;
    }

    @GetMapping("/login")
    public String login() {
        return "candidate/login";
    }

    @PostMapping("/signIn")
    public String signIn(RedirectAttributes redirectAttributes, HttpSession httpSession, String username, String password) {

        try {
            var token = candidateService.login (username, password);
            var grantedAuthorities = token.roles ().stream ().map (role -> new SimpleGrantedAuthority ("ROLE_" + role.toString ().toUpperCase ())).toList ();
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken (null, null, grantedAuthorities);
            auth.setDetails (token.access_token ());

            SecurityContextHolder.getContext ().setAuthentication (auth);
            SecurityContext context = SecurityContextHolder.getContext ();
            httpSession.setAttribute ("SPRING_SECURITY_CONTEXT", context);
            httpSession.setAttribute ("token", token.access_token ());

            return "redirect:/candidate/profile";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute ("error_message", "Usuário/senha inválidos!");
            return "redirect:/candidate/login";
        }
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('CANDIDATE')")
    public String profile(Model model) {
       try {
           Authentication authentication = SecurityContextHolder.getContext ().getAuthentication ();
           var user = profileCandidateService.execute (authentication.getDetails ().toString ());
           model.addAttribute ("user", user);
           return "candidate/profile";
       } catch (HttpClientErrorException.Unauthorized e ) {
           return "redirect:/candidate/login";
       }
    }

}
