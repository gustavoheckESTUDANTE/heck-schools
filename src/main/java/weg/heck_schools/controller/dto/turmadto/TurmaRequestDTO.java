package weg.heck_schools.controller.dto.turmadto;

import java.util.List;

public record TurmaRequestDTO(
        String nome,
        long cursoId,
        long professorId,
        List<Long> listaAlunosIds
) {}
