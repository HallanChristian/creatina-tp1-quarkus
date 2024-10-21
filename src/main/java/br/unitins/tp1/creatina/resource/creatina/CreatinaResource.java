package br.unitins.tp1.creatina.resource.creatina;

//import java.util.List;

import br.unitins.tp1.creatina.dto.CreatinaRequestDTO;
import br.unitins.tp1.creatina.dto.CreatinaResponseDTO;
import br.unitins.tp1.creatina.service.creatina.CreatinaService;
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
public class CreatinaResource {

    @Inject
    public CreatinaService creatinaService;

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(CreatinaResponseDTO.valueOf(creatinaService.findById(id))).build();
    }

    @GET
    @Path("/search/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        return Response.ok(creatinaService.findByNome(nome).
                    stream().
                    map(o -> CreatinaResponseDTO.valueOf(o)).
                    toList()).build();
    }

    @GET
    public Response findAll() {
        return Response.ok(creatinaService.findAll().
                    stream().
                    map(o -> CreatinaResponseDTO.valueOf(o)).
                    toList()).build();
    }

    @POST
    public Response create(@Valid CreatinaRequestDTO dto) {
        return Response.status(Status.CREATED).entity(
            CreatinaResponseDTO.valueOf(creatinaService.create(dto))
        ).build();
    
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid CreatinaRequestDTO dto) {
        creatinaService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        creatinaService.delete(id);
        return Response.noContent().build();
    }
    
}
