package br.unitins.tp1.faixas;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import br.unitins.tp1.creatina.dto.creatina.CreatinaRequestDTO;
import br.unitins.tp1.creatina.model.Creatina;
import br.unitins.tp1.creatina.service.creatina.CreatinaService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class CreatinaResourceTest {

    @Inject
    public CreatinaService creatinaService;

    @Test
    public void testFindById() {
        given()
            .when().get("/creatinas/1")
            .then().statusCode(200)
            .body("id", is(1));
    }

    @Test
    public void testFindByNome() {
        given()
            .when().pathParam("nome", "Creatina X")
            .get("/creatinas/search/{nome}")
            .then().statusCode(200)
            .body("nome", hasItem(is("Creatina X")));
    }

    @Test
    public void testFindAll() {
        given()
            .when().get("/creatinas")
            .then().statusCode(200);
    }

    @Test
    public void testCreate() {
        CreatinaRequestDTO dto = new CreatinaRequestDTO(
            "Creatina X",
            "Marca Y",
            300,
            "Pura",
            new BigDecimal("149.99"),
            1L, // Categoria ID
            2L  // Fornecedor ID
        );

        Integer id = given() 
            .contentType(ContentType.JSON)
            .body(dto)
            .when()
            .post("/creatinas")
            .then()
            .statusCode(201)
            .log().all()
            .extract().path("id");

        Long longId = id.longValue();
        
        assertNotNull(longId);
        
        Creatina creatinaCriada = creatinaService.findById(longId);
        assertEquals("Creatina X", creatinaCriada.getNome());
        assertEquals("Marca Y", creatinaCriada.getMarca());
        assertEquals(300, creatinaCriada.getQuantidadeEmGramas());
        assertEquals("Pura", creatinaCriada.getTipo());
        assertEquals(new BigDecimal("149.99"), creatinaCriada.getPreco());
        assertEquals(1L, creatinaCriada.getCategoria());
        assertEquals(2L, creatinaCriada.getFornecedor());
        
        creatinaService.delete(longId);
    }

    @Test
    public void testUpdate() {
        CreatinaRequestDTO dto = new CreatinaRequestDTO(
            "Creatina X",
            "Marca Y",
            300,
            "Pura",
            new BigDecimal("149.99"),
            1L, // Categoria ID
            2L  // Fornecedor ID
        );

        long id = creatinaService.create(dto).getId();

        CreatinaRequestDTO novoDto = new CreatinaRequestDTO(
            "Creatina Z",
            "Marca W",
            500,
            "Micronizada",
            new BigDecimal("199.99"),
            3L, // Nova Categoria ID
            4L  // Novo Fornecedor ID
        );

        given()
            .contentType(ContentType.JSON)
            .body(novoDto)
            .when()
            .put("/creatinas/" + id)
            .then()
            .statusCode(204);

        Creatina creatinaAtualizada = creatinaService.findById(id);
        assertEquals("Creatina Z", creatinaAtualizada.getNome());
        assertEquals("Marca W", creatinaAtualizada.getMarca());
        assertEquals(500, creatinaAtualizada.getQuantidadeEmGramas());
        assertEquals("Micronizada", creatinaAtualizada.getTipo());
        assertEquals(new BigDecimal("199.99"), creatinaAtualizada.getPreco());
        assertEquals(3L, creatinaAtualizada.getCategoria());
        assertEquals(4L, creatinaAtualizada.getFornecedor());

        creatinaService.delete(id);
    }

    @Test
    public void testDelete() {
        CreatinaRequestDTO dto = new CreatinaRequestDTO(
            "Creatina X",
            "Marca Y",
            300,
            "Pura",
            new BigDecimal("149.99"),
            1L, // Categoria ID
            2L  // Fornecedor ID
        );

        Long id = Long.valueOf(creatinaService.create(dto).getId());

        given()
            .when()
            .delete("/creatinas/" + id)
            .then().statusCode(204);

        Creatina creatina = creatinaService.findById(id);
        assertNull(creatina);
    }
}
