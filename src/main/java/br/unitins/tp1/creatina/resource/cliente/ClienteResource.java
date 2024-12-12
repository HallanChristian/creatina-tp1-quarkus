package br.unitins.tp1.creatina.resource.cliente;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import br.unitins.tp1.creatina.dto.cliente.ClienteRequestDTO;
import br.unitins.tp1.creatina.dto.cliente.ClienteResponseDTO;
import br.unitins.tp1.creatina.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.creatina.dto.endereco.EnderecoResponseDTO;
import br.unitins.tp1.creatina.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.creatina.dto.telefone.TelefoneResponseDTO;
import br.unitins.tp1.creatina.model.Cliente;
import br.unitins.tp1.creatina.model.Creatina;
import br.unitins.tp1.creatina.service.cliente.ClienteService;
import jakarta.annotation.security.RolesAllowed;
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
import jakarta.ws.rs.core.Response.Status;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    ClienteService clienteService;

    @Inject
    public JsonWebToken jsonWebToken;

    private static final Logger LOG = Logger.getLogger(ClienteResource.class);

    @POST
    @RolesAllowed({"User"})
    public Response create(ClienteRequestDTO dto) {
        String username = jsonWebToken.getSubject();
        LOG.infov("Criando cliente" + username);
        return Response.status(Status.CREATED).entity(ClienteResponseDTO.valueOf(clienteService.create(username, dto)))
                .build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"User"})
    public Cliente update(@PathParam("id") Long id, ClienteRequestDTO dto) {
        LOG.infof("Atualizando cliente com id %d", id);
        return clienteService.update(id, dto);
    }

    @POST
    @Path("adicao/endereco")
    @RolesAllowed({ "User" })
    public Response addEndereco(@Valid EnderecoRequestDTO dto) {
        String username = jsonWebToken.getSubject();
        return Response.status(Status.CREATED)
                .entity(EnderecoResponseDTO.valueOf(clienteService.addEndereco(username, dto))).build();
    }

    @GET
    @Path("/perfil")
    @RolesAllowed({ "User" })
    public Response getPerfilCliente() {
        String username = jsonWebToken.getSubject();
        return Response.ok().entity(ClienteResponseDTO.valueOf(clienteService.findByUsername(username))).build();
    }

    @PATCH
    @Path("/enderecos/{idEndereco}")
    @RolesAllowed({ "User" })
    public Response updateEnderecos(@PathParam("id") Long id, @PathParam("idEndereco") Long idEndereco,
            @Valid EnderecoRequestDTO endereco) {
        String username = jsonWebToken.getSubject();
        clienteService.updateEndereco(username, idEndereco, endereco);
        return Response.noContent().build();
    }

    @POST
    @Path("adicao/telefone")
    @RolesAllowed({ "User" })
    public Response addTelefone(@Valid TelefoneRequestDTO dto) {
        String username = jsonWebToken.getSubject();
        return Response.status(Status.CREATED)
                .entity(TelefoneResponseDTO.valueOf(clienteService.addTelefone(username, dto))).build();
    }

    @PATCH
    @Path("/telefones/{idTelefone}")
    @RolesAllowed({ "User" })
    public Response updateTelefones(@PathParam("id") Long id, @PathParam("idTelefone") Long idTelefone,
            @Valid TelefoneRequestDTO telefone) {
        String username = jsonWebToken.getSubject();
        clienteService.updateTelefone(username, idTelefone, telefone);
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
        LOG.info("Execucao do metodo removerProdutoListaDesejo");
        String username = jsonWebToken.getSubject();
        clienteService.removerListaDesejo(username, idProduto);
        return Response.noContent().build();
    }

    @GET
    @Path("/lista-desejo")
    @RolesAllowed({ "Adm", "User" })
    public List<Creatina> getListaDesejos() {
        LOG.info("Execucao do metodo getListaDesejos");
        String username = jsonWebToken.getSubject();
        return clienteService.getListaDesejos(username);
    }
}
