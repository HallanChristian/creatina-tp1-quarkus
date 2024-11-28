package br.unitins.tp1.creatina.dto.avaliacao;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

public record AvaliacaoRequestDTO(
    @NotNull(message = "O campo ID da Creatina é obrigatório") Long idCreatina,
    @Size(max = 255, message = "O comentário deve ter no máximo 255 caracteres") String comentario,
    @NotNull(message = "O campo estrelas é obrigatório") 
    @Min(value = 1, message = "A avaliação deve ter no mínimo 1 estrela") 
    @Max(value = 5, message = "A avaliação pode ter no máximo 5 estrelas") Integer estrelas,
    @JsonFormat(pattern = "yyyy-MM-dd") LocalDate dataAvaliacao
) {
}
