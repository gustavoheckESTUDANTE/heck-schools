package weg.heck_schools.controller.dto.notadto;

public record NotaResponseDTO (
        long id,
        String alunoNome,
        String aulaAssunto,
        double valor
) {}
