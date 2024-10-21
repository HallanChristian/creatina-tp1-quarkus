package br.unitins.tp1.creatina.resource.faixa;

import java.util.List;

import br.unitins.tp1.creatina.model.Faixa;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/creatina")
public class FaixaResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Faixa> findAll() {

        return Faixa.listAll();
        
    }
    
}
