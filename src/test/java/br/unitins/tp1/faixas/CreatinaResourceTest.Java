package br.unitins.tp1.faixas;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.creatina.dto.CreatinaRequestDTO;
import br.unitins.tp1.creatina.model.Creatina;
import br.unitins.tp1.creatina.service.creatina.CreatinaService;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
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
            300.0f,
            "Pura",
            149.99f
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
        
        creatinaService.delete(longId);
    }

    @Test
    public void testUpdate() {
        CreatinaRequestDTO dto = new CreatinaRequestDTO(
            "Creatina X",
            "Marca Y",
            300.0f,
            "Pura",
            149.99f
        );

        long id = creatinaService.create(dto).getId();

        CreatinaRequestDTO novoDto = new CreatinaRequestDTO(
            "Creatina Z",
            "Marca W",
            500.0f,
            "Micronizada",
            199.99f
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

        creatinaService.delete(id);
    }

    @Test
    public void testDelete() {
        CreatinaRequestDTO dto = new CreatinaRequestDTO(
            "Creatina X",
            "Marca Y",
            300.0f,
            "Pura",
            149.99f
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
