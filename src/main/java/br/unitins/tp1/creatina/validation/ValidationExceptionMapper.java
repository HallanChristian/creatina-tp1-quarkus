package br.unitins.tp1.creatina.validation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {
    @Override
    public Response toResponse(ValidationException exception) {
        ValidationError error = new ValidationError("400", "Erro de Validação");
        error.addFieldError(exception.getFildName(), exception.getMessage());
        return Response.status(400).entity(error).build();
    }

}