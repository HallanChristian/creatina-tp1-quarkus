package br.unitins.tp1.creatina.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Lote extends DefaultEntity {

    @ManyToOne
    @JoinColumn(name = "id_faixa")
    private Faixa faixa;
    
    private LocalDate data;
    private String codigo;
    private Integer estoque;

    public Faixa getFaixa() {
        return faixa;
    }
    public void setFaixa(Faixa faixa) {
        this.faixa = faixa;
    }
    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public Integer getEstoque() {
        return estoque;
    }
    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }
    
}
