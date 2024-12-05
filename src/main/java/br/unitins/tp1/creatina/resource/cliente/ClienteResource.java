package br.unitins.tp1.creatina.resource.cliente;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import br.unitins.tp1.creatina.dto.cliente.ClienteRequestDTO;
import br.unitins.tp1.creatina.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.creatina.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.creatina.model.Cliente;
import br.unitins.tp1.creatina.model.Creatina;
import br.unitins.tp1.creatina.model.Endereco;
import br.unitins.tp1.creatina.model.Telefone;
import br.unitins.tp1.creatina.service.cliente.ClienteService;
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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    ClienteService clienteService;

    @Inject
    public JsonWebToken jsonWebToken;

    private static final Logger LOG = Logger.getLogger(ClienteResource.class);

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Adm" })
    public Cliente findById(@PathParam("id") Long id) {
        return clienteService.findById(id);
    }

    @GET
    @Path("/nome/{nome}")
    @RolesAllowed({ "Adm" })
    public List<Cliente> findByNome(@PathParam("nome") String nome) {
        LOG.infof("Buscando cliente pelo nome %s", nome);
        return clienteService.findByNome(nome);
    }

    @GET
    @Path("/cpf/{cpf}")
    @RolesAllowed({ "Adm" })
    public Cliente findByCpf(@PathParam("cpf") String cpf) {
        LOG.infof("Buscando cliente com o cpf %s", cpf);
        return clienteService.findByCpf(cpf);
    }

    @GET
    @Path("/email/{email}")
    @RolesAllowed({ "Adm" })
    public Cliente findByEmail(@PathParam("email") String email) {
        LOG.infof("Buscando cliente com o email %s", email);
        return clienteService.findByEmail(email);
    }

    @GET
    @Path("/username/{username}")
    @RolesAllowed({ "Adm" })
    public Cliente findByUsername(@PathParam("username") String username) {
        LOG.infof("Buscando cliente com o username %s", username);
        return clienteService.findByUsername(username);
    }

    @GET
    @RolesAllowed({ "Adm" })
    public List<Cliente> findAll() {
        LOG.infof("Buscando todos os clientes");
        return clienteService.findAll();
    }

    @POST
    @RolesAllowed({"Adm", "User"})
    public Cliente create(ClienteRequestDTO dto) {
        LOG.infov("Criando cliente");
        String username = jsonWebToken.getSubject();
        return clienteService.create(username, dto);
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Adm", "User"})
    public Cliente update(@PathParam("id") Long id, ClienteRequestDTO dto) {
        LOG.infof("Atualizando cliente com id %d", id);
        return clienteService.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Adm", "User"})   
    public Response delete(@PathParam("id") Long id) {
        LOG.infof("Deletetando cliente com id %d", id);
        clienteService.delete(id);
        return Response.noContent().build();
    }

    @POST
    @Path("/{clienteId}/enderecos")
    @RolesAllowed({ "User" })
    public Endereco addEndereco(@PathParam("clienteId") Long clienteId, EnderecoRequestDTO dto) {
        return clienteService.addEndereco(clienteId, dto);
    }

    @PUT
    @Path("/enderecos/{idEndereco}")
    @RolesAllowed({ "User" })
    public Response updateEndereco(@QueryParam("username") String username, @PathParam("idEndereco") Long idEndereco,
            EnderecoRequestDTO dto) {
        clienteService.updateEndereco(username, idEndereco, dto);
        return Response.noContent().build();
    }

    @POST
    @Path("/{clienteId}/telefones")
    @RolesAllowed({ "User" })
    public Telefone addTelefone(@PathParam("clienteId") Long clienteId, TelefoneRequestDTO dto) {
        return clienteService.addTelefone(clienteId, dto);
    }

    @PUT
    @Path("/telefones/{idTelefone}")
    @RolesAllowed({ "User" })
    public Response updateTelefone(@QueryParam("username") String username, @PathParam("idTelefone") Long idTelefone,
            TelefoneRequestDTO dto) {
        clienteService.updateTelefone(username, idTelefone, dto);
        return Response.noContent().build();
    }

    @POST
    @Path("/lista-desejo/{idProduto}")
    @RolesAllowed({ "User" })
    public Response adicionarListaDesejo(@PathParam("idProduto") Long idProduto) {
        String username = jsonWebToken.getSubject();
        LOG.info("Execução do método adicionarListaDesejo para o usuário: " + username);
        clienteService.adicionarListaDesejo(username, idProduto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/lista-desejo/{idProduto}")
    @RolesAllowed({ "User" })
    public Response removerListaDesejo(@PathParam("idProduto") Long idProduto) {
        String username = jsonWebToken.getSubject();
        LOG.info("Execucao do metodo removerProdutoListaDesejo");
        clienteService.removerListaDesejo(username, idProduto);
        return Response.noContent().build();
    }

    @GET
    @Path("/lista-desejo")
    @RolesAllowed({ "Adm", "User" })
    public List<Creatina> getListaDesejos() {
        String username = jsonWebToken.getSubject();
        LOG.info("Execucao do metodo getListaDesejos");
        return clienteService.getListaDesejos(username);
    }
}
