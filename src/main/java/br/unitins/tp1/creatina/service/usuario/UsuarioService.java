package br.unitins.tp1.creatina.service.usuario;

import java.util.List;

import br.unitins.tp1.creatina.model.Usuario;


public interface UsuarioService {

    Usuario findById(Long id);

    Usuario findByUsernameAndSenha(String username, String senha);

    Usuario findByUsername(String username);

    List<Usuario> findByNome(String nome);

    List<Usuario> findAll();

   // Usuario create(UsuarioRequestDTO dto);

    //Usuario update(Long id, UsuarioRequestDTO dto);

    void delete(Long id); 

}