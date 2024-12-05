package br.unitins.tp1.creatina.model;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;

@Entity
public class Usuario extends DefaultEntity {
    
    @Column(unique = true)
    private String username;
    private String senha;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "perfil_usuario", joinColumns = @JoinColumn(name = "id_usuario"))
    private List<Perfil> Perfil;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Perfil> getPerfil() {
        return Perfil;
    }

    public void setPerfil(List<Perfil> Perfil) {
        this.Perfil = Perfil;
    }
    

}
