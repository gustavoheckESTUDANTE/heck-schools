package weg.heck_schools.controller.dto.professordto;

public record ProfessorResponseDTO (
        long id,
        String nome,
        String email,
        String disciplina
) {}
