package weg.heck_schools.controller.dto.cursodto;

import java.util.List;

public record CursoRequestDTO (
        String nome,
        String codigo,
        List<Long> listaProfessorIds
) {}
