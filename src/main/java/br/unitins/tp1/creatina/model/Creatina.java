package br.unitins.tp1.creatina.model;

import jakarta.persistence.Entity;

@Entity
public class Creatina extends DefaultEntity {
    
    private String nome;
    private String marca;
    private float quantidadeEmGramas;
    private String tipo;
    private float preco;

    // Métodos getters e setters

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

    public float getQuantidadeEmGramas() {
        return quantidadeEmGramas;
    }

    public void setQuantidadeEmGramas(float quantidadeEmGramas) {
        this.quantidadeEmGramas = quantidadeEmGramas;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }
}
