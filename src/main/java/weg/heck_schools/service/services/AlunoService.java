package weg.heck_schools.service.services;

import org.springframework.stereotype.Service;
import weg.heck_schools.controller.dto.alunodto.AlunoRequestDTO;
import weg.heck_schools.controller.dto.alunodto.AlunoResponseDTO;
import weg.heck_schools.domain.models.Aluno;
import weg.heck_schools.infra.repository.alunorepo.AlunoRepository;
import weg.heck_schools.service.mapper.AlunoMapper;

import java.sql.SQLException;
import java.util.List;

@Service
public class AlunoService {

    public AlunoRepository alunoRepository;
    public AlunoMapper alunoMapper;

    public AlunoService(AlunoRepository alunoRepository, AlunoMapper alunoMapper) {
        this.alunoRepository = alunoRepository;
        this.alunoMapper = alunoMapper;
    }

    public AlunoResponseDTO cadastrarAluno (AlunoRequestDTO alunoRequestDTO) throws SQLException {
        Aluno aluno = alunoMapper.toEntity(alunoRequestDTO);
        alunoRepository.salvarAluno(aluno);
        if (aluno == null) {
            throw new RuntimeException("Erro ao tentar salvar aluno no banco de dados!");
        }
        return alunoMapper.toResponse(aluno);
    }

    public List<AlunoResponseDTO> listarAlunos () throws SQLException {
        List<Aluno> listaDeAlunos = alunoRepository.listarAlunos();
        List<AlunoResponseDTO> listaDeAlunosDto;

        if (listaDeAlunos.isEmpty()) {
            throw new RuntimeException("Não há nenhum aluno cadastrado!");
        }

        listaDeAlunosDto = listaDeAlunos.stream().map(
                alunoMapper::toResponse
        ).toList();

        return listaDeAlunosDto;
    }

    public AlunoResponseDTO buscarAluno (long id) throws SQLException {
        Aluno alunoBuscado = alunoRepository.buscarAluno(id)
                .orElseThrow(() -> new RuntimeException("Nenhum aluno com este id foi encontrado!"));

        return alunoMapper.toResponse(alunoBuscado);
    }

    public AlunoResponseDTO atualizarAluno (AlunoRequestDTO alunoRequestDTO, long id) throws SQLException {
        Aluno aluno = alunoMapper.toEntity(alunoRequestDTO);
        if (alunoRepository.buscarAluno(id).isEmpty()) {
            throw new RuntimeException("Não existe nenhum aluno com esse id para atualizar!");
        }
        aluno.setId(id);
        alunoRepository.atualizarAluno(aluno);
        return alunoMapper.toResponse(aluno);
    }

    public void deletarAluno (long id) throws SQLException {
        if (alunoRepository.buscarAluno(id).isEmpty()) {
            throw new RuntimeException("Não existe nenhum aluno com esse id para deletar!");
        }
        alunoRepository.deletarAluno(id);
    }
}
