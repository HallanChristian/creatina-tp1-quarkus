package br.unitins.tp1.creatina.dto.usuario;

import br.unitins.tp1.creatina.model.Perfil;
import br.unitins.tp1.creatina.model.Usuario;

public record UsuarioResponseDTO(
    Long id, 
    String username, 
    String senha, 
    Perfil perfil) {

    public static UsuarioResponseDTO valueOf(Usuario usuario) {
        return new UsuarioResponseDTO (
            usuario.getId(), 
            usuario.getUsername(), 
            usuario.getSenha(),
            usuario.getPerfil());
    }

}
