package weg.heck_schools.controller.dto.auladto;

import java.time.LocalDateTime;

public record AulaResponseDTO (
        long id,
        long turmaId,
        LocalDateTime dataHora,
        String assunto
) {}
