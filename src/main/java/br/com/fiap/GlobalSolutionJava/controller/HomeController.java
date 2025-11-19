package br.com.fiap.GlobalSolutionJava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "menuprincipal"; // Mostra o menuprincipal.html
    }

}
