package br.unitins.tp1.creatina.service.telefone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.creatina.dto.TelefoneClienteRequestDTO;
import br.unitins.tp1.creatina.model.TelefoneCliente;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@QuarkusTest
public class TelefoneClienteResourceTest {

    @Inject
    public TelefoneClienteService telefoneClienteService;

    private TelefoneClienteRequestDTO telefoneDTO;

    @BeforeEach
    public void setup() {
        telefoneDTO = new TelefoneClienteRequestDTO("63", "99999-8888");
    }

    @Test
    @Transactional
    public void testCreate() {
        TelefoneCliente telefone = telefoneClienteService.create(telefoneDTO);
        assertNotNull(telefone.getId());
        assertEquals("63", telefone.getDdd());
        assertEquals("99999-8888", telefone.getNumero());

        // Limpar os dados
        telefoneClienteService.delete(telefone.getId());
    }

    @Test
    @Transactional
    public void testFindById() {
        TelefoneCliente telefone = telefoneClienteService.create(telefoneDTO);
        TelefoneCliente telefoneEncontrado = telefoneClienteService.findById(telefone.getId());
        assertNotNull(telefoneEncontrado);
        assertEquals(telefone.getId(), telefoneEncontrado.getId());

        // Limpar os dados
        telefoneClienteService.delete(telefone.getId());
    }

    @Test
    @Transactional
    public void testFindAll() {
        TelefoneCliente telefone1 = telefoneClienteService.create(telefoneDTO);
        TelefoneCliente telefone2 = telefoneClienteService.create(new TelefoneClienteRequestDTO("64", "88888-7777"));

        List<TelefoneCliente> telefones = telefoneClienteService.findAll();

        assertNotNull(telefones);
        assertEquals(2, telefones.size());

        // Limpar os dados
        telefoneClienteService.delete(telefone1.getId());
        telefoneClienteService.delete(telefone2.getId());
    }

    @Test
    @Transactional
    public void testUpdate() {
        TelefoneCliente telefone = telefoneClienteService.create(telefoneDTO);

        // Atualiza o telefone
        TelefoneClienteRequestDTO telefoneAtualizadoDTO = new TelefoneClienteRequestDTO("65", "77777-6666");
        TelefoneCliente telefoneAtualizado = telefoneClienteService.update(telefone.getId(), telefoneAtualizadoDTO);

        assertEquals("65", telefoneAtualizado.getDdd());
        assertEquals("77777-6666", telefoneAtualizado.getNumero());

        // Limpar os dados
        telefoneClienteService.delete(telefone.getId());
    }

    @Test
    @Transactional
    public void testDelete() {
        TelefoneCliente telefone = telefoneClienteService.create(telefoneDTO);

        telefoneClienteService.delete(telefone.getId());

        assertThrows(EntityNotFoundException.class, () -> {
            telefoneClienteService.findById(telefone.getId());
        });
    }
}
