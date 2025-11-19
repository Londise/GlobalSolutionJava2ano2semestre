package br.com.fiap.GlobalSolutionJava.controller;

import br.com.fiap.GlobalSolutionJava.model.dto.UsuarioDTO;
import br.com.fiap.GlobalSolutionJava.model.entity.Usuario;
import br.com.fiap.GlobalSolutionJava.model.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/novo")
    public ModelAndView novo() {
        return new ModelAndView("formUsuario");
    }

    @PostMapping
    public ModelAndView cadastrar(@Valid UsuarioDTO usuarioDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("formUsuario");
        }

        // Verificar se o email já existe
        if (usuarioRepository.findByEmail(usuarioDTO.email()) != null) {
            ModelAndView mv = new ModelAndView("formUsuario");
            mv.addObject("erro", "Já existe um usuário com este e-mail.");
            return mv;
        }

        Usuario usuario = new Usuario(usuarioDTO);
        usuarioRepository.save(usuario);

        return new ModelAndView("redirect:/usuarios/login");
    }

    @GetMapping("/login")
    public ModelAndView loginForm() {
        return new ModelAndView("login"); // página de login
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam String email,
                              @RequestParam String senha,
                              HttpSession session) {

        Usuario usuario = usuarioRepository.findByEmailAndSenha(email, senha);
        if (usuario != null) {
            // login válido, redireciona para /cursos
            session.setAttribute("usuarioLogado", usuario);
            return new ModelAndView("redirect:/cursos");
        } else {
            // login inválido, retorna para o form de login com mensagem
            ModelAndView mv = new ModelAndView("login");
            mv.addObject("erro", "Email ou senha inválidos!");
            return mv;
        }
    }

    // Carrega o formulário para edição de usuário
    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        ModelAndView mv = new ModelAndView("formUsuario");
        mv.addObject("usuario", usuario);
        mv.addObject("acao", "/usuarios/atualizar/" + id);
        return mv;
    }

    @PostMapping("/atualizar/{id}")
    public ModelAndView atualizar(@PathVariable Long id, @Valid UsuarioDTO usuarioDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("formUsuario");
        }

        Usuario usuario = new Usuario(usuarioDTO);
        usuario.setIdUsuario(id);
        usuarioRepository.save(usuario);

        return new ModelAndView("redirect:/usuarios");
    }

    @GetMapping("/excluir/{id}")
    public ModelAndView excluir(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return new ModelAndView("redirect:/usuarios");
    }
}
