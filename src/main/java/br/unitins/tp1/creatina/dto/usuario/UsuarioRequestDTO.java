package br.unitins.tp1.creatina.dto.usuario;

import br.unitins.tp1.creatina.model.Perfil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(
        @NotBlank(message = "Informe o username de usuario") String username,
        @NotBlank(message = "Informe a senha do usuario") @Size(min = 5, max = 20, message = "A senha deve ter entre 5 e 20 caracteres") String senha,
        Perfil perfil) {
    
}
