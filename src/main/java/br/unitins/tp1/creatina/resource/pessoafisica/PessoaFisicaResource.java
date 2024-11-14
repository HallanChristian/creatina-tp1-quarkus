package br.unitins.tp1.creatina.resource.pessoafisica;

import java.io.IOException;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.form.PessoaFisicaImageForm;

//import java.util.List;

import br.unitins.tp1.creatina.dto.PessoaFisicaRequestDTO;
import br.unitins.tp1.creatina.dto.PessoaFisicaResponseDTO;
import br.unitins.tp1.creatina.service.fileservice.FileService;
import br.unitins.tp1.creatina.service.pessoafisica.PessoaFisicaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;

@Path("/pessoasfisicas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PessoaFisicaResource {

    @Inject
    public PessoaFisicaService pessoafisicaService;

    @Inject
    public FileService pessoafisicaFileService;

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(PessoaFisicaResponseDTO.valueOf(pessoafisicaService.findById(id))).build();
    }

    @GET
    @Path("/search/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        return Response.ok(pessoafisicaService.findByNome(nome).
                    stream().
                    map(o -> PessoaFisicaResponseDTO.valueOf(o)).
                    toList()).build();
    }

    @GET
    public Response findAll() {
        return Response.ok(pessoafisicaService.findAll().
                    stream().
                    map(o -> PessoaFisicaResponseDTO.valueOf(o)).
                    toList()).build();
    }

    @POST
    public Response create(@Valid PessoaFisicaRequestDTO dto) {
        return Response.status(Status.CREATED).entity(
            PessoaFisicaResponseDTO.valueOf(pessoafisicaService.create(dto))
        ).build();
    
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid PessoaFisicaRequestDTO dto) {
        pessoafisicaService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        pessoafisicaService.delete(id);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id}/upload/imagem")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadImage(@PathParam("id") Long id, @MultipartForm PessoaFisicaImageForm form) {

        try {
        pessoafisicaFileService.save(form.getNomeImagem(), form.getImagem());
        }
        catch (IOException e) {
            Response.status(500).build();
        }
        return Response.noContent().build();
    }

    @GET
    @Path("/download/imagem/{nomeImagem}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadImage(@PathParam("nomeImagem") String nomeImagem) {
        ResponseBuilder response = Response.ok(pessoafisicaFileService.find(nomeImagem));
        response.header("Content-Disposition", "attachment; filename" + nomeImagem);

        return response.build();
    }
    
}
