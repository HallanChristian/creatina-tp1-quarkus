package br.unitins.tp1.creatina.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Funcionario extends DefaultEntity {

    @OneToOne
    @JoinColumn(name = "id_pessoaFisica", unique = true)
    private PessoaFisica pessoaFisica;
    private String cargo;
    private BigDecimal salario;
    private LocalDate dataContratacao;

    public PessoaFisica getPessoaFisica() {
        return pessoaFisica;
    }
    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }
    public String getCargo() {
        return cargo;
    }
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    public BigDecimal getSalario() {
        return salario;
    }
    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }
    public LocalDate getDataContratacao() {
        return dataContratacao;
    }
    public void setDataContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
    }
    
}
