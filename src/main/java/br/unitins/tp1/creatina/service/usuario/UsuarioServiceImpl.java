package br.unitins.tp1.creatina.service.usuario;

import java.util.List;

import br.unitins.tp1.creatina.model.Usuario;
import br.unitins.tp1.creatina.repository.usuario.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    public UsuarioRepository usuarioRepository;

    @Override
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario findByUsernameAndSenha(String username, String senha) {
        return usuarioRepository.findByUsernameAndSenha(username, senha);
    }

    @Override
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public List<Usuario> findByNome(String nome) {
        return null;
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll().list();
    }


    @Override
    @Transactional
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }
    
}