package weg.heck_schools.service.mapper;

import org.springframework.stereotype.Component;
import weg.heck_schools.controller.dto.auladto.AulaRequestDTO;
import weg.heck_schools.controller.dto.auladto.AulaResponseDTO;
import weg.heck_schools.domain.models.Aula;

@Component
public class AulaMapper {

    public Aula toEntity (AulaRequestDTO aulaRequestDTO) {
        return new Aula(
                aulaRequestDTO.turmaId(),
                aulaRequestDTO.dataHora(),
                aulaRequestDTO.assunto()
        );
    }

    public AulaResponseDTO toResponse (Aula aula) {
        return new AulaResponseDTO(
                aula.getId(),
                aula.getTurmaId(),
                aula.getDataHora(),
                aula.getAssunto()
        );
    }
}
