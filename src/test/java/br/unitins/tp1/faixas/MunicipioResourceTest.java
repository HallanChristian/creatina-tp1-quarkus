package br.unitins.tp1.faixas;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.creatina.dto.municipio.MunicipioRequestDTO;
import br.unitins.tp1.creatina.model.Municipio;
import br.unitins.tp1.creatina.service.municipio.MunicipioService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class MunicipioResourceTest {

    @Inject
    public MunicipioService municipioService;

    @Test
    @TestSecurity(user = "test", roles = { "Administrador", "Funcionario" })
    void testCreate() {

        MunicipioRequestDTO dto = new MunicipioRequestDTO("Monte do Carmo", 1L);

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/municipios")
                .then()
                .statusCode(201)
                .body("nome", is("Monte do Carmo"),
                        "estado.nome", is("Tocantins"),
                        "estado.sigla", is("TO"));

        municipioService.delete(municipioService.findByNome("Monte do Carmo").getFirst().getId());
    }

    @Test
    @TestSecurity(user = "test", roles = { "Administrador", "Funcionario" })
    void testDelete() {
        Long id = municipioService.create(new MunicipioRequestDTO("Torres Tortas", 1L)).getId();

        given()
                .when()
                .delete("/municipios/{id}", id)
                .then().statusCode(204);
    }

    @Test
    @TestSecurity(user = "test", roles = { "Administrador", "Funcionario" })
    void testFindAll() {
        given()
                .when().get("/municipios")
                .then().statusCode(200);
    }

    @Test
    @TestSecurity(user = "test", roles = { "Administrador", "Funcionario" })
    void testFindById() {
        given()
                .when().get("/municipios/1")
                .then().statusCode(200)
                .body("id", is(1));
    }

    @Test
    @TestSecurity(user = "test", roles = { "Administrador", "Funcionario" })
    void testFindByNome() {
        given()
                .when()
                .get("/municipios/search/{nome}", "Palmas")
                .then().statusCode(200)
                .body("nome", hasItem(is("Palmas")));
    }

    @Test
    @TestSecurity(user = "test", roles = { "Administrador", "Funcionario" })
    void testUpdate() {
        MunicipioRequestDTO dto = new MunicipioRequestDTO("Torres Tortas", 1l);
        Long id = municipioService.create(dto).getId();

        MunicipioRequestDTO novoDto = new MunicipioRequestDTO("Torres Retas", 1l);

        given()
                .contentType(ContentType.JSON)
                .body(novoDto)
                .when()
                .put("/municipios/{id}", id)
                .then()
                .statusCode(204);

        Municipio municipio = municipioService.findById(id);

        assertEquals(municipio.getNome(), "Torres Retas");

        municipioService.delete(id);

    }
}