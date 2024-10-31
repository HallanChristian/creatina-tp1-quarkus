package br.unitins.tp1.creatina.resource.telefonecliente;

import br.unitins.tp1.creatina.dto.TelefoneClienteRequestDTO;
import br.unitins.tp1.creatina.dto.TelefoneClienteResponseDTO;
import br.unitins.tp1.creatina.model.TelefoneCliente;
import br.unitins.tp1.creatina.service.telefone.TelefoneClienteService;
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

@Path("/telefoneclientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TelefoneClienteResource {

    @Inject
    public TelefoneClienteService telefoneclienteService;

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(TelefoneClienteResponseDTO.valueOf(telefoneclienteService.findById(id))).build();
    }

    @GET
    @Path("/search/{idCliente}")
    public Response findByCliente(@PathParam("idCliente") Long idCliente) {
        return Response.ok(telefoneclienteService.findByCliente(idCliente).
                    stream().
                    map(o -> TelefoneClienteResponseDTO.valueOf(o)).
                    toList()).build();
    }

    @GET
    public Response findAll() {
        return Response.ok(telefoneclienteService.findAll().
                    stream().
                    map(o -> TelefoneClienteResponseDTO.valueOf(o)).
                    toList()).build();
    }

    @POST
    @Path("/{idCliente}")
    public Response create(@PathParam("idCliente") Long idCliente, @Valid TelefoneClienteRequestDTO dto) {
        TelefoneCliente telefone = telefoneclienteService.create(idCliente, dto);
        return Response.status(Status.CREATED).entity(TelefoneClienteResponseDTO.valueOf(telefone)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid TelefoneClienteRequestDTO dto) {
        telefoneclienteService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        telefoneclienteService.delete(id);
        return Response.noContent().build();
    }
    
}
