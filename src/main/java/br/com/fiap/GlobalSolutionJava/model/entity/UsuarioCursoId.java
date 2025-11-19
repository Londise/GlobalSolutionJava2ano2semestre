package br.com.fiap.GlobalSolutionJava.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class UsuarioCursoId {
    @Column(name = "id_curso")
    private Long cursoId;

    @Column(name = "id_usuario")
    private Long usuarioId;

    // construtores

    public UsuarioCursoId() {}

    public UsuarioCursoId(Long cursoId, Long usuarioId) {
        this.cursoId = cursoId;
        this.usuarioId = usuarioId;
    }

    // getters / setters

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    // equals e hashCode (necessário para chave composta)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioCursoId)) return false;
        UsuarioCursoId that = (UsuarioCursoId) o;
        return Objects.equals(cursoId, that.cursoId) &&
                Objects.equals(usuarioId, that.usuarioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cursoId, usuarioId);
    }
}
