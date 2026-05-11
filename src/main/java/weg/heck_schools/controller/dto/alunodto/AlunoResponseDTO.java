package weg.heck_schools.controller.dto.alunodto;

import java.time.LocalDate;

public record AlunoResponseDTO(
        long id,
        String nome,
        String email,
        String matricula,
        LocalDate dataNascimento
) {}
