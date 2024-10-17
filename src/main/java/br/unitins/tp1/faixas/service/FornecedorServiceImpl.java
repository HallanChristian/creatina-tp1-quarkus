package br.unitins.tp1.faixas.service;

import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.faixas.dto.EnderecoRequestDTO;
import br.unitins.tp1.faixas.dto.FornecedorRequestDTO;
import br.unitins.tp1.faixas.dto.TelefoneRequestDTO;
import br.unitins.tp1.faixas.model.Endereco;
import br.unitins.tp1.faixas.model.Fornecedor;
import br.unitins.tp1.faixas.model.Telefone;
import br.unitins.tp1.faixas.repository.FornecedorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class FornecedorServiceImpl implements FornecedorService {

    @Inject
    public FornecedorRepository fornecedorRepository;

    @Inject
    public MunicipioService municipioService;

    @Override
    public Fornecedor findById(Long id) {
        return fornecedorRepository.findById(id);
    }

    @Override
    public List<Fornecedor> findByNome(String nome) {
        return fornecedorRepository.findByNome(nome);
    }

    @Override
    public List<Fornecedor> findByCnpj(String cnpj) {
        return fornecedorRepository.findByCnpj(cnpj);
    }

    @Override
    public List<Fornecedor> findAll() {
        return fornecedorRepository.findAll().list();
    }

    @Override
    @Transactional
    public Fornecedor create(FornecedorRequestDTO dto) {
        Fornecedor fornecedor = new Fornecedor();

        fornecedor.setNome(dto.nome());
        fornecedor.setCnpj(dto.cnpj());
        fornecedor.setTelefones(getTelefones(dto));
        fornecedor.setEnderecos(getEnderecos(dto));

        fornecedorRepository.persist(fornecedor);

        return fornecedor;
    }

    @Override
    @Transactional
    public Fornecedor update(Long id, FornecedorRequestDTO dto) {
        Fornecedor fornecedor = fornecedorRepository.findById(id);

        fornecedor.setNome(dto.nome());
        fornecedor.setCnpj(dto.cnpj());
        updateTelefones(fornecedor, dto.telefones());
        updateEnderecos(fornecedor, dto.enderecos());

        return fornecedor;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        fornecedorRepository.deleteById(id);
    }

        private List<Telefone> getTelefones(FornecedorRequestDTO dto) {
        List<Telefone> telefones = new ArrayList<>();

        for (int i = 0; i < dto.telefones().size(); i++) {
            Telefone telefone = new Telefone();
            TelefoneRequestDTO telefoneRequestDTO = dto.telefones().get(i);
            telefone.setDdd(telefoneRequestDTO.ddd());
            telefone.setNumero(telefoneRequestDTO.numero());
            telefones.add(telefone);
        }

        return telefones;
    }

    private List<Endereco> getEnderecos(FornecedorRequestDTO dto) {
        List<Endereco> enderecos = new ArrayList<>();

        for (int i = 0; i < dto.enderecos().size(); i++) {
            Endereco endereco = new Endereco();
            EnderecoRequestDTO enderecoRequestDTO = dto.enderecos().get(i);
            endereco.setLogradouro(enderecoRequestDTO.logradouro());
            endereco.setNumero(enderecoRequestDTO.numero());
            endereco.setCep(enderecoRequestDTO.cep());
            endereco.setMunicipio(municipioService.findById(enderecoRequestDTO.idMunicipio()));
            enderecos.add(endereco);
        }

        return enderecos;
    }

    private void updateTelefones(Fornecedor fornecedor, List<TelefoneRequestDTO> novosTelefonesDTO) {
        List<Telefone> telefonesExistentes = fornecedor.getTelefones();
    
        telefonesExistentes.removeIf(telefone -> 
            novosTelefonesDTO.stream().noneMatch(dto -> 
                dto.ddd().equals(telefone.getDdd()) && dto.numero().equals(telefone.getNumero()))
        );
    
        // Adicionar ou atualizar telefones
        for (TelefoneRequestDTO dto : novosTelefonesDTO) {
            Telefone telefoneExistente = telefonesExistentes.stream()
                .filter(t -> t.getDdd().equals(dto.ddd()) && t.getNumero().equals(dto.numero()))
                .findFirst()
                .orElse(null);
    
            if (telefoneExistente == null) {
                Telefone novoTelefone = new Telefone();
                novoTelefone.setDdd(dto.ddd());
                novoTelefone.setNumero(dto.numero());
                telefonesExistentes.add(novoTelefone);
            } else {
                telefoneExistente.setDdd(dto.ddd());
                telefoneExistente.setNumero(dto.numero());
            }
        }
    }
    
    private void updateEnderecos(Fornecedor fornecedor, List<EnderecoRequestDTO> novosEnderecosDTO) {
        List<Endereco> enderecosExistentes = fornecedor.getEnderecos();
    
        enderecosExistentes.removeIf(endereco -> 
            novosEnderecosDTO.stream().noneMatch(dto -> 
                dto.logradouro().equals(endereco.getLogradouro()) && dto.numero().equals(endereco.getNumero()))
        );
    
        for (EnderecoRequestDTO dto : novosEnderecosDTO) {
            Endereco enderecoExistente = enderecosExistentes.stream()
                .filter(e -> e.getLogradouro().equals(dto.logradouro()) && e.getNumero().equals(dto.numero()))
                .findFirst()
                .orElse(null);
    
            if (enderecoExistente == null) {
                Endereco novoEndereco = new Endereco();
                novoEndereco.setLogradouro(dto.logradouro());
                novoEndereco.setNumero(dto.numero());
                novoEndereco.setCep(dto.cep());
                novoEndereco.setMunicipio(municipioService.findById(dto.idMunicipio()));
                enderecosExistentes.add(novoEndereco);
            } else {
                enderecoExistente.setLogradouro(dto.logradouro());
                enderecoExistente.setNumero(dto.numero());
                enderecoExistente.setCep(dto.cep());
                enderecoExistente.setMunicipio(municipioService.findById(dto.idMunicipio()));
            }
        }
    
}
}