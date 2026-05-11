package weg.heck_schools.controller.dto.cursodto;

import java.util.List;

public record CursoResponseDTO(
        String nome,
        String codigo,
        List<String> listaProfessorNomes
) {}
