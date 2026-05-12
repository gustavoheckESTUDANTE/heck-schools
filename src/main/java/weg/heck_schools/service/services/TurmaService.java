package weg.heck_schools.service.services;

import org.springframework.stereotype.Service;
import weg.heck_schools.controller.dto.turmadto.TurmaRequestDTO;
import weg.heck_schools.controller.dto.turmadto.TurmaResponseDTO;
import weg.heck_schools.controller.dto.turmadto.TurmaRequestDTO;
import weg.heck_schools.controller.dto.turmadto.TurmaResponseDTO;
import weg.heck_schools.domain.models.Turma;
import weg.heck_schools.domain.models.Aluno;
import weg.heck_schools.domain.models.Turma;
import weg.heck_schools.infra.repository.turmarepo.TurmaRepository;
import weg.heck_schools.infra.repository.alunorepo.AlunoRepository;
import weg.heck_schools.infra.repository.turmarepo.TurmaRepository;
import weg.heck_schools.service.mapper.TurmaMapper;
import weg.heck_schools.service.mapper.TurmaMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TurmaService {
    public AlunoRepository alunoRepository;
    public TurmaRepository turmaRepository;
    public TurmaMapper turmaMapper;

    public TurmaService(TurmaRepository turmaRepository, TurmaMapper turmaMapper, AlunoRepository alunoRepository) {
        this.turmaRepository = turmaRepository;
        this.turmaMapper = turmaMapper;
        this.alunoRepository = alunoRepository;
    }

    public TurmaResponseDTO cadastrarTurma (TurmaRequestDTO turmaRequestDTO) throws SQLException {
        Turma turma = turmaMapper.toEntity(turmaRequestDTO);
        turmaRepository.salvarTurma(turma);
        if (turma == null) {
            throw new RuntimeException("Erro ao tentar salvar turma no banco de dados!");
        }
        return turmaMapper.toResponse(turma, toNameList(turmaRequestDTO.listaAlunosIds()));
    }

    public List<TurmaResponseDTO> listarTurmas () throws SQLException {
        List<Turma> listaDeTurmas = turmaRepository.listarTurmas();
        List<TurmaResponseDTO> listaDeTurmasDto = new ArrayList<>();

        if (listaDeTurmas.isEmpty()) {
            throw new RuntimeException("Não há nenhum turma cadastrado!");
        }

        for (Turma turma : listaDeTurmas) {
            List<Aluno> listaAlunosTurma = alunoRepository.listarAlunosDaTurma(turma.getId());
            List<String> listaNomeAlunos = listaAlunosTurma.stream().map(
                    Aluno::getNome
            ).toList();
            TurmaResponseDTO response = turmaMapper.toResponse(turma, listaNomeAlunos);
            listaDeTurmasDto.add(response);
        }

        return listaDeTurmasDto;
    }

    public TurmaResponseDTO buscarTurma (long id) throws SQLException {
        Turma turmaBuscado = turmaRepository.buscarTurma(id)
                .orElseThrow(() -> new RuntimeException("Nenhum turma com este id foi encontrado!"));

        List<Aluno> listaAlunosTurma = alunoRepository.listarAlunosDaTurma(id);
        List<String> listaNomeAlunos = listaAlunosTurma.stream().map(
                Aluno::getNome
        ).toList();

        return turmaMapper.toResponse(turmaBuscado, listaNomeAlunos);
    }

    public TurmaResponseDTO atualizarTurma (TurmaRequestDTO turmaRequestDTO, long id) throws SQLException {
        Turma turma = turmaMapper.toEntity(turmaRequestDTO);
        if (turmaRepository.buscarTurma(id).isEmpty()) {
            throw new RuntimeException("Não existe nenhum turma com esse id para atualizar!");
        }
        turma.setId(id);
        turmaRepository.atualizarTurma(turma);
        return turmaMapper.toResponse(turma, toNameList(turmaRequestDTO.listaAlunosIds()));
    }

    public void deletarTurma (long id) throws SQLException {
        if (turmaRepository.buscarTurma(id).isEmpty()) {
            throw new RuntimeException("Não existe nenhum turma com esse id para deletar!");
        }
        turmaRepository.deletarTurma(id);
    }

    public List<String> toNameList (List<Long> idsList) throws SQLException {
        if (idsList.isEmpty()) {
            throw new RuntimeException("A lista de ids está vazia");
        }
        List<Aluno> listaAlunoes = alunoRepository.listarAlunosPorVariosIds(idsList);

        return listaAlunoes.stream().map(Aluno::getNome).toList();
    }
}
