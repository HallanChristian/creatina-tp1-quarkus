package br.unitins.tp1.creatina.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class TelefoneFornecedor extends DefaultEntity {

    private String numero;
    private String ddd;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private  Fornecedor fornecedor;

    // Métodos getters e setters

    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public String getDdd() {
        return ddd;
    }
    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

}
