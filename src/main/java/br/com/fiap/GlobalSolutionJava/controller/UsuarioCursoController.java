package br.com.fiap.GlobalSolutionJava.controller;


import br.com.fiap.GlobalSolutionJava.model.dto.UsuarioCursoDTO;
import br.com.fiap.GlobalSolutionJava.model.entity.Curso;
import br.com.fiap.GlobalSolutionJava.model.entity.Usuario;
import br.com.fiap.GlobalSolutionJava.model.entity.UsuarioCurso;
import br.com.fiap.GlobalSolutionJava.model.entity.UsuarioCursoId;
import br.com.fiap.GlobalSolutionJava.model.repository.CursoRepository;
import br.com.fiap.GlobalSolutionJava.model.repository.UsuarioCursoRepository;
import br.com.fiap.GlobalSolutionJava.model.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/meus-cursos")
public class UsuarioCursoController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioCursoRepository usuarioCursoRepository;

    // Carrega conteúdo ao acessar a URL
    // Lista todos os cursos de um aluno específico
    @GetMapping
    public ModelAndView meusCursos(HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            return new ModelAndView("redirect:/usuarios/login");
        }

        List<UsuarioCurso> cursosDoUsuario = usuarioCursoRepository
                .findByUsuario_IdUsuario(usuario.getIdUsuario());

        ModelAndView mv = new ModelAndView("meus-cursos");
        mv.addObject("cursosDoUsuario", cursosDoUsuario);
        mv.addObject("usuario", usuario);

        return mv;
    }

    // Matricular usuário em um curso
    @PostMapping("/matricular/{cursoId}")
    public ModelAndView matricular(@PathVariable Long cursoId,
                                   HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            return new ModelAndView("redirect:/usuarios/login");
        }

        // Procura pelo curso para verificar existência
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        UsuarioCurso usuarioCurso = new UsuarioCurso(usuario, curso);
        System.out.println(usuarioCurso);
        usuarioCursoRepository.save(usuarioCurso);

        return new ModelAndView("redirect:/meus-cursos");
    }


    // Atualizar o conteúdo de um curso
    @PostMapping("/atualizar/{usuarioId}/{cursoId}")
    public ResponseEntity<UsuarioCurso> atualizar(
            @PathVariable Long usuarioId,
            @PathVariable Long cursoId,
            @Valid @RequestBody UsuarioCursoDTO dto) {

        UsuarioCursoId id = new UsuarioCursoId(usuarioId, cursoId);

        UsuarioCurso usuarioCurso = usuarioCursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matrícula não encontrada"));

        UsuarioCurso atualizado = usuarioCursoRepository.save(usuarioCurso);

        return ResponseEntity.ok(atualizado);
    }

    // Listar todas as relações usuário ↔ curso
    @GetMapping("/matriculas")
    public ModelAndView listar() {
        List<UsuarioCurso> cursosDoUsuario = usuarioCursoRepository.findAll();
        ModelAndView mv = new ModelAndView("cursos");
        mv.addObject("cursosDoUsuario", cursosDoUsuario);
        return mv;
    }

    // Remover a matrícula do curso
    @PostMapping("/remover/{usuarioId}/{cursoId}")
    public String excluir(
            @PathVariable Long usuarioId,
            @PathVariable Long cursoId) {

        UsuarioCursoId id = new UsuarioCursoId(cursoId, usuarioId);

        usuarioCursoRepository.deleteById(id);

        // Redireciona para a URL (/meus-cursos)
        return "redirect:/meus-cursos";
    }
}
