package br.unitins.tp1.creatina.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Lote extends DefaultEntity {

    @ManyToOne
    @JoinColumn(name = "id_creatina", nullable = false)
    private Creatina creatina;
    
    private LocalDate dataValidade;
    private LocalDate dataFabricacao;

    @Column(nullable = false, unique = true)
    private String codigo;
    
    @Column(nullable = false)
    private Integer estoque;

    public Creatina getCreatina() {
        return creatina;
    }
    
    public void setCreatina(Creatina creatina) {
        this.creatina = creatina;
    }
    
    public LocalDate getDataValidade() {
        return dataValidade;
    }
    
    public void setDataValidade(LocalDate datavalidade) {
        this.dataValidade = datavalidade;
    }
    
    public LocalDate getDataFabricacao() {
        return dataFabricacao;
    }

    public void setDataFabricacao(LocalDate dataFabricacao) {
        this.dataFabricacao = dataFabricacao;
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