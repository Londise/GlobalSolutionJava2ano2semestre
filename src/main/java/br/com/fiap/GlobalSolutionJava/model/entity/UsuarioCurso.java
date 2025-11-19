package br.com.fiap.GlobalSolutionJava.model.entity;

import jakarta.persistence.*;

@Entity(name = "usuario_curso")
@Table(name = "usuario_curso")

public class UsuarioCurso {
    @EmbeddedId
    private UsuarioCursoId id;

    @ManyToOne
    @MapsId("cursoId")
    @JoinColumn(name = "id_curso")
    private Curso curso;

    @ManyToOne
    @MapsId("usuarioId")
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    // construtores
    public UsuarioCurso() {}

    public UsuarioCurso(Usuario usuario, Curso curso) {
        this.curso = curso;
        this.usuario = usuario;
        this.id = new UsuarioCursoId(usuario.getIdUsuario(), curso.getIdCurso());
    }

    // getters / setters
    public UsuarioCursoId getId() {
        return id;
    }

    public void setId(UsuarioCursoId id) {
        this.id = id;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
