package weg.heck_schools.service.mapper;

import org.springframework.stereotype.Component;
import weg.heck_schools.controller.dto.alunodto.AlunoRequestDTO;
import weg.heck_schools.controller.dto.alunodto.AlunoResponseDTO;
import weg.heck_schools.domain.models.Aluno;

@Component
public class AlunoMapper {

    public Aluno toEntity (AlunoRequestDTO alunoRequestDTO) {
        return new Aluno(
                alunoRequestDTO.nome(),
                alunoRequestDTO.email(),
                alunoRequestDTO.matricula(),
                alunoRequestDTO.dataNascimento()
        );
    }

    public AlunoResponseDTO toResponse (Aluno aluno) {
        return new AlunoResponseDTO(
                aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getMatricula(),
                aluno.getDataNascimento()
        );
    }
}
