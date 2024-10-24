package br.unitins.tp1.creatina.service.telefone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.creatina.dto.TelefoneFornecedorRequestDTO;
import br.unitins.tp1.creatina.model.TelefoneFornecedor;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@QuarkusTest
public class TelefoneFornecedorServiceImplTest {

    @Inject
    public TelefoneFornecedorService telefoneFornecedorService;

    private TelefoneFornecedorRequestDTO telefoneDTO;

    @BeforeEach
    public void setup() {
        telefoneDTO = new TelefoneFornecedorRequestDTO("63", "99999-8888");
    }

    @Test
    @Transactional
    public void testCreate() {
        TelefoneFornecedor telefone = telefoneFornecedorService.create(telefoneDTO);
        assertNotNull(telefone.getId());
        assertEquals("63", telefone.getDdd());
        assertEquals("99999-8888", telefone.getNumero());

        // Limpar os dados
        telefoneFornecedorService.delete(telefone.getId());
    }

    @Test
    @Transactional
    public void testFindById() {
        TelefoneFornecedor telefone = telefoneFornecedorService.create(telefoneDTO);
        TelefoneFornecedor telefoneEncontrado = telefoneFornecedorService.findById(telefone.getId());
        assertNotNull(telefoneEncontrado);
        assertEquals(telefone.getId(), telefoneEncontrado.getId());

        // Limpar os dados
        telefoneFornecedorService.delete(telefone.getId());
    }

    @Test
    @Transactional
    public void testFindAll() {
        TelefoneFornecedor telefone1 = telefoneFornecedorService.create(telefoneDTO);
        TelefoneFornecedor telefone2 = telefoneFornecedorService.create(new TelefoneFornecedorRequestDTO("64", "88888-7777"));

        List<TelefoneFornecedor> telefones = telefoneFornecedorService.findAll();

        assertNotNull(telefones);
        assertEquals(2, telefones.size());

        // Limpar os dados
        telefoneFornecedorService.delete(telefone1.getId());
        telefoneFornecedorService.delete(telefone2.getId());
    }

    @Test
    @Transactional
    public void testUpdate() {
        TelefoneFornecedor telefone = telefoneFornecedorService.create(telefoneDTO);

        // Atualiza o telefone
        TelefoneFornecedorRequestDTO telefoneAtualizadoDTO = new TelefoneFornecedorRequestDTO("65", "77777-6666");
        TelefoneFornecedor telefoneAtualizado = telefoneFornecedorService.update(telefone.getId(), telefoneAtualizadoDTO);

        assertEquals("65", telefoneAtualizado.getDdd());
        assertEquals("77777-6666", telefoneAtualizado.getNumero());

        // Limpar os dados
        telefoneFornecedorService.delete(telefone.getId());
    }

    @Test
    @Transactional
    public void testDelete() {
        TelefoneFornecedor telefone = telefoneFornecedorService.create(telefoneDTO);

        telefoneFornecedorService.delete(telefone.getId());

        assertThrows(EntityNotFoundException.class, () -> {
            telefoneFornecedorService.findById(telefone.getId());
        });
    }
}
