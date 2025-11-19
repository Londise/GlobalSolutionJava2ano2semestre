package br.com.fiap.GlobalSolutionJava.model.repository;

import br.com.fiap.GlobalSolutionJava.model.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
}