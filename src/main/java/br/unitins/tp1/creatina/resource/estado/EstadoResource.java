package br.unitins.tp1.creatina.resource.estado;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.tp1.creatina.dto.estado.EstadoRequestDTO;
import br.unitins.tp1.creatina.dto.estado.EstadoResponseDTO;
import br.unitins.tp1.creatina.model.Estado;
import br.unitins.tp1.creatina.service.estado.EstadoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/estados")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EstadoResource {

    private static final Logger LOG = Logger.getLogger(EstadoResource.class);

    @Inject
    public EstadoService estadoService;

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Funcionario", "Adm" })
    public Estado findById(@PathParam("id") Long id) {
        LOG.info("Execução do metodo findById. Id: " + id);
        return estadoService.findById(id);
    }

    @GET
    @Path("/search/{nome}")
    @RolesAllowed({ "Funcionario", "Adm" })
    public List<Estado> findByNome(@PathParam("nome") String nome) {
        return estadoService.findByNome(nome);
    }

    @GET
    @RolesAllowed({ "Funcionario", "Adm" })
    public List<Estado> findAll() {
        return estadoService.findAll();
    }

    @POST
    @RolesAllowed({ "Funcionario", "Adm" })
    public EstadoResponseDTO create(EstadoRequestDTO dto) {
        return EstadoResponseDTO.valueOf(estadoService.create(dto));
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({ "Funcionario", "Adm" })
    public void update(@PathParam("id") Long id, EstadoRequestDTO estado) {
        estadoService.update(id, estado);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "Funcionario", "Adm" })
    public void delete(@PathParam("id") Long id) {
        estadoService.delete(id);
    }
    
}
