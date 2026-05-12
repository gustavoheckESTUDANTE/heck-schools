package weg.heck_schools.service.mapper;

import org.springframework.stereotype.Component;
import weg.heck_schools.controller.dto.professordto.ProfessorRequestDTO;
import weg.heck_schools.controller.dto.professordto.ProfessorResponseDTO;
import weg.heck_schools.domain.models.Professor;

@Component
public class ProfessorMapper {

    public Professor toEntity (ProfessorRequestDTO professorRequestDTO) {
        return new Professor(
                professorRequestDTO.nome(),
                professorRequestDTO.email(),
                professorRequestDTO.disciplina()
        );
    }

    public ProfessorResponseDTO toResponse (Professor professor) {
        return new ProfessorResponseDTO(
                professor.getId(),
                professor.getNome(),
                professor.getEmail(),
                professor.getDisciplina()
        );
    }
}
