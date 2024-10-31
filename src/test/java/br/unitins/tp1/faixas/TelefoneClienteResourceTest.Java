package br.unitins.tp1.faixas;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.creatina.dto.TelefoneClienteRequestDTO;
import br.unitins.tp1.creatina.model.TelefoneCliente;
import br.unitins.tp1.creatina.service.telefone.TelefoneClienteService;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class TelefoneClienteResourceTest {

    @Inject
    public TelefoneClienteService telefoneClienteService;

    @Test
    public void testFindAll() {
        long idCliente = 1L; 

        TelefoneClienteRequestDTO dto = new TelefoneClienteRequestDTO(
                "11", 
                "987654321"
        );

        TelefoneCliente telefoneClienteCriado = telefoneClienteService.create(idCliente, dto);

        given()
                .when().get("/telefoneclientes")
                .then().statusCode(200)
                .body("size()", is(greaterThan(0))); 

        TelefoneCliente telefoneCliente = telefoneClienteService.findById(telefoneClienteCriado.getId());
        if (telefoneCliente != null) {
            telefoneClienteService.delete(telefoneCliente.getId());
        }
    }

    @Test
    public void testCreate() {
        long idCliente = 1L;

        TelefoneClienteRequestDTO dto = new TelefoneClienteRequestDTO(
                "11", 
                "987654321"
        );

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/telefoneclientes/" + idCliente)
                .then()
                .statusCode(201)
                .body("id", notNullValue(), 
                        "ddd", is("11"),
                        "numero", is("987654321"));

        TelefoneCliente telefoneCliente = telefoneClienteService.findById((long) 1);
        if (telefoneCliente != null) {
            telefoneClienteService.delete(telefoneCliente.getId());
        }
    }

    @Test
    public void testUpdateTelefoneCliente() {
        long idCliente = 1L;
    
        TelefoneClienteRequestDTO dto = new TelefoneClienteRequestDTO(
                "11",
                "987654321"
        );
    
        TelefoneCliente telefoneCliente = telefoneClienteService.create(idCliente, dto);
    
        TelefoneClienteRequestDTO novoDto = new TelefoneClienteRequestDTO(
                "21", 
                "123456789"
        );
    
        given()
                .contentType(ContentType.JSON)
                .body(novoDto)
                .when()
                .put("/telefoneclientes/" + telefoneCliente.getId())
                .then()
                .statusCode(204);
    
        TelefoneCliente telefoneAtualizado = telefoneClienteService.findById(telefoneCliente.getId());
    
        assertEquals("21", telefoneAtualizado.getDdd());
        assertEquals("123456789", telefoneAtualizado.getNumero());
    
        telefoneClienteService.delete(telefoneAtualizado.getId());
    }    

    @Test
    public void testDelete() {
        long idCliente = 1L; 

        TelefoneClienteRequestDTO dto = new TelefoneClienteRequestDTO(
                "11",
                "912345678"
        );

        TelefoneCliente telefoneCliente = telefoneClienteService.create(idCliente, dto);
        long id = telefoneCliente.getId();

        given()
                .when()
                .delete("/telefoneclientes/" + id)
                .then()
                .statusCode(204);

        TelefoneCliente telefoneClienteAfterDelete = telefoneClienteService.findById(id);
        assertNull(telefoneClienteAfterDelete, "O TelefoneCliente deve ser nulo após a exclusão.");
    }

    @Test
    public void testFindByCliente() {
        long idCliente = 1L;

        telefoneClienteService.deleteAllByClienteId(idCliente); 

        TelefoneClienteRequestDTO dto1 = new TelefoneClienteRequestDTO("11", "912345678");
        TelefoneClienteRequestDTO dto2 = new TelefoneClienteRequestDTO("21", "987654321");

        TelefoneCliente telefoneCliente1 = telefoneClienteService.create(idCliente, dto1);
        TelefoneCliente telefoneCliente2 = telefoneClienteService.create(idCliente, dto2);

        given()
                .when()
                .get("/telefoneclientes/search/" + idCliente)
                .then()
                .statusCode(200)
                .body("size()", is(2),
                    "[0].ddd", is("11"),
                    "[0].numero", is("912345678"),
                    "[1].ddd", is("21"),
                    "[1].numero", is("987654321"));

        telefoneClienteService.delete(telefoneCliente1.getId());
        telefoneClienteService.delete(telefoneCliente2.getId());
    }

}
