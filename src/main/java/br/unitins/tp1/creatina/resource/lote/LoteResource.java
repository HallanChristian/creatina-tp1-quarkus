package br.unitins.tp1.creatina.resource.lote;

import br.unitins.tp1.creatina.dto.lote.LoteRequestDTO;
import br.unitins.tp1.creatina.model.Lote;
import br.unitins.tp1.creatina.service.lote.LoteService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.jboss.logging.Logger;

import java.util.List;

@Path("/lotes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoteResource {

    @Inject
    public LoteService loteService;

    private static final Logger LOG = Logger.getLogger(LoteResource.class);

    @GET
    @Path("/{id}")
    @RolesAllowed({"Adm"})
    public Response findById(@PathParam("id") Long id) {
        LOG.infof("Buscando lote com id %d", id);
        Lote lote = loteService.findById(id);
        return Response.ok(lote).build();
    }

    @GET
    @Path("/search/codigo/{codigo}")
    @RolesAllowed({"Adm"})
    public Response findByCodigo(@PathParam("codigo") String codigo) {
        LOG.infof("Buscando lote com o código %s", codigo);
        Lote lote = loteService.findByCodigo(codigo);
        return Response.ok(lote).build();
    }

    @GET
    @Path("/search/creatina/{idCreatina}")
    @RolesAllowed({"Adm"})
    public Response findByIdCreatina(@PathParam("idCreatina") Long idCreatina) {
        LOG.infof("Buscando lote pelo id da creatina %d", idCreatina);
        Lote lote = loteService.findByIdCreatina(idCreatina);
        return Response.ok(lote).build();
    }

    @GET
    @RolesAllowed({"Adm"})
    public Response findAll() {
        LOG.info("Buscando todos os lotes");
        List<Lote> lotes = loteService.findAll();
        return Response.ok(lotes).build();
    }

    @POST
    @RolesAllowed({"Adm"})
    public Response create(@Valid LoteRequestDTO dto) {
        LOG.info("Criando novo lote");
        Lote lote = loteService.create(dto);
        return Response.status(Status.CREATED).entity(lote).build();
    }

    @GET
    @Path("/estoque/creatina/{idCreatina}")
    @RolesAllowed({"Adm"})
    public Response findEstoqueTotalPorCreatina(@PathParam("idCreatina") Long idCreatina) {
        LOG.infof("Buscando estoque total para a creatina com id %d", idCreatina);
        Integer estoqueTotal = loteService.findEstoqueTotalPorCreatina(idCreatina);
        return Response.ok(estoqueTotal).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Adm"})
    public Response update(@PathParam("id") Long id, @Valid LoteRequestDTO dto) {
        LOG.infof("Atualizando lote com id %d", id);
        Lote lote = loteService.update(id, dto);
        return Response.ok(lote).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Adm"})
    public Response delete(@PathParam("id") Long id) {
        LOG.infof("Deletando lote com id %d", id);
        loteService.delete(id);
        return Response.noContent().build();
    }
}
