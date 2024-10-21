package br.unitins.tp1.creatina.model;

import jakarta.persistence.Entity;

@Entity
public class Creatina extends DefaultEntity {
    
    private String nome;
    private String marca;
    private double quantidadeEmGramas;
    private String tipo;
    private double preco;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getQuantidadeEmGramas() {
        return quantidadeEmGramas;
    }

    public void setQuantidadeEmGramas(double quantidadeEmGramas) {
        this.quantidadeEmGramas = quantidadeEmGramas;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
