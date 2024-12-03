package br.unitins.tp1.creatina.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;

@Entity
public class EstadoPedido extends DefaultEntity{

    private LocalDateTime dataSituacao;

    private SituacaoPedido situacaoPedido;

    public EstadoPedido() {}

    public EstadoPedido(SituacaoPedido situacaoPedido, LocalDateTime dataSituacao) {
        this.situacaoPedido = situacaoPedido;
        this.dataSituacao = dataSituacao;
    }

    public LocalDateTime getDataSituacao() {
        return dataSituacao;
    }

    public void setDataSituacao(LocalDateTime dataSituacao) {
        this.dataSituacao = dataSituacao;
    }

    public SituacaoPedido getSituacaoPedido() {
        return situacaoPedido;
    }

    public void setSituacaoPedido(SituacaoPedido situacaoPedido) {
        this.situacaoPedido = situacaoPedido;
    }
    
}
