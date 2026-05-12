package weg.heck_schools.service.mapper;

import org.springframework.stereotype.Component;
import weg.heck_schools.controller.dto.notadto.NotaRequestDTO;
import weg.heck_schools.controller.dto.notadto.NotaResponseDTO;
import weg.heck_schools.domain.models.Nota;
import weg.heck_schools.infra.repository.alunorepo.AlunoRepository;
import weg.heck_schools.infra.repository.aularepo.AulaRepository;

import java.sql.SQLException;

@Component
public class NotaMapper {

    public AlunoRepository alunoRepository;
    public AulaRepository aulaRepository;

    public NotaMapper(AlunoRepository alunoRepository, AulaRepository aulaRepository) {
        this.alunoRepository = alunoRepository;
        this.aulaRepository = aulaRepository;
    }

    public Nota toEntity (NotaRequestDTO notaRequestDTO) {
        return new Nota(
                notaRequestDTO.alunoId(),
                notaRequestDTO.aulaId(),
                notaRequestDTO.valor()
        );
    }

    public NotaResponseDTO toResponse (Nota nota) throws SQLException {
        return new NotaResponseDTO(
                nota.getId(),
                alunoRepository.buscarAluno(nota.getAlunoId()).getNome(),
                aulaRepository.buscarAula(nota.getAulaId()).getAssunto(),
                nota.getValor()
        );
    }
}
