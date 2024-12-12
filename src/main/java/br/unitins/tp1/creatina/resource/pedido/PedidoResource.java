package br.unitins.tp1.creatina.resource.pedido;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import br.unitins.tp1.creatina.dto.pedido.PedidoBasicoResponseDTO;
import br.unitins.tp1.creatina.dto.pedido.PedidoDetalhadoResponseDTO;
import br.unitins.tp1.creatina.dto.pedido.PedidoRequestDTO;
import br.unitins.tp1.creatina.model.Pedido;
import br.unitins.tp1.creatina.service.pedido.PedidoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {

    @Inject
    public PedidoService pedidoService;

    @Inject
    public JsonWebToken jsonWebToken;

    private static final Logger LOG = Logger.getLogger(PedidoResource.class);

    @GET
    @Path("/{id}")
    @RolesAllowed({"User", "Adm"})
    public Response findById(@PathParam("id") Long id, @HeaderParam("username") String username) {
        LOG.infof("Buscando pedido com id %d", id);
        Pedido pedido = pedidoService.detalhesPedido(id, username);
        return Response.ok(pedido).build();
    }

    @GET
    @Path("/search/username/{username}")
    @RolesAllowed({"User", "Adm"})
    public Response findByUsername() {
        String username = jsonWebToken.getSubject();
        LOG.infof("Execução do método findByUsername. Usuário: %s", username);
        List<Pedido> pedidos = pedidoService.findByUsername(username);
        return Response.ok(pedidos.stream().map(PedidoBasicoResponseDTO::valueOf)).build();
    }

    @GET
    @Path("/{id}/detalhes")
    @RolesAllowed({ "User", "Adm" })
    public Response findDetalhesPedido(@PathParam("id") Long id) {
        String username = jsonWebToken.getSubject();
        LOG.infof("Execução do método findDetalhesByPedido. Usuário: %s. ID do pedido: %d", username, id);
        return Response.ok(PedidoDetalhadoResponseDTO.valueOf(pedidoService.detalhesPedido(id, username))).build();
    }

    @POST
    @RolesAllowed({"User", "Adm"})
    public Response create(@Valid PedidoRequestDTO dto) {
        String username = jsonWebToken.getSubject();
        LOG.infof("Execução do método create. Usuário: %s. Dados do pedido: %s", username, dto);
        Pedido pedido = pedidoService.create(dto, username);
        return Response.status(Status.CREATED).entity(PedidoBasicoResponseDTO.valueOf(pedido)).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"User", "Adm"})
    public Response cancelarPedido(@PathParam("id") Long id) {
        String username = jsonWebToken.getSubject();
        LOG.infof("Execução do método cancel. Usuário: %s. ID do pedido para cancelamento: %d", username, id);
        pedidoService.cancelarPedido(username, id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}/retorno")
    @RolesAllowed({"User", "Adm"})
    public Response retornarPedido(@PathParam("id") Long id) {
        String username = jsonWebToken.getSubject();
        LOG.infof("Execução do método returnPedido. Usuário: %s. ID do pedido para devolução: %d", username, id);
        pedidoService.retornarPedido(username, id);
        return Response.noContent().build();
    }
}
