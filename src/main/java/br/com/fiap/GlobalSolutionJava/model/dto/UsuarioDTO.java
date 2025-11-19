package br.com.fiap.GlobalSolutionJava.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioDTO(

        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 200, message = "O nome deve ter no máximo 200 caracteres")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Formato de e-mail inválido")
        @Size(max = 150)
        String email,

        @Size(max = 30)
        @NotBlank
        String senha,

        @NotBlank
        String tipoUsuario
) { }
