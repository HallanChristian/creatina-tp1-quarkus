package br.unitins.tp1.creatina.resource.creatina;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.unitins.tp1.creatina.dto.creatina.CreatinaRequestDTO;
import br.unitins.tp1.creatina.form.ImageForm;
import br.unitins.tp1.creatina.model.Creatina;
import br.unitins.tp1.creatina.service.creatina.CreatinaFileServiceImpl;
import br.unitins.tp1.creatina.service.creatina.CreatinaService;
import br.unitins.tp1.creatina.validation.ValidationException;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;

@Path("/creatinas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CreatinaResource {

    @Inject
    public CreatinaService creatinaService;

    @Inject
    public CreatinaFileServiceImpl creatinaFileServiceImpl;

    private static final Logger LOG = Logger.getLogger(CreatinaResource.class);

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Adm" })
    public Creatina getById(@PathParam("id") Long id) {
        return creatinaService.findById(id);
    }

    @GET
    @Path("/nome/{nome}")
    @RolesAllowed({ "Adm" })
    public List<Creatina> getByNome(@PathParam("nome") String nome) {
        return creatinaService.findByNome(nome);
    }

    @GET
    @Path("/marca/{marca}")
    @RolesAllowed({ "Adm" })
    public List<Creatina> getByMarca(@PathParam("marca") String marca) {
        return creatinaService.findByMarca(marca);
    }

    @GET
    @Path("/tipo/{tipo}")
    @RolesAllowed({ "Adm" })
    public List<Creatina> getByTipo(@PathParam("tipo") String tipo) {
        return creatinaService.findByTipo(tipo);
    }

    @GET
    @Path("/preco")
    @RolesAllowed({ "Adm" })
    public List<Creatina> getByPreco(@QueryParam("precoMin") BigDecimal precoMin, @QueryParam("precoMax") BigDecimal precoMax) {
        return creatinaService.findByPreco(precoMin, precoMax);
    }

    @GET
    @Path("/filtros")
    @RolesAllowed({ "Adm" })
    public List<Creatina> getByFilters(@QueryParam("nome") String nome, @QueryParam("marca") String marca, 
                                       @QueryParam("tipo") String tipo, @QueryParam("precoMin") BigDecimal precoMin, 
                                       @QueryParam("precoMax") BigDecimal precoMax) {
        return creatinaService.findByFilters(nome, marca, tipo, precoMin, precoMax);
    }

    @GET
    @RolesAllowed({ "Adm" })
    public List<Creatina> getAll() {
        return creatinaService.findAll();
    }

    @POST
    @RolesAllowed({ "Adm" })
    public Creatina create(CreatinaRequestDTO dto) {
        return creatinaService.create(dto);
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({ "Adm" })
    public Creatina update(@PathParam("id") Long id, CreatinaRequestDTO dto) {
        return creatinaService.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "Adm" })
    public Response delete(@PathParam("id") Long id) {
        creatinaService.delete(id);
        return Response.noContent().build();
    }

    @PATCH
    @RolesAllowed({ "Adm" })
    @Path("/{idCreatina}/upload/imagem")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadImage(@PathParam("idCreatina") Long id, @MultipartForm ImageForm form) {
        LOG.info("Execução do uploadImage. Id da creatina: " + id);
        LOG.info("Nome do arquivo recebido para upload: " + form.getNomeImagem());

        try {
            String nomeImagem = creatinaFileServiceImpl.save(form.getNomeImagem(), form.getImagem());
            LOG.info("Arquivo salvo com sucesso. Novo nome: " + nomeImagem);
            creatinaService.updateNomeImagem(id, nomeImagem);
            LOG.info("Nome da imagem atualizado no banco de dados.");
        } catch (IOException e) {
            LOG.error("Erro ao salvar o arquivo. Detalhes: " + e.getMessage(), e);
            return Response.status(500).entity("Erro ao salvar o arquivo.").build();
        }

        return Response.noContent().build();
    }

    @GET
    @RolesAllowed({ "Adm" })
    @Path("/download/imagem/{nomeImagem}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadImage(@PathParam("nomeImagem") String nomeImagem) {
        LOG.info("Execução do método downloadImage.");
        LOG.info("Nome da imagem solicitado para download: " + nomeImagem);

        try {
            // Busca o arquivo pelo nome
            File arquivo = creatinaFileServiceImpl.find(nomeImagem);
            LOG.info("Arquivo localizado com sucesso: " + arquivo.getAbsolutePath());

            // Retorna o arquivo como resposta
            ResponseBuilder response = Response.ok(arquivo);
            response.header("Content-Disposition", "attachment; filename=" + nomeImagem);
            return response.build();
        } catch (ValidationException e) {
            LOG.error("Erro na validação: " + e.getMessage());
            return Response.status(404).entity("Arquivo não encontrado.").build();
        } catch (Exception e) {
            LOG.error("Erro ao realizar o download: " + e.getMessage(), e);
            return Response.status(500).entity("Erro interno no servidor.").build();
        }
    }
}
