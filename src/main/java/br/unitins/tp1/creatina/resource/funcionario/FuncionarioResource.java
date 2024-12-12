package br.unitins.tp1.creatina.resource.funcionario;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import br.unitins.tp1.creatina.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.creatina.dto.funcionario.FuncionarioRequestDTO;
import br.unitins.tp1.creatina.dto.funcionario.FuncionarioResponseDTO;
import br.unitins.tp1.creatina.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.creatina.model.Funcionario;
import br.unitins.tp1.creatina.service.funcionario.FuncionarioService;
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

@Path("/funcionarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FuncionarioResource {

    @Inject
    public FuncionarioService funcionarioService;

    @Inject
    public JsonWebToken jsonWebToken;

    private static final Logger LOG = Logger.getLogger(FuncionarioResource.class);

    @GET
    @Path("/{id}")
    @RolesAllowed({"Adm", "Funcionario"})
    public Response findById(@PathParam("id") Long id) {
        LOG.infof("Buscando funcionário com id %d", id);
        Funcionario funcionario = funcionarioService.findById(id);
        return Response.ok(funcionario).build();
    }

    @GET
    @Path("/search/{nome}")
    @RolesAllowed({"Adm", "Funcionario"})
    public Response findByNome(@PathParam("nome") String nome) {
        LOG.infof("Buscando funcionário pelo nome %s", nome);
        List<Funcionario> funcionarios = funcionarioService.findByNome(nome);
        return Response.ok(funcionarios).build();
    }

    @GET
    @Path("/search/username/{username}")
    @RolesAllowed({"Adm", "Funcionario"})
    public Response findByUsername(@PathParam("username") String username) {
        LOG.infof("Buscando funcionário com o username %s", username);
        Funcionario funcionario = funcionarioService.findByUsername(username);
        return Response.ok(funcionario).build();
    }

    @GET
    @Path("/search/cpf/{cpf}")
    @RolesAllowed({"Adm", "Funcionario"})
    public Response findByCpf(@PathParam("cpf") String cpf) {
        LOG.infof("Buscando funcionário com o CPF %s", cpf);
        Funcionario funcionario = funcionarioService.findByCpf(cpf);
        return Response.ok(funcionario).build();
    }

    @GET
    @Path("/search/email/{email}")
    @RolesAllowed({"Adm", "Funcionario"})
    public Response findByEmail(@PathParam("email") String email) {
        LOG.infof("Buscando funcionário com o email %s", email);
        Funcionario funcionario = funcionarioService.findByEmail(email);
        return Response.ok(funcionario).build();
    }

    @GET
    @RolesAllowed({"Adm", "Funcionario"})
    public Response findAll() {
        LOG.info("Buscando todos os funcionários");
        List<Funcionario> funcionarios = funcionarioService.findAll();
        return Response.ok(funcionarios).build();
    }

    @POST
    @RolesAllowed({"Adm", "Funcionario"})
    public Response create(@Valid FuncionarioRequestDTO dto) {
        String username = jsonWebToken.getSubject();
        LOG.info("Criando funcionário pelo usuário: " + username);
        return Response.status(Status.CREATED)
                .entity(FuncionarioResponseDTO.valueOf(funcionarioService.create(dto)))
                .build();
    }

    @POST
    @Path("/{id}/telefones")
    @RolesAllowed({"Adm", "Funcionario"})
    public Response addTelefone(@PathParam("id") Long funcionarioId, @Valid TelefoneRequestDTO telefoneDTO) {
        LOG.infof("Adicionando telefone para funcionário com id %d", funcionarioId);
        funcionarioService.addTelefone(funcionarioId, telefoneDTO);
        return Response.status(Status.CREATED).build();
    }

    @PUT
    @Path("/{id}/telefones/{idTelefone}")
    @RolesAllowed({"Adm", "Funcionario"})
    public Response updateTelefone(
            @PathParam("id") Long funcionarioId,
            @PathParam("idTelefone") Long telefoneId,
            @Valid TelefoneRequestDTO telefoneDTO) {
        LOG.infof("Atualizando telefone com id %d para funcionário com id %d", telefoneId, funcionarioId);
        funcionarioService.updateTelefone(funcionarioId, telefoneId, telefoneDTO);
        return Response.noContent().build();
    }

    @POST
    @Path("/{id}/enderecos")
    @RolesAllowed({"Adm", "Funcionario"})
    public Response addEndereco(@PathParam("id") Long funcionarioId, @Valid EnderecoRequestDTO enderecoDTO) {
        LOG.infof("Adicionando endereço para funcionário com id %d", funcionarioId);
        funcionarioService.addEndereco(funcionarioId, enderecoDTO);
        return Response.status(Status.CREATED).build();
    }

    @PUT
    @Path("/{id}/enderecos/{idEndereco}")
    @RolesAllowed({"Adm", "Funcionario"})
    public Response updateEndereco(
            @PathParam("id") Long funcionarioId,
            @PathParam("idEndereco") Long enderecoId,
            @Valid EnderecoRequestDTO enderecoDTO) {
        LOG.infof("Atualizando endereço com id %d para funcionário com id %d", enderecoId, funcionarioId);
        funcionarioService.updateEndereco(funcionarioId, enderecoId, enderecoDTO);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Adm"})
    public Response update(@PathParam("id") Long id, @Valid FuncionarioRequestDTO dto) {
        LOG.infof("Atualizando funcionário com id %d", id);
        Funcionario funcionario = funcionarioService.update(id, dto);
        return Response.ok(funcionario).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Adm"})
    public Response delete(@PathParam("id") Long id) {
        LOG.infof("Deletando funcionário com id %d", id);
        funcionarioService.delete(id);
        return Response.noContent().build();
    }
}
