package br.com.fiap.GlobalSolutionJava.model.entity;

import br.com.fiap.GlobalSolutionJava.model.dto.CursoDTO;
import jakarta.persistence.*;

@Entity(name = "Curso")
@Table(name = "CURSO")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CURSO")
    private Long idCurso;

    @Column(name = "TITULO")
    private String titulo;

    @Lob
    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "CATEGORIA")
    private String categoria;

    @Column(name = "CARGA_HORARIA")
    private Integer cargaHoraria;

    // Construtor vazio
    public Curso() {
    }

    // Construtor com DTO (conversão segura de cargaHoraria)
    public Curso(CursoDTO cursoDTO) {
        this.titulo = cursoDTO.titulo();
        this.descricao = cursoDTO.descricao();
        this.categoria = cursoDTO.categoria();
        // trata caso o DTO use Double ou Integer
        if (cursoDTO.cargaHoraria() != null) {
            // se for Double -> converte; se for Integer -> converte via Number
            Object ch = cursoDTO.cargaHoraria();
            if (ch instanceof Number) {
                this.cargaHoraria = ((Number) ch).intValue();
            } else {
                // tenta parsear por segurança (caso venha string) — evita NPE
                try {
                    this.cargaHoraria = Integer.parseInt(ch.toString());
                } catch (Exception e) {
                    this.cargaHoraria = null;
                }
            }
        } else {
            this.cargaHoraria = null;
        }
    }

    // Getters e Setters
    public Long getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Long idCurso) {
        this.idCurso = idCurso;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
