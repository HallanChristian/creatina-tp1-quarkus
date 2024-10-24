package br.unitins.tp1.creatina.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Fornecedor extends DefaultEntity {
    @Column(length = 60, nullable = false)
    private String nome;

    @Column(length = 60, nullable = false)
    private String cnpj;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_fornecedor")
    private List<TelefoneFornecedor> telefones;

    // Métodos getters e setters

    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getCnpj() {
        return cnpj;
    }


    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }


    public List<TelefoneFornecedor> getTelefones() {
        return telefones;
    }
    

    public void setTelefones(List<TelefoneFornecedor> telefones) {
        this.telefones = telefones;

    }
}
