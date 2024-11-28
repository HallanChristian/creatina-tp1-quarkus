package br.unitins.tp1.creatina.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class EnderecoEntrega extends DefaultEntity{

    @OneToOne
    @JoinColumn(name = "id_endereco", nullable = false)
    private Endereco enderecoEntrega;

    public Endereco getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(Endereco enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }
    
}
