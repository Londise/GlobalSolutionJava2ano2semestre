package br.com.fiap.GlobalSolutionJava.controller;

import br.com.fiap.GlobalSolutionJava.model.dto.CursoDTO;
import br.com.fiap.GlobalSolutionJava.model.entity.Curso;
import br.com.fiap.GlobalSolutionJava.model.entity.Usuario;
import br.com.fiap.GlobalSolutionJava.model.entity.UsuarioCurso;
import br.com.fiap.GlobalSolutionJava.model.repository.CursoRepository;
import br.com.fiap.GlobalSolutionJava.model.repository.UsuarioCursoRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioCursoRepository usuarioCursoRepository;

    // Carrega conteúdo ao acessar a URL contendo todos os cursos
    @GetMapping
    public ModelAndView cursos(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

        List<Curso> cursos = cursoRepository.findAll();
        // Buscar os IDs dos cursos que o usuário está cursando
        List<Long> cursosMatriculadosIds = new ArrayList<>();

        if (usuario != null) {
            // Busca todas as entidades UsuarioCurso (matrículas) do usuário
            List<UsuarioCurso> matriculas = usuarioCursoRepository
                    .findByUsuario_IdUsuario(usuario.getIdUsuario());

            // Mapeia essa lista para uma lista de IDs de cursos
            cursosMatriculadosIds = matriculas.stream()
                    .map(uc -> uc.getCurso().getIdCurso())
                    .collect(java.util.stream.Collectors.toList());
        }

        ModelAndView mv = new ModelAndView("cursos");
        mv.addObject("usuario", usuario);
        mv.addObject("cursos", cursos);
        mv.addObject("cursosMatriculadosIds", cursosMatriculadosIds);

        return mv;
    }

    // Carrega o formulário para criação de curso
    @GetMapping("/novo")
    public ModelAndView novo() {
        return new ModelAndView("formCurso");
    }

    // Carrega o formulário para edição de curso
    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable Long id) {
        Curso curso = cursoRepository.findById(id).orElse(null);
        ModelAndView mv = new ModelAndView("formCurso");
        mv.addObject("curso", curso);
        mv.addObject("acao", "/cursos/atualizar/" + id);
        return mv;
    }

    // Adiciona um novo curso
    @PostMapping
    public ModelAndView cadastrar(@Valid CursoDTO cursoDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("formCurso");
        }

        Curso curso = new Curso(cursoDTO);
        cursoRepository.save(curso);

        return new ModelAndView("redirect:/cursos");
    }

    // Atualiza um curso (PUT transformado em POST para suportar formulário)
    @PostMapping("/atualizar/{id}")
    public ModelAndView atualizar(@PathVariable Long id, @Valid CursoDTO cursoDTO, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println("Houve um erro: Campos inválidos\n" + result);
            return new ModelAndView("formCurso");
        }

        Curso curso = new Curso(cursoDTO);
        curso.setIdCurso(id);
        cursoRepository.save(curso);

        return new ModelAndView("redirect:/cursos");
    }

    // Deleta um curso
    @GetMapping("/excluir/{id}")
    public ModelAndView excluir(@PathVariable Long id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        cursoRepository.deleteById(id);
        return new ModelAndView("redirect:/cursos");
    }

}
