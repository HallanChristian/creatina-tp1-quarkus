package br.unitins.tp1.creatina.service.usuario;

import java.util.List;

import br.unitins.tp1.creatina.dto.usuario.SenhaPatchDTO;
import br.unitins.tp1.creatina.dto.usuario.UsuarioRequestDTO;
import br.unitins.tp1.creatina.model.Perfil;
import br.unitins.tp1.creatina.model.Usuario;
import br.unitins.tp1.creatina.repository.UsuarioRepository;
import br.unitins.tp1.creatina.service.hash.HashService;
import br.unitins.tp1.creatina.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    public UsuarioRepository usuarioRepository;

    @Inject
    public HashService hashService;

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
    public List<Usuario> findAll() {
        return usuarioRepository.findAll().list();
    }


    @Override
    @Transactional
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Usuario createAdm(UsuarioRequestDTO dto) {
        if (existeUsername(dto.username())) {
            throw new ValidationException("username", "O username é invalido");
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.username());
        usuario.setPerfil(List.of(Perfil.ADM));
        usuario.setSenha(hashService.getHashSenha(dto.senha()));
        usuarioRepository.persist(usuario);

        return usuario;
    }

    @Override
    @Transactional
    public Usuario createUser(UsuarioRequestDTO dto) {
        if (existeUsername(dto.username())) {
            throw new ValidationException("username", "O username é invalido");
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.username());
        usuario.setPerfil(List.of(Perfil.USER));
        usuario.setSenha(hashService.getHashSenha(dto.senha()));
        usuarioRepository.persist(usuario);

        return usuario;
    }

    private boolean existeUsername(String username) {
        Usuario usuario = findByUsername(username);
        if (usuario != null) {
            return true;
        }

        return false;
    }


    @Override
    @Transactional
    public void updateSenha(Long id, SenhaPatchDTO dto) {
        Usuario usuario = findById(id);
        usuario.setSenha(hashService.getHashSenha(dto.senha()));
    }
    
}
