package br.unitins.tp1.creatina.resource.cliente;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.tp1.creatina.dto.cliente.ClienteResponseDTO;
import br.unitins.tp1.creatina.model.Cliente;
import br.unitins.tp1.creatina.service.cliente.ClienteService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/clientes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteAdministrativoResource {

    @Inject
    public ClienteService clienteService;

    private static final Logger LOG = Logger.getLogger(ClienteResource.class);

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Funcionario" })
    public Cliente findById(@PathParam("id") Long id) {
        return clienteService.findById(id);
    }

    @GET
    @Path("/cpf/{cpf}")
    @RolesAllowed({ "Funcionario" })
    public Cliente findByCpf(@PathParam("cpf") String cpf) {
        LOG.infof("Buscando cliente com o cpf %s", cpf);
        return clienteService.findByCpf(cpf);
    }

    @GET
    @Path("/email/{email}")
    @RolesAllowed({ "Funcionario" })
    public Cliente findByEmail(@PathParam("email") String email) {
        LOG.infof("Buscando cliente com o email %s", email);
        return clienteService.findByEmail(email);
    }

    @GET
    @Path("/username/{username}")
    @RolesAllowed({ "Funcionario" })
    public Cliente findByUsername(@PathParam("username") String username) {
        LOG.infof("Buscando cliente com o username %s", username);
        return clienteService.findByUsername(username);
    }

    @GET
    @Path("/search/{nome}")
    @RolesAllowed("Funcionario")
    public Response findByNome(@PathParam("nome") String nome) {
        List<Cliente> clientes = clienteService.findByNome(nome);
        return Response.ok(clientes.stream().map(ClienteResponseDTO::valueOf).toList()).build();
    }

    @GET
    @RolesAllowed("Funcionario")
    public Response findAll() {
        List<Cliente> clientes = clienteService.findAll();
        return Response.ok(clientes.stream().map(ClienteResponseDTO::valueOf).toList()).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Funcionario")
    public Response delete(@PathParam("id") Long id) {
        clienteService.delete(id);
        return Response.noContent().build();
    }

}
