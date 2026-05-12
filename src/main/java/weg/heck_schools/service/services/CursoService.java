package weg.heck_schools.service.services;

import org.springframework.stereotype.Service;
import weg.heck_schools.controller.dto.cursodto.CursoRequestDTO;
import weg.heck_schools.controller.dto.cursodto.CursoResponseDTO;
import weg.heck_schools.domain.models.Curso;
import weg.heck_schools.domain.models.Professor;
import weg.heck_schools.infra.repository.cursorepo.CursoRepository;
import weg.heck_schools.infra.repository.professorrepo.ProfessorRepository;
import weg.heck_schools.service.mapper.CursoMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CursoService {
    public ProfessorRepository professorRepository;
    public CursoRepository cursoRepository;
    public CursoMapper cursoMapper;

    public CursoService(CursoRepository cursoRepository, CursoMapper cursoMapper, ProfessorRepository professorRepository) {
        this.cursoRepository = cursoRepository;
        this.cursoMapper = cursoMapper;
        this.professorRepository = professorRepository;
    }

    public CursoResponseDTO cadastrarCurso (CursoRequestDTO cursoRequestDTO) throws SQLException {
        Curso curso = cursoMapper.toEntity(cursoRequestDTO);
        cursoRepository.salvarCurso(curso);
        if (curso == null) {
            throw new RuntimeException("Erro ao tentar salvar curso no banco de dados!");
        }
        return cursoMapper.toResponse(curso, toNameList(cursoRequestDTO.listaProfessorIds()));
    }

    public List<CursoResponseDTO> listarCursos () throws SQLException {
        List<Curso> listaDeCursos = cursoRepository.listarCursos();
        List<CursoResponseDTO> listaDeCursosDto = new ArrayList<>();

        if (listaDeCursos.isEmpty()) {
            throw new RuntimeException("Não há nenhum curso cadastrado!");
        }

        for (Curso curso : listaDeCursos) {
            List<Professor> listaProfessores = professorRepository.listarProfessoresDoCurso(curso.getId());
            List<String> nomeProfessores = listaProfessores.stream().map(
                    Professor::getNome
            ).toList();

            listaDeCursosDto.add(cursoMapper.toResponse(curso, nomeProfessores));
        }

        return listaDeCursosDto;
    }

    public CursoResponseDTO buscarCurso (long id) throws SQLException {
        Curso cursoBuscado = cursoRepository.buscarCurso(id)
                .orElseThrow(() -> new RuntimeException("Nenhum curso com este id foi encontrado!"));

        List<Professor> listaProfessores = professorRepository.listarProfessoresDoCurso(id);
        List<String> nomeProfessores = listaProfessores.stream().map(
                Professor::getNome
        ).toList();

        return cursoMapper.toResponse(cursoBuscado, nomeProfessores);
    }

    public CursoResponseDTO atualizarCurso (CursoRequestDTO cursoRequestDTO, long id) throws SQLException {
        Curso curso = cursoMapper.toEntity(cursoRequestDTO);
        if (cursoRepository.buscarCurso(id).isEmpty()) {
            throw new RuntimeException("Não existe nenhum curso com esse id para atualizar!");
        }
        curso.setId(id);
        cursoRepository.atualizarCurso(curso);
        return cursoMapper.toResponse(curso, toNameList(cursoRequestDTO.listaProfessorIds()));
    }

    public void deletarCurso (long id) throws SQLException {
        if (cursoRepository.buscarCurso(id).isEmpty()) {
            throw new RuntimeException("Não existe nenhum curso com esse id para deletar!");
        }
        cursoRepository.deletarCurso(id);
    }

    public List<String> toNameList (List<Long> idsList) throws SQLException {
        if (idsList.isEmpty()) {
            throw new RuntimeException("A lista de ids está vazia");
        }
        List<Professor> listaProfessores = professorRepository.listarProfessoresPorVariosIds(idsList);

        return listaProfessores.stream().map(Professor::getNome).toList();
    }
}
