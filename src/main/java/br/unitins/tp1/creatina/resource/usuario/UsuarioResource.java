package br.unitins.tp1.creatina.resource.usuario;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import br.unitins.tp1.creatina.dto.usuario.EmailPatchDTO;
import br.unitins.tp1.creatina.dto.usuario.SenhaPatchDTO;
import br.unitins.tp1.creatina.dto.usuario.UsuarioRequestDTO;
import br.unitins.tp1.creatina.dto.usuario.UsuarioResponseDTO;
import br.unitins.tp1.creatina.service.usuario.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    private UsuarioService usuarioService;

    @Inject
    public JsonWebToken jsonWebToken;

    private static final Logger LOG = Logger.getLogger(UsuarioResource.class);

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        LOG.infof("Buscando usuário com id %d", id);
        return Response.ok(UsuarioResponseDTO.valueOf(usuarioService.findById(id))).build();
    }

    @GET
    @Path("/Username/{username}")
    public Response findByUsername(@PathParam("username") String username) {
        LOG.infof("Buscando usuário com username %s", username);
        return Response.ok(UsuarioResponseDTO.valueOf(usuarioService.findByUsername(username))).build();
    }

    @GET
    public Response findAll() {
        LOG.info("Buscando todos os usuários");
        List<UsuarioResponseDTO> usuarios = usuarioService.findAll().stream()
                .map(UsuarioResponseDTO::valueOf)
                .toList();
        return Response.ok(usuarios).build();
    }

    @POST
    @Path("/adm")
    public Response createAdm(@Valid UsuarioRequestDTO dto) {
        LOG.info("Criando novo usuário");
        UsuarioResponseDTO usuario = UsuarioResponseDTO.valueOf(usuarioService.createAdm(dto));
        return Response.status(Status.CREATED).entity(usuario).build();
    }

    @POST
    @Path("/user")
    public Response createUser(@Valid UsuarioRequestDTO dto) {
        LOG.info("Criando novo usuário");
        UsuarioResponseDTO usuario = UsuarioResponseDTO.valueOf(usuarioService.createUser(dto));
        return Response.status(Status.CREATED).entity(usuario).build();
    }

    @PATCH
    @Path("/{id}/senha")
    public Response updateSenha(@PathParam("id") Long id, @Valid SenhaPatchDTO dto) {
        String username = jsonWebToken.getSubject();
        LOG.infof("Usuário %s atualizando senha do usuário com id %d", username, id);

        // Validar que o usuário autenticado é o mesmo que está atualizando a senha
        usuarioService.updateSenha(id, dto);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id}/email")
    public Response updateEmail(@PathParam("id") Long id, @Valid EmailPatchDTO dto) {
        usuarioService.updateEmail(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Adm"})
    public Response delete(@PathParam("id") Long id) {
        LOG.infof("Deletando usuário com id %d", id);
        usuarioService.delete(id);
        return Response.noContent().build();
    }
}
