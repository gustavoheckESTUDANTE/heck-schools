package weg.heck_schools.controller.dto.auladto;

import java.time.LocalDateTime;

public record AulaRequestDTO (
        long turmaId,
        LocalDateTime dataHora,
        String assunto
) {}
