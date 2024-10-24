package br.unitins.tp1.creatina.service.fornecedor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.creatina.dto.FornecedorRequestDTO;
import br.unitins.tp1.creatina.dto.TelefoneFornecedorRequestDTO;
import br.unitins.tp1.creatina.model.Fornecedor;
import br.unitins.tp1.creatina.service.telefone.TelefoneFornecedorServiceImpl;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@QuarkusTest
public class FornecedorResourceTest {

    @Inject
    public FornecedorService fornecedorService;

    @Inject
    public TelefoneFornecedorServiceImpl telefoneFornecedorService;

    private FornecedorRequestDTO fornecedorDTO;

    @BeforeEach
    public void setup() {
        TelefoneFornecedorRequestDTO telefoneDTO = new TelefoneFornecedorRequestDTO("(63) 99999-8888");

        fornecedorDTO = new FornecedorRequestDTO("Fornecedor Exemplo", "12345678000195", List.of(telefoneDTO));
    }

    @Test
    @Transactional
    public void testCreate() {
        Fornecedor fornecedor = fornecedorService.create(fornecedorDTO);
        assertNotNull(fornecedor.getId());
        assertEquals("Fornecedor Exemplo", fornecedor.getNome());
        assertEquals("12345678000195", fornecedor.getCnpj());
        assertEquals(1, fornecedor.getTelefones().size());
        assertEquals("(63) 99999-8888", fornecedor.getTelefones().get(0).getNumero());

        // Limpar os dados
        fornecedorService.delete(fornecedor.getId());
    }

    @Test
    @Transactional
    public void testFindById() {
        Fornecedor fornecedor = fornecedorService.create(fornecedorDTO);
        Fornecedor fornecedorEncontrado = fornecedorService.findById(fornecedor.getId());
        assertNotNull(fornecedorEncontrado);
        assertEquals(fornecedor.getId(), fornecedorEncontrado.getId());

        // Limpar os dados
        fornecedorService.delete(fornecedor.getId());
    }

    @Test
    @Transactional
    public void testFindByNome() {
        Fornecedor fornecedor = fornecedorService.create(fornecedorDTO);
        List<Fornecedor> fornecedores = fornecedorService.findByNome("Fornecedor Exemplo");

        assertNotNull(fornecedores);
        assertEquals(1, fornecedores.size());
        assertEquals("Fornecedor Exemplo", fornecedores.get(0).getNome());

        // Limpar os dados
        fornecedorService.delete(fornecedor.getId());
    }

    @Test
    @Transactional
    public void testFindByCnpj() {
        Fornecedor fornecedor = fornecedorService.create(fornecedorDTO);
        List<Fornecedor> fornecedores = fornecedorService.findByCnpj("12345678000195");

        assertNotNull(fornecedores);
        assertEquals(1, fornecedores.size());
        assertEquals("12345678000195", fornecedores.get(0).getCnpj());

        // Limpar os dados
        fornecedorService.delete(fornecedor.getId());
    }

    @Test
    @Transactional
    public void testAddTelefone() {
        Fornecedor fornecedor = fornecedorService.create(fornecedorDTO);

        // Adiciona um novo telefone
        TelefoneFornecedorRequestDTO novoTelefoneDTO = new TelefoneFornecedorRequestDTO("(63) 88888-7777");
        fornecedorService.addTelefone(fornecedor.getId(), novoTelefoneDTO);

        Fornecedor fornecedorAtualizado = fornecedorService.findById(fornecedor.getId());

        assertEquals(2, fornecedorAtualizado.getTelefones().size());
        assertEquals("(63) 88888-7777", fornecedorAtualizado.getTelefones().get(1).getNumero());

        // Limpar os dados
        fornecedorService.delete(fornecedor.getId());
    }

    @Test
    @Transactional
    public void testUpdate() {
        Fornecedor fornecedor = fornecedorService.create(fornecedorDTO);

        // Atualiza os dados do fornecedor
        FornecedorRequestDTO fornecedorAtualizadoDTO = new FornecedorRequestDTO("Fornecedor Atualizado", "98765432000123", fornecedorDTO.telefones());
        Fornecedor fornecedorAtualizado = fornecedorService.update(fornecedor.getId(), fornecedorAtualizadoDTO);

        assertEquals("Fornecedor Atualizado", fornecedorAtualizado.getNome());
        assertEquals("98765432000123", fornecedorAtualizado.getCnpj());

        // Limpar os dados
        fornecedorService.delete(fornecedor.getId());
    }

    @Test
    @Transactional
    public void testDelete() {
        Fornecedor fornecedor = fornecedorService.create(fornecedorDTO);

        fornecedorService.delete(fornecedor.getId());

        assertThrows(EntityNotFoundException.class, () -> {
            fornecedorService.findById(fornecedor.getId());
        });
    }

    @Test
    @Transactional
    public void testFindAll() {
        Fornecedor fornecedor1 = fornecedorService.create(fornecedorDTO);
        Fornecedor fornecedor2 = fornecedorService.create(new FornecedorRequestDTO("Fornecedor 2", "98765432000123", fornecedorDTO.telefones()));

        List<Fornecedor> fornecedores = fornecedorService.findAll();

        assertEquals(2, fornecedores.size());

        // Limpar os dados
        fornecedorService.delete(fornecedor1.getId());
        fornecedorService.delete(fornecedor2.getId());
    }
}
