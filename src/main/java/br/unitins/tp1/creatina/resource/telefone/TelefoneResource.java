package br.unitins.tp1.creatina.resource.telefone;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.tp1.creatina.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.creatina.model.Telefone;
import br.unitins.tp1.creatina.service.telefone.TelefoneService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/telefones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TelefoneResource {

    @Inject
    public TelefoneService telefoneService;

    private static final Logger LOG = Logger.getLogger(TelefoneResource.class);

    @GET
    @Path("/{id}")
    @RolesAllowed({"Adm"})
    public Response findById(@PathParam("id") Long id) {
        LOG.infof("Buscando telefone com id %d", id);
        Telefone telefone = telefoneService.findById(id);
        return Response.ok(telefone).build();
    }

    @GET
    @Path("/cliente/{id}")
    @RolesAllowed({"Adm"})
    public Response findByCliente(@PathParam("id") Long id) {
        LOG.infof("Buscando telefones para o cliente com id %d", id);
        List<Telefone> telefones = telefoneService.findByCliente(id);
        return Response.ok(telefones).build();
    }

    @GET
    @Path("/funcionario/{id}")
    @RolesAllowed({"Adm"})
    public Response findByFuncionario(@PathParam("id") Long id) {
        LOG.infof("Buscando telefones para o funcionário com id %d", id);
        List<Telefone> telefones = telefoneService.findByFuncionario(id);
        return Response.ok(telefones).build();
    }

    @GET
    @Path("/fornecedor/{id}")
    @RolesAllowed({"Adm"})
    public Response findByFornecedor(@PathParam("id") Long id) {
        LOG.infof("Buscando telefones para o fornecedor com id %d", id);
        List<Telefone> telefones = telefoneService.findByFornecedor(id);
        return Response.ok(telefones).build();
    }

    @GET
    @Path("/numero/{numero}")
    @RolesAllowed({"Adm"})
    public Response findByNumero(@PathParam("numero") String numero) {
        LOG.infof("Buscando telefone pelo número %s", numero);
        List<Telefone> telefones = telefoneService.findByNumero(numero);
        return Response.ok(telefones).build();
    }

    @GET
    @Path("/ddd/{ddd}")
    @RolesAllowed({"Adm"})
    public Response findByDdd(@PathParam("ddd") String ddd) {
        LOG.infof("Buscando telefones com DDD %s", ddd);
        List<Telefone> telefones = telefoneService.findByDdd(ddd);
        return Response.ok(telefones).build();
    }

    @GET
    @RolesAllowed({"Adm"})
    public Response findAll() {
        LOG.info("Buscando todos os telefones");
        List<Telefone> telefones = telefoneService.findAll();
        return Response.ok(telefones).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Adm"})
    public Response update(@PathParam("id") Long id, @Valid TelefoneRequestDTO dto) {
        LOG.infof("Atualizando telefone com id %d", id);
        Telefone telefone = telefoneService.update(id, dto);
        return Response.ok(telefone).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Adm"})
    public Response delete(@PathParam("id") Long id) {
        LOG.infof("Deletando telefone com id %d", id);
        telefoneService.delete(id);
        return Response.noContent().build();
    }
}
