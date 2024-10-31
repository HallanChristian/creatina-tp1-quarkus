package br.unitins.tp1.faixas;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.creatina.dto.ClienteRequestDTO;
import br.unitins.tp1.creatina.dto.EnderecoRequestDTO;
import br.unitins.tp1.creatina.dto.TelefoneClienteRequestDTO;
import br.unitins.tp1.creatina.model.Cliente;
import br.unitins.tp1.creatina.service.cliente.ClienteService;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class ClienteResourceTest {
    @Inject
    public ClienteService clienteService;

    @Test
    public void testFindById() {
        given()
            .when().get("/clientes/1")
            .then().statusCode(200)
            .body("id", is(1));
    }

    @Test
    public void testFindByNome() {
        given()
            .when().pathParam("nome", "Carlos Henrique")
            .get("/clientes/search/{nome}")
            .then().statusCode(200)
            .body("nome", hasItem(is("Carlos Henrique")));
    }

    @Test
    public void testFindByCpf() {
        given()
            .when().pathParam("cpf", "123.456.789-00")
            .get("/clientes/search/cpf/{cpf}")
            .then().statusCode(200)
            .body("cpf", hasItem(is("123.456.789-00")));
    }

    @Test
    public void testFindAll() {
        given()
            .when().get("/clientes")
            .then().statusCode(200);
    }

    @Test
    public void testCreate() {
        ClienteRequestDTO dto = new ClienteRequestDTO(
            "Carlos Henrique",
            "123.456.789-00",
            LocalDate.of(1990, 5, 15),
            "carlos@example.com",
            List.of(new TelefoneClienteRequestDTO("63", "12345-678")),
            List.of(new EnderecoRequestDTO("SP", "São Paulo", "12345-678", "Centro", "100"))
        );

        Long id = given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when()
            .post("/clientes")
            .then()
            .statusCode(201)
            .extract().path("id");

        Cliente clienteCriado = clienteService.findById(id);
        assertEquals("Carlos Henrique", clienteCriado.getNome());

        clienteService.delete(id);
    }

    @Test
    public void testUpdate() {
        ClienteRequestDTO dto = new ClienteRequestDTO(
            "Carlos Henrique",
            "123.456.789-00",
            LocalDate.of(1990, 5, 15),
            "carlos@example.com",
            List.of(new TelefoneClienteRequestDTO("63", "12345-678")),
            List.of(new EnderecoRequestDTO("SP", "São Paulo", "12345-678", "Centro", "100"))
        );

        Long id = clienteService.create(dto).getId();

        ClienteRequestDTO novoDto = new ClienteRequestDTO(
            "Leandra Cavina",
            "123.456.789-00",
            LocalDate.of(1990, 5, 15),
            "leandra@example.com",
            List.of(new TelefoneClienteRequestDTO("21", "222222222")),
            List.of(new EnderecoRequestDTO("RJ", "Rio de Janeiro", "98765-432", "Copacabana", "200"))
        );

        given()
            .contentType(ContentType.JSON)
            .body(novoDto)
            .when()
            .put("/clientes/" + id)
            .then()
            .statusCode(200);

        Cliente clienteAtualizado = clienteService.findById(id);
        assertEquals("Leandra Cavina", clienteAtualizado.getNome());
        assertEquals("leandra@example.com", clienteAtualizado.getEmail());

        clienteService.delete(id);
    }

    @Test
    public void testDelete() {
        ClienteRequestDTO dto = new ClienteRequestDTO(
            "Carlos Henrique",
            "123.456.789-01",
            LocalDate.of(1990, 5, 15),
            "carlos@example.com",
            List.of(new TelefoneClienteRequestDTO("63", "12345-678")),
            List.of(new EnderecoRequestDTO("SP", "São Paulo", "12345-678", "Centro", "100"))
        );

        Long id = clienteService.create(dto).getId();

        given()
            .when()
            .delete("/clientes/" + id)
            .then().statusCode(204);

        Cliente cliente = clienteService.findById(id);
        assertNull(cliente);
    }
}
