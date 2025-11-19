package br.com.fiap.GlobalSolutionJava.model.repository;

import br.com.fiap.GlobalSolutionJava.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Exemplo de consulta
    Usuario findByEmail(String email);

    Usuario findByEmailAndSenha(String email, String senha);

}
