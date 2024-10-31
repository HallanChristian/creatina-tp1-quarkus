package br.unitins.tp1.faixas;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.creatina.dto.FornecedorRequestDTO;
import br.unitins.tp1.creatina.dto.TelefoneFornecedorRequestDTO;
import br.unitins.tp1.creatina.model.Fornecedor;
import br.unitins.tp1.creatina.service.fornecedor.FornecedorService;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class FornecedorResourceTest {
    
    @Inject
    public FornecedorService fornecedorService;
    
    @Test
    public void testFindByNome() {
        given()
            .when().pathParam("nome", "Fornecedor Exemplo")
            .get("/fornecedores/search/{nome}")
            .then().statusCode(200)
            .body("nome", hasItem(is("Fornecedor Exemplo")));
    }
    
    @Test
    public void testFindByCnpj() {
        given()
            .when().pathParam("cnpj", "12345678000195")
            .get("/fornecedores/search/cnpj/{cnpj}")
            .then().statusCode(200)
            .body("cnpj", hasItem(is("12345678000195")));
    }
    
    @Test
    public void testFindAll() {
        given()
            .when().get("/fornecedores")
            .then().statusCode(200);
    }
    
    @Test
    public void testCreate() {
        TelefoneFornecedorRequestDTO telefoneDTO = new TelefoneFornecedorRequestDTO("63", "3904-1538");
        FornecedorRequestDTO dto = new FornecedorRequestDTO(
            "Fornecedor Exemplo",
            "12345678000195",
            List.of(telefoneDTO)
        );

        Integer idInteger = given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when()
            .post("/fornecedores")
            .then()
            .statusCode(201)
            .extract().path("id");

        Long id = idInteger.longValue();

        fornecedorService.delete(id);
    }

    @Test
    public void testAddTelefone() {
        TelefoneFornecedorRequestDTO telefoneDTO = new TelefoneFornecedorRequestDTO("63", "3904-1538");
        FornecedorRequestDTO dto = new FornecedorRequestDTO(
            "Fornecedor Exemplo",
            "12345678000195",
            List.of()
        );
        
        Long fornecedorId = fornecedorService.create(dto).getId();

        given()
            .contentType(ContentType.JSON)
            .body(telefoneDTO)
            .when()
            .post("/fornecedores/" + fornecedorId + "/telefones")
            .then()
            .statusCode(201);
            
        Fornecedor fornecedor = fornecedorService.findById(fornecedorId);
        assertEquals(1, fornecedor.getTelefones().size());
        assertEquals("3904-1538", fornecedor.getTelefones().get(0).getNumero());
        
        fornecedorService.delete(fornecedorId);
    }

    @Test
    public void testFindById() {
        given()
            .when().get("/fornecedores/1")
            .then().statusCode(200)
            .body("id", is(1));
    }
    
    @Test
    public void testUpdate() {
        TelefoneFornecedorRequestDTO telefoneDTO = new TelefoneFornecedorRequestDTO("63", "3904-1538");
        FornecedorRequestDTO dto = new FornecedorRequestDTO(
            "Fornecedor Exemplo",
            "12345678000195",
            List.of(telefoneDTO)
        );

        Long id = fornecedorService.create(dto).getId();
        
        FornecedorRequestDTO novoDto = new FornecedorRequestDTO(
            "Fornecedor Atualizado",
            "12345678000196",
            List.of(new TelefoneFornecedorRequestDTO("63", "3904-1234"))
        );
                
        given()
            .contentType(ContentType.JSON)
            .body(novoDto)
            .when()
            .put("/fornecedores/" + id)
            .then()
            .statusCode(204);

        Fornecedor fornecedorAtualizado = fornecedorService.findById(id);
        assertEquals("Fornecedor Atualizado", fornecedorAtualizado.getNome());
        assertEquals("12345678000196", fornecedorAtualizado.getCnpj());
        
        fornecedorService.delete(id);
    }
    
    @Test
    public void testDelete() {
        TelefoneFornecedorRequestDTO telefoneDTO = new TelefoneFornecedorRequestDTO("63", "3904-1538");
        FornecedorRequestDTO dto = new FornecedorRequestDTO(
            "Fornecedor Exemplo",
            "12345678000195",
            List.of(telefoneDTO)
        );

        Long id = fornecedorService.create(dto).getId();

        given()
            .when()
            .delete("/fornecedores/" + id)
            .then().statusCode(204);

        Fornecedor fornecedor = fornecedorService.findById(id);
        assertNull(fornecedor);
    }
}

