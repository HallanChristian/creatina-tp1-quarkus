package br.unitins.tp1.faixas;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.creatina.dto.TelefoneFornecedorRequestDTO;
import br.unitins.tp1.creatina.model.TelefoneFornecedor;
import br.unitins.tp1.creatina.service.telefone.TelefoneFornecedorService;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class TelefoneFornecedorResourceTest {

    @Inject
    public TelefoneFornecedorService telefoneFornecedorService;

    @Test
    public void testFindById() {
        given()
            .when().get("/telefonefornecedores/1")
            .then().statusCode(200)
            .body("id", is(1));
    }

    @Test
    public void testFindByFornecedor() {
        long idFornecedor = 1L;

        given()
            .when().pathParam("idFornecedor", idFornecedor)
            .get("/telefonefornecedores/search/{idFornecedor}")
            .then().statusCode(200)
            .body("ddd", hasItem(is("63")));
    }

    @Test
    public void testFindAll() {
        given()
            .when().get("/telefonefornecedores")
            .then().statusCode(200)
            .body("size()", is(greaterThan(0)));
    }

    @Test
    public void testCreate() {
        TelefoneFornecedorRequestDTO dto = new TelefoneFornecedorRequestDTO("63", "3904-1538");

        Integer idInteger = given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when()
            .post("/telefonefornecedores/1")
            .then()
            .statusCode(201)
            .extract().path("id"); 

        Long id = Long.valueOf(idInteger); 

        TelefoneFornecedor telefoneCriado = telefoneFornecedorService.findById(id);
        assertNotNull(telefoneCriado);
        assertEquals("63", telefoneCriado.getDdd());
        assertEquals("3904-1538", telefoneCriado.getNumero());

        telefoneFornecedorService.delete(id);
    }

    @Test
    public void testUpdate() {
        TelefoneFornecedorRequestDTO dto = new TelefoneFornecedorRequestDTO("63", "3904-1538");
        Long id = telefoneFornecedorService.create(dto).getId();

        TelefoneFornecedorRequestDTO novoDto = new TelefoneFornecedorRequestDTO("63", "3904-1234");

        given()
            .contentType(ContentType.JSON)
            .body(novoDto)
            .when()
            .put("/telefonefornecedores/" + id)
            .then()
            .statusCode(204);

        TelefoneFornecedor telefoneAtualizado = telefoneFornecedorService.findById(id);
        assertNotNull(telefoneAtualizado);
        assertEquals("3904-1234", telefoneAtualizado.getNumero());

        telefoneFornecedorService.delete(id);
    }

    @Test
    public void testDelete() {
        TelefoneFornecedorRequestDTO dto = new TelefoneFornecedorRequestDTO("63", "3904-1538");
        Long id = telefoneFornecedorService.create(dto).getId();

        given()
            .when()
            .delete("/telefonefornecedores/" + id)
            .then().statusCode(204);

        TelefoneFornecedor telefone = telefoneFornecedorService.findById(id);
        assertNull(telefone, "O TelefoneFornecedor deve ser nulo após a exclusão.");
    }
}
