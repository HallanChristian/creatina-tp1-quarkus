package br.unitins.tp1.faixas;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.creatina.dto.lote.LoteRequestDTO;
import br.unitins.tp1.creatina.model.Lote;
import br.unitins.tp1.creatina.service.lote.LoteService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class LoteResourceTest {

    @Inject
    public LoteService loteService;

    @Test
    @TestSecurity(user = "test", roles = { "Administrador", "Funcionario" })
    @DisplayName("Deve retornar status code 201 para o metodo POST em /lotes")
    void testCreate() {
        LoteRequestDTO dto = new LoteRequestDTO(1L, LocalDate.now(), LocalDate.now().plusMonths(6), "COD-TEST", 100);
        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/lotes")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("estoque", is(100));

        // Limpeza após o teste
        loteService.delete(loteService.findByCodigo(dto.codigo()).getId());
    }

    @Test
    @TestSecurity(user = "test", roles = { "Administrador", "Funcionario" })
    @DisplayName("Deve retornar status code 204 para o metodo DELETE em /lotes/{id}")
    void testDelete() {
        LoteRequestDTO dto = new LoteRequestDTO(1L, LocalDate.now(), LocalDate.now().plusMonths(6), "COD-TEST", 100);
        Long id = loteService.create(dto).getId();

        given()
                .when()
                .delete("/lotes/{id}", id)
                .then()
                .statusCode(204);

        // Validando que o lote foi removido
        Lote deletedLote = loteService.findById(id);
        assertEquals(null, deletedLote);
    }

    @Test
    @TestSecurity(user = "test", roles = { "Administrador", "Funcionario" })
    @DisplayName("Deve retornar status code 200 para o metodo GET em /lotes/search/codigo")
    void testFindByCodigo() {
        given()
                .queryParam("codigo", "COD-CREATINA-001")
                .when()
                .get("/lotes/search/codigo")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("estoque", is(1));
    }

    @Test
    @TestSecurity(user = "test", roles = { "Administrador", "Funcionario" })
    @DisplayName("Deve retornar status code 200 para o metodo GET em /lotes/{id}")
    void testFindById() {
        Long idExistente = 1L;
        given()
                .when()
                .get("/lotes/{id}", idExistente)
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "test", roles = { "Administrador", "Funcionario" })
    @DisplayName("Deve retornar status code 200 para o metodo GET em /lotes/search/creatina")
    void testFindByCreatina() {
        given()
                .queryParam("idCreatina", 1)
                .when()
                .get("/lotes/search/creatina")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("estoque", is(1));
    }

    @Test
    @TestSecurity(user = "test", roles = { "Administrador", "Funcionario" })
    @DisplayName("Deve retornar status code 200 para o metodo GET em /lotes")
    void testGetAll() {
        given()
                .when()
                .get("/lotes")
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "test", roles = { "Administrador", "Funcionario" })
    @DisplayName("Deve retornar status code 204 para o metodo PUT em /lotes/{id}")
    void testUpdate() {
        LoteRequestDTO dto = new LoteRequestDTO(1L, LocalDate.now(), LocalDate.now().plusMonths(6), "COD-TEST", 100);
        Long id = loteService.create(dto).getId();

        LoteRequestDTO novoLote = new LoteRequestDTO(1L, LocalDate.now(), LocalDate.now().plusMonths(6), "COD-TEST-NEW", 200);

        given()
                .contentType(ContentType.JSON)
                .body(novoLote)
                .when()
                .put("/lotes/{id}", id)
                .then()
                .statusCode(204);

        Lote lote = loteService.findById(id);
        assertEquals(lote.getEstoque(), novoLote.estoque());

        // Limpeza após o teste
        loteService.delete(loteService.findById(id).getId());
    }
}
