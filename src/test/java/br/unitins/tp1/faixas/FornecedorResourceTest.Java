package br.unitins.tp1.faixas;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.unitins.tp1.creatina.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.creatina.dto.fornecedor.FornecedorRequestDTO;
import br.unitins.tp1.creatina.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.creatina.dto.usuario.UsuarioRequestDTO;
import br.unitins.tp1.creatina.model.Fornecedor;
import br.unitins.tp1.creatina.service.fornecedor.FornecedorService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class FornecedorResourceTest {

        @Inject
        public FornecedorService fornecedorService;

        @Test
        @TestSecurity(user = "test", roles = { "Funcionario", "Administrador" })
        void testCreate() {
                List<TelefoneRequestDTO> telefones = List.of(new TelefoneRequestDTO("63", "912345678"),
                                new TelefoneRequestDTO("85", "912355678"));

                List<EnderecoRequestDTO> enderecos = List
                                .of(new EnderecoRequestDTO(1L, "Teste", "Teste bairro", "Teste numero",
                                                "Teste comlemento", "121212"));

                FornecedorRequestDTO dto = new FornecedorRequestDTO("Empresa Teste", "07126830000109",
                                "empresa@gmail.com", telefones, enderecos,
                                new UsuarioRequestDTO("empresa.teste", "123456", "email@gmail.com"));

                given()
                                .contentType(ContentType.JSON)
                                .body(dto)
                                .when()
                                .post("/fornecedors")
                                .then()
                                .statusCode(201)
                                .body("id", notNullValue(), "nome", is("Empresa Teste"));

                fornecedorService.delete(fornecedorService.findByNome("Empresa Teste").getFirst().getId());
        }

        @Test
        @TestSecurity(user = "test", roles = { "Funcionario", "Administrador" })
        void testDelete() {
                List<TelefoneRequestDTO> telefones = List.of(new TelefoneRequestDTO("63", "912345678"),
                                new TelefoneRequestDTO("85", "912355678"));

                List<EnderecoRequestDTO> enderecos = List
                                .of(new EnderecoRequestDTO(1L, "Teste", "Teste bairro", "Teste numero",
                                                "Teste comlemento", "121212"));

                FornecedorRequestDTO dto = new FornecedorRequestDTO("Empresa Teste", "07126830000109",
                                "empresa@gmail.com", telefones, enderecos,
                                new UsuarioRequestDTO("empresa.teste", "123456", "email@gmail.com"));

                Long id = fornecedorService.create(dto).getId();

                given()
                                .when()
                                .delete("/fornecedors/{id}", id)
                                .then()
                                .statusCode(204);

                assertThrows(EntidadeNotFoundException.class, () -> fornecedorService.findById(id));
        }

        @Test
        @TestSecurity(user = "test", roles = { "Funcionario", "Administrador" })
        void testFindAll() {
                given()
                                .when()
                                .get("/fornecedors")
                                .then()
                                .statusCode(200);
        }

        @Test
        @TestSecurity(user = "test", roles = { "Funcionario", "Administrador" })
        void testFindById() {
                Long idExistente = 2L;
                given()
                                .when()
                                .get("/fornecedors/{id}", idExistente)
                                .then()
                                .statusCode(200)
                                .body("nome", is("Growth Suplementos"));
        }

        @Test
        @TestSecurity(user = "test", roles = { "Funcionario", "Administrador" })
        void testFindByNome() {
                given()
                                .when()
                                .queryParam("nome", "Growth")
                                .get("/fornecedors/search/nome")
                                .then()
                                .statusCode(200)
                                .body("[0].id", is(2), "[0].nome", is("Growth Suplementos"));
        }

        @Test
        @TestSecurity(user = "test", roles = { "Funcionario", "Administrador" })
        void testUpdate() {
                List<TelefoneRequestDTO> telefones = List.of(new TelefoneRequestDTO("63", "912345678"),
                                new TelefoneRequestDTO("85", "912355678"));

                List<EnderecoRequestDTO> enderecos = List
                                .of(new EnderecoRequestDTO(1L, "Teste", "Teste bairro", "Teste numero",
                                                "Teste comlemento", "121212"));

                FornecedorRequestDTO dto = new FornecedorRequestDTO("Empresa Teste", "07126830000109",
                                "empresa@gmail.com", telefones, enderecos,
                                new UsuarioRequestDTO("empresa.teste", "123456", "email@gmail.com"));

                Long id = fornecedorService.create(dto).getId();

                FornecedorUpdateRequestDTO novoFornecedor = new FornecedorUpdateRequestDTO("Empresa Teste Atualizada ",
                                "40954681000141",
                                "empresaatualizada@gmail.com");
                given()
                                .contentType(ContentType.JSON)
                                .body(novoFornecedor)
                                .when()
                                .patch("/fornecedors/{id}", id)
                                .then()
                                .statusCode(204);

                Fornecedor fornecedor = fornecedorService.findById(id);
                assertEquals(fornecedor.getPessoaJuridica().getNome(),
                                novoFornecedor.nome());
                assertEquals(fornecedor.getPessoaJuridica().getEmail(),
                                novoFornecedor.email());

                fornecedorService.delete(fornecedorService.findById(id).getId());
        }

}