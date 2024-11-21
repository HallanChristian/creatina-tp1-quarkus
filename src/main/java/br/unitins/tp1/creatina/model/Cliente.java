package br.unitins.tp1.creatina.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cliente extends DefaultEntity {

    @OneToOne
    @JoinColumn(name = "id_pessoaFisica", unique = true)
    private PessoaFisica pessoaFisica;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "lista_desejo", joinColumns = @JoinColumn(name = "id_cliente"), inverseJoinColumns = @JoinColumn(name = "id_creatina"))
    private List<Creatina> listaDesejos;
    
    // Métodos getters e setters
    
    public PessoaFisica getPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    public List<Creatina> getListaDesejos() {
        return listaDesejos;
    }

    public void setListaDesejos(List<Creatina> listaDesejos) {
        this.listaDesejos = listaDesejos;
    }
    
}
