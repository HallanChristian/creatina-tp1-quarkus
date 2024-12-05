package br.unitins.tp1.creatina.resource.pedido;

import java.util.List;

import org.jboss.logging.Logger;

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
    @RolesAllowed({"Adm"})
    public Response findByUsername(@PathParam("username") String username) {
        LOG.infof("Buscando pedidos para o usuário com username %s", username);
        List<Pedido> pedidos = pedidoService.findByUsername(username);
        return Response.ok(pedidos).build();
    }

    @POST
    @RolesAllowed({"User", "Adm"})
    public Response create(@Valid PedidoRequestDTO dto, @HeaderParam("username") String username) {
        LOG.info("Criando novo pedido");
        Pedido pedido = pedidoService.create(dto, username);
        return Response.status(Status.CREATED).entity(pedido).build();
    }

    @PUT
    @Path("/{id}/estado/{novaSituacaoId}")
    @RolesAllowed({"Adm"})
    public Response updateEstadoPedido(@PathParam("id") Long id, @PathParam("novaSituacaoId") Integer novaSituacaoId) {
        LOG.infof("Atualizando estado do pedido com id %d", id);
        pedidoService.updateEstadoPedido(id, novaSituacaoId);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"User", "Adm"})
    public Response cancelarPedido(@HeaderParam("username") String username, @PathParam("id") Long id) {
        LOG.infof("Cancelando pedido com id %d para o usuário %s", id, username);
        pedidoService.cancelarPedido(username, id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}/retorno")
    @RolesAllowed({"User", "Adm"})
    public Response retornarPedido(@HeaderParam("username") String username, @PathParam("id") Long id) {
        LOG.infof("Retornando pedido com id %d para o usuário %s", id, username);
        pedidoService.retornarPedido(username, id);
        return Response.noContent().build();
    }
}
