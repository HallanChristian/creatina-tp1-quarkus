package br.unitins.tp1.creatina.model;

import jakarta.persistence.Entity;

@Entity
public class PessoaFisica extends Pessoa {
    
    private String cpf;
    private Sexo sexo;
    private String nomeImagem;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getNomeImagem() {
        return nomeImagem;
    }

    public void setNomeImagem(String nomeImagem) {
        this.nomeImagem = nomeImagem;
    }

}
