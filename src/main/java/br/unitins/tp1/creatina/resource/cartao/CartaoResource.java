package br.unitins.tp1.creatina.resource.cartao;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import br.unitins.tp1.creatina.dto.pagamento.CartaoRequestDTO;
import br.unitins.tp1.creatina.dto.pagamento.CartaoResponseDTO;
import br.unitins.tp1.creatina.service.cartao.CartaoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/cartoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartaoResource {

    @Inject
    private CartaoService cartaoService;

    @Inject
    public JsonWebToken jsonWebToken;

    private static final Logger LOG = Logger.getLogger(CartaoResource.class);

    @GET
    @Path("/{id}")
    @RolesAllowed({"Adm", "User"})
    public Response findById(@PathParam("id") Long id) {
        LOG.infof("Buscando cartão com id %d", id);
        return Response.ok(CartaoResponseDTO.valueOf(cartaoService.findById(id))).build();
    }

    @GET
    @Path("/user/{username}")
    @RolesAllowed({"Adm", "User"})
    public Response findByUser(@PathParam("username") String username) {
        LOG.infof("Buscando cartões para o user %s", username);
        return Response.ok(
            cartaoService.findByCliente(username).stream().map(CartaoResponseDTO::valueOf).toList()
        ).build();
    }

    @GET
    @RolesAllowed({"Adm"})
    public Response findAll() {
        LOG.info("Buscando todos os cartões");
        return Response.ok(
            cartaoService.findAll().stream().map(CartaoResponseDTO::valueOf).toList()
        ).build();
    }

    @POST
    @Path("/{username}")
    @RolesAllowed({"User"})
    public Response create(@Valid CartaoRequestDTO dto) {
        String username = jsonWebToken.getSubject();
        LOG.infof("Criando cartão para o user %s", username);
        return Response.status(Status.CREATED)
                .entity(CartaoResponseDTO.valueOf(cartaoService.create(username, dto)))
                .build();
    }

    @PUT
    @Path("/{username}/{idCartao}")
    @RolesAllowed({"User"})
    public Response update(
            @PathParam("idCartao") Long idCartao,
            @Valid CartaoRequestDTO dto) {
            String username = jsonWebToken.getSubject();
        LOG.infof("Atualizando cartão de id %d para o user %s", idCartao, username);
        cartaoService.update(username, idCartao, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{username}/{id}")
    @RolesAllowed({"Adm", "User"})
    public Response delete(@PathParam("id") Long id) {
        String username = jsonWebToken.getSubject();
        LOG.infof("Deletando cartão de id %d do user %s", id, username);
        cartaoService.delete(username, id);
        return Response.noContent().build();
    }
}
