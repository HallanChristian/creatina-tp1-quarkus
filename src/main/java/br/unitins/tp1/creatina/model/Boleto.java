package br.unitins.tp1.creatina.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;

@Entity
public class Boleto extends Pagamento {

    private String codigoBarras;
    private LocalDate dataValidade;

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

}
