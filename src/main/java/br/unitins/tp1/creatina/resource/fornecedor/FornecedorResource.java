package br.unitins.tp1.creatina.resource.fornecedor;

import br.unitins.tp1.creatina.dto.FornecedorRequestDTO;
import br.unitins.tp1.creatina.dto.FornecedorResponseDTO;
import br.unitins.tp1.creatina.dto.TelefoneFornecedorRequestDTO;
import br.unitins.tp1.creatina.service.fornecedor.FornecedorService;
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

@Path("/fornecedores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FornecedorResource {

    @Inject
    public FornecedorService fornecedorService;

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(FornecedorResponseDTO.valueOf(fornecedorService.findById(id))).build();
    }

    @GET
    @Path("/search/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        return Response.ok(fornecedorService.findByNome(nome).
                    stream().
                    map(o -> FornecedorResponseDTO.valueOf(o)).
                    toList()).build();
    }

    @GET
    @Path("/search/cnpj/{cnpj}")
    public Response findByCnpj(@PathParam("cnpj") String cnpj) {
        return Response.ok(fornecedorService.findByCnpj(cnpj)
            .stream()
            .map(FornecedorResponseDTO::valueOf)
            .toList()).build();
    }

    @GET
    public Response findAll() {
        return Response.ok(fornecedorService.findAll().
                    stream().
                    map(o -> FornecedorResponseDTO.valueOf(o)).
                    toList()).build();
    }

    @POST
    public Response create(@Valid FornecedorRequestDTO dto) {
        return Response.status(Status.CREATED).entity(
            FornecedorResponseDTO.valueOf(fornecedorService.create(dto))
        ).build();
    
    }

    @POST
    @Path("/{id}/telefones")
    public Response addEndereco(@PathParam("id") Long fornecedorId, @Valid TelefoneFornecedorRequestDTO telefoneDTO) {
        fornecedorService.addTelefone(fornecedorId, telefoneDTO);
        return Response.status(Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid FornecedorRequestDTO dto) {
        fornecedorService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        fornecedorService.delete(id);
        return Response.noContent().build();
    }
    
}
