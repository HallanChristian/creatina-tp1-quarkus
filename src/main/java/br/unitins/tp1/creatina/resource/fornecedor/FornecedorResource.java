package br.unitins.tp1.creatina.resource.fornecedor;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.tp1.creatina.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.creatina.dto.fornecedor.FornecedorRequestDTO;
import br.unitins.tp1.creatina.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.creatina.model.Fornecedor;
import br.unitins.tp1.creatina.service.fornecedor.FornecedorService;
import jakarta.annotation.security.RolesAllowed;
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

    private static final Logger LOG = Logger.getLogger(FornecedorResource.class);

    @GET
    @Path("/{id}")
    @RolesAllowed({"Adm", "Funcionario"})
    public Response findById(@PathParam("id") Long id) {
        LOG.infof("Buscando fornecedor com id %d", id);
        Fornecedor fornecedor = fornecedorService.findById(id);
        return Response.ok(fornecedor).build();
    }

    @GET
    @Path("/search/{nome}")
    @RolesAllowed({"Adm", "Funcionario"})
    public Response findByNome(@PathParam("nome") String nome) {
        LOG.infof("Buscando fornecedor pelo nome %s", nome);
        List<Fornecedor> fornecedores = fornecedorService.findByNome(nome);
        return Response.ok(fornecedores).build();
    }

    @GET
    @Path("/search/{cnpj}")
    @RolesAllowed({"Adm", "Funcionario"})
    public Response findByCnpj(@PathParam("cnpj") String cnpj) {
        LOG.infof("Buscando fornecedor com o CNPJ %s", cnpj);
        Fornecedor fornecedor = fornecedorService.findByCnpj(cnpj);
        return Response.ok(fornecedor).build();
    }

    @GET
    @RolesAllowed({"Adm", "Funcionario"})
    public Response findAll() {
        LOG.info("Buscando todos os fornecedores");
        List<Fornecedor> fornecedores = fornecedorService.findAll();
        return Response.ok(fornecedores).build();
    }

    @POST
    @RolesAllowed({"Adm", "Funcionario"})
    public Response create(@Valid FornecedorRequestDTO dto) {
        LOG.info("Criando novo fornecedor");
        Fornecedor fornecedor = fornecedorService.create(dto);
        return Response.status(Status.CREATED).entity(fornecedor).build();
    }

    @POST
    @Path("/{id}/telefones")
    @RolesAllowed({"Adm", "Funcionario"})
    public Response addTelefone(@PathParam("id") Long fornecedorId, @Valid TelefoneRequestDTO telefoneDTO) {
        LOG.infof("Adicionando telefone para fornecedor com id %d", fornecedorId);
        fornecedorService.addTelefone(fornecedorId, telefoneDTO);
        return Response.status(Status.CREATED).build();
    }

    @PUT
    @Path("/{id}/telefones/{idTelefone}")
    @RolesAllowed({"Adm", "Funcionario"})
    public Response updateTelefone(
            @PathParam("id") Long fornecedorId,
            @PathParam("idTelefone") Long telefoneId,
            @Valid TelefoneRequestDTO telefoneDTO) {
        LOG.infof("Atualizando telefone com id %d para fornecedor com id %d", telefoneId, fornecedorId);
        fornecedorService.updateTelefone(fornecedorId, telefoneId, telefoneDTO);
        return Response.noContent().build();
    }

    @POST
    @Path("/{id}/enderecos")
    @RolesAllowed({"Adm", "Funcionario"})
    public Response addEndereco(@PathParam("id") Long fornecedorId, @Valid EnderecoRequestDTO enderecoDTO) {
        LOG.infof("Adicionando endereço para fornecedor com id %d", fornecedorId);
        fornecedorService.addEndereco(fornecedorId, enderecoDTO);
        return Response.status(Status.CREATED).build();
    }

    @PUT
    @Path("/{id}/enderecos/{idEndereco}")
    @RolesAllowed({"Adm", "Funcionario"})
    public Response updateEndereco(
            @PathParam("id") Long fornecedorId,
            @PathParam("idEndereco") Long enderecoId,
            @Valid EnderecoRequestDTO enderecoDTO) {
        LOG.infof("Atualizando endereço com id %d para fornecedor com id %d", enderecoId, fornecedorId);
        fornecedorService.updateEndereco(fornecedorId, enderecoId, enderecoDTO);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Adm", "Funcionario"})
    public Response update(@PathParam("id") Long id, @Valid FornecedorRequestDTO dto) {
        LOG.infof("Atualizando fornecedor com id %d", id);
        Fornecedor fornecedor = fornecedorService.update(id, dto);
        return Response.ok(fornecedor).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Adm"})
    public Response delete(@PathParam("id") Long id) {
        LOG.infof("Deletando fornecedor com id %d", id);
        fornecedorService.delete(id);
        return Response.noContent().build();
    }
    
}
