package br.unitins.tp1.creatina.dto;

import java.time.LocalDate;

public record LoteRequestDTO(
    Long idCreatina, 
    LocalDate data, 
    String codigo,
    Integer estoque
) {}
