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

    public final CursoRepository cursoRepository;
    public final ProfessorRepository professorRepository;

    public TurmaMapper(CursoRepository cursoRepository, ProfessorRepository professorRepository) {
        this.cursoRepository = cursoRepository;
        this.professorRepository = professorRepository;
    }

    public Turma toEntity (TurmaRequestDTO turmaRequestDTO) {
        return new Turma(
              turmaRequestDTO.nome(),
              turmaRequestDTO.cursoId(),
              turmaRequestDTO.professorId()
        );
    }

    public TurmaResponseDTO toResponse (Turma turma, List<String> nomeAlunos) throws SQLException {
        if (cursoRepository.buscarCurso(turma.getId()).isEmpty() ||
                professorRepository.buscarProfessor(turma.getProfessorId()).isEmpty()) {

            throw new RuntimeException("Não foi encontrado nenhum curso ou professor com este id");
        }

        return new TurmaResponseDTO(
                turma.getId(),
                cursoRepository.buscarCurso(turma.getId()).get().getNome(),
                professorRepository.buscarProfessor(turma.getProfessorId()).get().getNome(),
                nomeAlunos
        );
    }
}
