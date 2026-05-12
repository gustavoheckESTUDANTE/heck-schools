package weg.heck_schools.service.services;

import org.springframework.stereotype.Service;
import weg.heck_schools.controller.dto.alunodto.AlunoRequestDTO;
import weg.heck_schools.controller.dto.alunodto.AlunoResponseDTO;
import weg.heck_schools.controller.dto.notadto.NotaResponseDTO;
import weg.heck_schools.domain.models.Aluno;
import weg.heck_schools.domain.models.Nota;
import weg.heck_schools.infra.repository.alunorepo.AlunoRepository;
import weg.heck_schools.infra.repository.notarepo.NotaRepository;
import weg.heck_schools.service.mapper.AlunoMapper;
import weg.heck_schools.service.mapper.NotaMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlunoService {

    public final AlunoRepository alunoRepository;
    public final AlunoMapper alunoMapper;
    public final NotaRepository notaRepository;
    public final NotaMapper notaMapper;

    public AlunoService(AlunoRepository alunoRepository,
                        AlunoMapper alunoMapper,
                        NotaRepository notaRepository,
                        NotaMapper notaMapper
    ) {
        this.alunoRepository = alunoRepository;
        this.alunoMapper = alunoMapper;
        this.notaRepository = notaRepository;
        this.notaMapper = notaMapper;
    }

    public AlunoResponseDTO cadastrarAluno (AlunoRequestDTO alunoRequestDTO) throws SQLException {
        Aluno aluno = alunoMapper.toEntity(alunoRequestDTO);
        if (aluno == null) {
            throw new RuntimeException("Erro ao tentar salvar aluno no banco de dados!");
        }
        alunoRepository.salvarAluno(aluno);
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

    public List<NotaResponseDTO> notasDoAluno (long id) throws SQLException {
        if (alunoRepository.buscarAluno(id).isEmpty()) {
            throw new RuntimeException("Não existe nenhum aluno com esse id para buscar notas!");
        }
        List<NotaResponseDTO> listaNotasDTO = new ArrayList<>();
        List<Nota> listaNotas = notaRepository.listarNotasPorIdAluno(id);
        for (Nota nota : listaNotas) {
            listaNotasDTO.add(notaMapper.toResponse(nota));
        }
        return listaNotasDTO;
    }
}
