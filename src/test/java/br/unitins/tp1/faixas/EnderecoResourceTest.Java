package br.unitins.tp1.creatina.service.endereco;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import br.unitins.tp1.creatina.dto.EnderecoRequestDTO;
import br.unitins.tp1.creatina.model.Endereco;
import br.unitins.tp1.creatina.resource.endereco.EnderecoResource;
import br.unitins.tp1.creatina.service.endereco.EnderecoService;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import jakarta.inject.Inject;

@QuarkusTest
public class EnderecoResourceTest {

    @Inject
    public EnderecoService enderecoService;

    @Test
    public void testCreate() {
        EnderecoRequestDTO dto = new EnderecoRequestDTO("77000-000", "Rua Exemplo", "123", "Palmas", "TO");
        
        given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when()
                .post("/enderecos")
            .then()
                .statusCode(201)
                .body("cep", is("77000-000"),
                      "logradouro", is("Rua Exemplo"),
                      "numero", is("123"),
                      "cidade", is("Palmas"),
                      "estado", is("TO"));

        // Limpar o dado inserido
        Endereco endereco = enderecoService.findByCliente(1L).get(0);  // Exemplo, ajuste conforme necessário
        enderecoService.delete(endereco.getId());
    }

    @Test
    public void testFindById() {
        // Cria um endereço para garantir que há um no banco
        EnderecoRequestDTO dto = new EnderecoRequestDTO("77001-111", "Rua Teste", "321", "Palmas", "TO");
        Long id = enderecoService.create(dto).getId();

        given()
            .when().get("/enderecos/" + id)
            .then()
                .statusCode(200)
                .body("id", is(id.intValue()),
                      "cep", is("77001-111"),
                      "logradouro", is("Rua Teste"),
                      "numero", is("321"),
                      "cidade", is("Palmas"),
                      "estado", is("TO"));

        // Limpar o dado inserido
        enderecoService.delete(id);
    }

    @Test
    public void testFindAll() {
        given()
            .when().get("/enderecos")
            .then()
                .statusCode(200);
    }

    @Test
    public void testUpdate() {
        // Cria um endereço para atualizar
        EnderecoRequestDTO dto = new EnderecoRequestDTO("77002-222", "Avenida Central", "500", "Palmas", "TO");
        Long id = enderecoService.create(dto).getId();

        // Cria o DTO atualizado
        EnderecoRequestDTO updatedDto = new EnderecoRequestDTO("77002-222", "Avenida Central", "600", "Palmas", "TO");
        
        given()
            .contentType(ContentType.JSON)
            .body(updatedDto)
            .when()
                .put("/enderecos/" + id)
            .then()
                .statusCode(204);
        
        // Verifique se o dado foi atualizado
        Endereco endereco = enderecoService.findById(id);
        assertEquals("600", endereco.getNumero());

        // Limpar o dado inserido
        enderecoService.delete(id);
    }

    @Test
    public void testDelete() {
        // Cria um endereço para excluir
        EnderecoRequestDTO dto = new EnderecoRequestDTO("77003-333", "Rua Nova", "777", "Palmas", "TO");
        Long id = enderecoService.create(dto).getId();

        given()
            .when()
                .delete("/enderecos/" + id)
            .then()
                .statusCode(204);

        // Verifica se o dado foi deletado
        Endereco endereco = enderecoService.findById(id);
        assertNull(endereco);
    }

    @Test
    public void testDeleteByCliente() {
        // Cria dois endereços associados a um cliente
        EnderecoRequestDTO dto1 = new EnderecoRequestDTO("77004-444", "Rua A", "101", "Palmas", "TO");
        EnderecoRequestDTO dto2 = new EnderecoRequestDTO("77005-555", "Rua B", "202", "Palmas", "TO");
        
        enderecoService.create(dto1);
        enderecoService.create(dto2);

        // Delete os endereços desse cliente
        Long clienteId = 1L;  // Ajustar conforme sua lógica de identificação do cliente
        enderecoService.deleteByCliente(clienteId);

        // Verifique se os endereços foram deletados
        List<Endereco> enderecos = enderecoService.findByCliente(clienteId);
        assertEquals(0, enderecos.size());
    }
}
