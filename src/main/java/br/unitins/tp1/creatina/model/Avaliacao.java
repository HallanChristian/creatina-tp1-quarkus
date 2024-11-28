package br.unitins.tp1.creatina.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class Avaliacao extends DefaultEntity {

    @NotNull(message = "O campo estrelas deve ser informado")
    @Min(value = 1, message = "A avaliação deve ser no mínimo 1 estrela")
    @Max(value = 5, message = "A avaliação deve ser no máximo 5 estrelas")
    private Integer estrelas;

    private String comentario;

    @NotNull(message = "A data da avaliação deve ser informada")
    private LocalDateTime dataAvaliacao;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_creatina", nullable = false)
    private Creatina creatina;

    public Integer getEstrelas() {
        return estrelas;
    }

    public void setEstrelas(Integer estrelas) {
        this.estrelas = estrelas;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(LocalDateTime dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Creatina getCreatina() {
        return creatina;
    }

    public void setCreatina(Creatina creatina) {
        this.creatina = creatina;
    }
    
}
