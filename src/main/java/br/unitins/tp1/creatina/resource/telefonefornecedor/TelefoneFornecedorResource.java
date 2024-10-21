package br.unitins.tp1.creatina.resource.telefonefornecedor;

import br.unitins.tp1.creatina.dto.TelefoneFornecedorRequestDTO;
import br.unitins.tp1.creatina.dto.TelefoneFornecedorResponseDTO;
import br.unitins.tp1.creatina.service.telefone.TelefoneFornecedorService;
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

@Path("/pessoasfisicas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TelefoneFornecedorResource {

    @Inject
    public TelefoneFornecedorService telefonefornecedorService;

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(TelefoneFornecedorResponseDTO.valueOf(telefonefornecedorService.findById(id))).build();
    }

    @GET
    @Path("/search/{idFornecedor}")
    public Response findByFornecedor(@PathParam("idFornecedor") Long idFornecedor) {
        return Response.ok(telefonefornecedorService.findByFornecedor(idFornecedor).
                    stream().
                    map(o -> TelefoneFornecedorResponseDTO.valueOf(o)).
                    toList()).build();
    }

    @GET
    public Response findAll() {
        return Response.ok(telefonefornecedorService.findAll().
                    stream().
                    map(o -> TelefoneFornecedorResponseDTO.valueOf(o)).
                    toList()).build();
    }

    @POST
    @Path("/{idFornecedor}")
    public Response create(@PathParam("id") Long id, @Valid TelefoneFornecedorRequestDTO dto) {
        return Response.status(Status.CREATED).entity(TelefoneFornecedorResponseDTO.valueOf(telefonefornecedorService.create(dto, id))).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid TelefoneFornecedorRequestDTO dto) {
        telefonefornecedorService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        telefonefornecedorService.delete(id);
        return Response.noContent().build();
    }
    
}
