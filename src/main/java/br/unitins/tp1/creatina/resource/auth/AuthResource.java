package br.unitins.tp1.creatina.resource.auth;

import org.jboss.logging.Logger;

import br.unitins.tp1.creatina.dto.usuario.AuthRequestDTO;
import br.unitins.tp1.creatina.dto.usuario.UsuarioResponseDTO;
import br.unitins.tp1.creatina.model.Usuario;
import br.unitins.tp1.creatina.resource.cliente.ClienteResource;
import br.unitins.tp1.creatina.service.hash.HashService;
import br.unitins.tp1.creatina.service.jwt.JwtService;
import br.unitins.tp1.creatina.service.usuario.UsuarioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    HashService hashService;

    @Inject
    UsuarioService usuarioService;

    @Inject
    JwtService jwtService;

    private static final Logger LOG = Logger.getLogger(ClienteResource.class);

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(AuthRequestDTO authDTO) {
        LOG.info("Executando autorizacao do usuario");
        String hash = hashService.getHashSenha(authDTO.senha());

        Usuario usuario = usuarioService.findByUsernameAndSenha(authDTO.username(), hash);

        if (usuario == null) {
            return Response.status(Status.NO_CONTENT)
                .entity("Usuario não encontrado").build();
        } 
        return Response.ok()
            .header("Authorization", jwtService.generateJwt(UsuarioResponseDTO.valueOf(usuario)))
            .build();

    }

}
