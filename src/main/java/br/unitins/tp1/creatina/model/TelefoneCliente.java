package br.unitins.tp1.creatina.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class TelefoneCliente extends DefaultEntity {

    private String numero;
    private String ddd;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
