package br.com.fiap.GlobalSolutionJava.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.PositiveOrZero;

public record CursoDTO(

        Long id,

        @NotBlank(message = "O título é obrigatório")
        String titulo,

        @NotBlank(message = "A descrição é obrigatória")
        String descricao,

        @NotBlank(message = "A categoria é obrigatória")
        String categoria,

        @NotNull(message = "A carga horária é obrigatória")
        @PositiveOrZero(message = "A carga horária deve ser positiva")
        Integer cargaHoraria

) { }
