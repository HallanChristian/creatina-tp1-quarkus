package br.unitins.tp1.creatina.service.jwt;

import br.unitins.tp1.creatina.dto.UsuarioResponseDTO;

public interface JwtService {

    String generateJwt(UsuarioResponseDTO dto);

}