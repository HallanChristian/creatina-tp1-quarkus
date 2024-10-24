package br.unitins.tp1.faixas;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;


import br.unitins.tp1.creatina.dto.ClienteRequestDTO;
import br.unitins.tp1.creatina.model.Cliente;
import br.unitins.tp1.creatina.resource.cliente.ClienteResource;
import br.unitins.tp1.creatina.service.cliente.ClienteService;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import jakarta.inject.Inject;

@QuarkusTest
public class ClienteResourceTest {
    @Inject
    public ClienteService clienteService;

    @Test
    public void testCreate() {
        ClienteRequestDTO dto = new ClienteRequestDTO("Carlos Silva", "123.456.789-10", "1990-01-01", "carlos@example.com");
        given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when()
                .post("/clientes")
            .then()
                .statusCode(201)
                .body("nome", is("Carlos Silva"),
                      "cpf", is("123.456.789-10"));
        
        // Limpar o dado inserido
        Cliente cliente = clienteService.findByNome("Carlos Silva").get(0);
        clienteService.delete(cliente.getId());
    }

    @Test
    public void testFindById() {
        // Primeiro, crie um cliente para garantir que há um cliente para buscar
        ClienteRequestDTO dto = new ClienteRequestDTO("Maria Oliveira", "987.654.321-00", "1995-05-15", "maria@example.com");
        Long id = clienteService.create(dto).getId();

        given()
            .when().get("/clientes/" + id)
            .then().statusCode(200)
            .body("id", is(id.intValue()),
                  "nome", is("Maria Oliveira"));

        // Limpar o dado inserido
        clienteService.delete(id);
    }

    @Test
    public void testFindAll() {
        given()
            .when().get("/clientes")
            .then().statusCode(200);
    }

    @Test
    public void testUpdate() {
        // Primeiro, crie um cliente para atualizar
        ClienteRequestDTO dto = new ClienteRequestDTO("Ana Costa", "111.222.333-44", "1992-03-10", "ana@example.com");
        Long id = clienteService.create(dto).getId();

        ClienteRequestDTO novoDto = new ClienteRequestDTO("Ana Costa Atualizada", "111.222.333-44", "1992-03-10", "ana.atualizada@example.com");
        given()
            .contentType(ContentType.JSON)
            .body(novoDto)
            .when()
                .put("/clientes/" + id)
            .then()
                .statusCode(204);
        
        Cliente cliente = clienteService.findById(id);
        assertEquals("Ana Costa Atualizada", cliente.getNome());

        // Limpar o dado inserido
        clienteService.delete(id);
    }

    @Test
    public void testDelete() {
        // Primeiro, crie um cliente para excluir
        ClienteRequestDTO dto = new ClienteRequestDTO("Pedro Santos", "222.333.444-55", "1988-12-01", "pedro@example.com");
        Long id = clienteService.create(dto).getId();

        given()
            .when()
                .delete("/clientes/" + id)
            .then().statusCode(204);

        Cliente cliente = clienteService.findById(id);
        assertNull(cliente);
    }
}