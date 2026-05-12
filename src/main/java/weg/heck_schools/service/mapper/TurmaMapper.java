package weg.heck_schools.service.mapper;

import org.springframework.stereotype.Component;
import weg.heck_schools.controller.dto.turmadto.TurmaRequestDTO;
import weg.heck_schools.controller.dto.turmadto.TurmaResponseDTO;
import weg.heck_schools.domain.models.Turma;
import weg.heck_schools.infra.repository.cursorepo.CursoRepository;
import weg.heck_schools.infra.repository.professorrepo.ProfessorRepository;

import java.sql.SQLException;
import java.util.List;

@Component
public class TurmaMapper {

    public CursoRepository cursoRepository;
    public ProfessorRepository professorRepository;

    public Turma toEntity (TurmaRequestDTO turmaRequestDTO) {
        return new Turma(
              turmaRequestDTO.nome(),
              turmaRequestDTO.cursoId(),
              turmaRequestDTO.professorId()
        );
    }

    public TurmaResponseDTO toResponse (Turma turma, List<String> nomeAlunos) throws SQLException {
        return new TurmaResponseDTO(
                turma.getId(),
                cursoRepository.buscarCurso(turma.getCursoId()).getNome(),
                professorRepository.buscarProfessor(turma.getProfessorId()).getNome(),
                nomeAlunos
        );
    }
}
