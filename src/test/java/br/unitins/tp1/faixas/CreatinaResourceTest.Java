package br.unitins.tp1.creatina.service.creatina;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import br.unitins.tp1.creatina.dto.CreatinaRequestDTO;
import br.unitins.tp1.creatina.model.Creatina;
import br.unitins.tp1.creatina.resource.creatina.CreatinaResource;
import br.unitins.tp1.creatina.service.creatina.CreatinaService;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import jakarta.inject.Inject;

@QuarkusTest
public class CreatinaResourceTest {

    @Inject
    public CreatinaService creatinaService;

    @Test
    public void testCreate() {
        CreatinaRequestDTO dto = new CreatinaRequestDTO("Creatina X", "Marca A", 300, "Monohidratada", 89.99);
        
        given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when()
                .post("/creatinas")
            .then()
                .statusCode(201)
                .body("nome", is("Creatina X"),
                      "marca", is("Marca A"),
                      "quantidadeEmGramas", is(300),
                      "tipo", is("Monohidratada"),
                      "preco", is(89.99f));

        // Limpar o dado inserido
        Creatina creatina = creatinaService.findByNome("Creatina X").get(0);
        creatinaService.delete(creatina.getId());
    }

    @Test
    public void testFindById() {
        // Primeiro, cria uma creatina para garantir que há uma no banco
        CreatinaRequestDTO dto = new CreatinaRequestDTO("Creatina Y", "Marca B", 400, "Micronizada", 99.99);
        Long id = creatinaService.create(dto).getId();

        given()
            .when().get("/creatinas/" + id)
            .then()
                .statusCode(200)
                .body("id", is(id.intValue()),
                      "nome", is("Creatina Y"),
                      "marca", is("Marca B"),
                      "quantidadeEmGramas", is(400),
                      "tipo", is("Micronizada"),
                      "preco", is(99.99f));

        // Limpar o dado inserido
        creatinaService.delete(id);
    }

    @Test
    public void testFindAll() {
        given()
            .when().get("/creatinas")
            .then()
                .statusCode(200);
    }

    @Test
    public void testUpdate() {
        // Primeiro, cria uma creatina para atualizar
        CreatinaRequestDTO dto = new CreatinaRequestDTO("Creatina Z", "Marca C", 500, "Creatina HCl", 120.50);
        Long id = creatinaService.create(dto).getId();

        // Cria o DTO atualizado
        CreatinaRequestDTO updatedDto = new CreatinaRequestDTO("Creatina Z Atualizada", "Marca C", 500, "Creatina HCl", 125.00);
        
        given()
            .contentType(ContentType.JSON)
            .body(updatedDto)
            .when()
                .put("/creatinas/" + id)
            .then()
                .statusCode(204);
        
        // Verifique se o dado foi atualizado
        Creatina creatina = creatinaService.findById(id);
        assertEquals("Creatina Z Atualizada", creatina.getNome());
        assertEquals(125.00, creatina.getPreco());

        // Limpar o dado inserido
        creatinaService.delete(id);
    }

    @Test
    public void testDelete() {
        // Primeiro, cria uma creatina para excluir
        CreatinaRequestDTO dto = new CreatinaRequestDTO("Creatina W", "Marca D", 600, "Creatina HCl", 110.00);
        Long id = creatinaService.create(dto).getId();

        given()
            .when()
                .delete("/creatinas/" + id)
            .then()
                .statusCode(204);

        // Verifica se o dado foi deletado
        Creatina creatina = creatinaService.findById(id);
        assertNull(creatina);
    }
}
