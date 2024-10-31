package br.unitins.tp1.faixas;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.creatina.dto.EnderecoRequestDTO;
import br.unitins.tp1.creatina.model.Endereco;
import br.unitins.tp1.creatina.service.endereco.EnderecoService;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class EnderecoResourceTest {

    @Inject
    public EnderecoService enderecoService;

    @Test
    public void testFindAll() {
        given()
                .when().get("/enderecos")
                .then()
                .statusCode(200);
    }

    @Test
    public void testCreateEndereco() {
        long idCliente = 1L;

        EnderecoRequestDTO dto = new EnderecoRequestDTO(
                "SP", "São Paulo", "12345-678", "Centro", "123"
        );

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/enderecos/" + idCliente)
                .then()
                .statusCode(201)
                .body("id", notNullValue(),
                        "cep", is("12345-678"),
                        "cidade", is("São Paulo"),
                        "estado", is("SP"),
                        "logradouro", is("Centro"),
                        "numero", is("123"));

    }

    @Test
    public void testUpdateEndereco() {
        long idEndereco = 1L;

        EnderecoRequestDTO dto = new EnderecoRequestDTO(
                "SP", "São Paulo", "12345-678", "Centro", "123"
        );

        Endereco endereco = enderecoService.create(dto);
        idEndereco = endereco.getId();

        EnderecoRequestDTO novoDto = new EnderecoRequestDTO(
                "RJ", "Rio de Janeiro", "98765-432", "Copacabana", "456"
        );

        given()
                .contentType(ContentType.JSON)
                .body(novoDto)
                .when()
                .put("/enderecos/" + idEndereco)
                .then()
                .statusCode(204);

        Endereco enderecoAtualizado = enderecoService.findById(idEndereco);
        assertEquals("98765-432", enderecoAtualizado.getCep());
        assertEquals("Rio de Janeiro", enderecoAtualizado.getCidade());
        assertEquals("RJ", enderecoAtualizado.getEstado());
        assertEquals("Copacabana", enderecoAtualizado.getLogradouro());
        assertEquals("456", enderecoAtualizado.getNumero());

        enderecoService.delete(enderecoAtualizado.getId());
    }

    @Test
    public void testDelete() {
        EnderecoRequestDTO dto = new EnderecoRequestDTO(
                "SPR", "São Paulo", "12345-678", "Centro", "123"
        );

        Endereco endereco = enderecoService.create(dto);
        long id = endereco.getId();

        given()
                .when()
                .delete("/enderecos/" + id)
                .then()
                .statusCode(204);

        Endereco enderecoDeletado = enderecoService.findById(id);
        assertNull(enderecoDeletado);
    }
    
    @Test
    public void testFindById() {
        EnderecoRequestDTO dto = new EnderecoRequestDTO(
                "SP", "São Paulo", "12345-678", "Centro", "123"
        );
    
        Endereco endereco = enderecoService.create(dto);
        Long id = endereco.getId();
    
        Endereco enderecoEncontrado = enderecoService.findById(id);
        assertEquals(endereco.getId(), enderecoEncontrado.getId());
    
        enderecoService.delete(id);
    }
    
    @Test
    public void testFindByCliente() {
        long idCliente = 1L;
    
        EnderecoRequestDTO dto1 = new EnderecoRequestDTO(
                "SP", "São Paulo", "12345-678", "Centro", "123"
        );
        EnderecoRequestDTO dto2 = new EnderecoRequestDTO(
                "RJ", "Rio de Janeiro", "98765-432", "Copacabana", "456"
        );
    
        enderecoService.create(dto1); 
        enderecoService.create(dto2);
    
        List<Endereco> enderecos = enderecoService.findByCliente(idCliente);
        assertEquals(1, enderecos.size()); 
    
        // Limpeza após o teste
        for (Endereco endereco : enderecos) {
            enderecoService.delete(endereco.getId());
        }
    }
    
    @Test
    public void testDeleteByCliente() {
        long idCliente = 1L;
    
        EnderecoRequestDTO dto1 = new EnderecoRequestDTO(
                "SP", "São Paulo", "12345-678", "Centro", "123"
        );
        EnderecoRequestDTO dto2 = new EnderecoRequestDTO(
                "RJ", "Rio de Janeiro", "98765-432", "Copacabana", "456"
        );
    
        enderecoService.create(dto1); 
        enderecoService.create(dto2); 
    
        enderecoService.deleteByCliente(idCliente); 
    
        List<Endereco> enderecos = enderecoService.findByCliente(idCliente);
        assertEquals(0, enderecos.size()); 

    }
    
}
