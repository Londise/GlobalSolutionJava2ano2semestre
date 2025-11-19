package br.com.fiap.GlobalSolutionJava.model.repository;

import br.com.fiap.GlobalSolutionJava.model.entity.UsuarioCurso;
import br.com.fiap.GlobalSolutionJava.model.entity.UsuarioCursoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioCursoRepository extends JpaRepository<UsuarioCurso, UsuarioCursoId> {
    List<UsuarioCurso> findByUsuario_IdUsuario(Long usuarioId);
}
