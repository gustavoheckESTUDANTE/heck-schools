package weg.heck_schools.controller.dto.turmadto;

import java.time.LocalDateTime;
import java.util.List;

public record TurmaResponseDTO(
        long id,
        String nomeCurso,
        String nomeProfessor,
        List<String> listaAlunosNome
) {}
