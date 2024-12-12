package br.unitins.tp1.creatina.service.usuario;

import java.util.List;

import br.unitins.tp1.creatina.dto.usuario.EmailPatchDTO;
import br.unitins.tp1.creatina.dto.usuario.SenhaPatchDTO;
import br.unitins.tp1.creatina.dto.usuario.UsuarioRequestDTO;
import br.unitins.tp1.creatina.model.Usuario;


public interface UsuarioService {

    Usuario findById(Long id);

    Usuario findByUsernameAndSenha(String username, String senha);

    Usuario findByUsername(String username);

    List<Usuario> findAll();

    Usuario createAdm(UsuarioRequestDTO dto);

    Usuario createUser(UsuarioRequestDTO dto);

    void updateSenha(Long id, SenhaPatchDTO dto);

    void updateEmail(Long id, EmailPatchDTO dto);

    void delete(Long id); 

}