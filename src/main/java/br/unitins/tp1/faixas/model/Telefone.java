package br.unitins.tp1.faixas.model;

import jakarta.persistence.Entity;

@Entity
public class Telefone extends DefaultEntity {

    private String numero;
    private String ddd;

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
}
