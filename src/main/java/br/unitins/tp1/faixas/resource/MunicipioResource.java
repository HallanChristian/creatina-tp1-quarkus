package br.unitins.tp1.faixas.resource;

//import java.util.List;

import br.unitins.tp1.faixas.dto.MunicipioRequestDTO;
import br.unitins.tp1.faixas.dto.MunicipioResponseDTO;
import br.unitins.tp1.faixas.service.MunicipioService;
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

@Path("/municipios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MunicipioResource {

    @Inject
    public MunicipioService municipioService;

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(MunicipioResponseDTO.valueOf(municipioService.findById(id))).build();
    }

    @GET
    @Path("/search/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        return Response.ok(municipioService.findByNome(nome).
                    stream().
                    map(o -> MunicipioResponseDTO.valueOf(o)).
                    toList()).build();
    }

    @GET
    public Response findAll() {
        return Response.ok(municipioService.findAll().
                    stream().
                    map(o -> MunicipioResponseDTO.valueOf(o)).
                    toList()).build();
    }

    @POST
    public Response create(@Valid MunicipioRequestDTO dto) {
        return Response.status(Status.CREATED).entity(
            MunicipioResponseDTO.valueOf(municipioService.create(dto))
        ).build();
    
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid MunicipioRequestDTO dto) {
        municipioService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        municipioService.delete(id);
        return Response.noContent().build();
    }
    
}
