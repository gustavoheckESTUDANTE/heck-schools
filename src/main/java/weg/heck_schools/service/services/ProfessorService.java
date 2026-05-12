package weg.heck_schools.service.services;

import org.springframework.stereotype.Service;
import weg.heck_schools.controller.dto.professordto.ProfessorRequestDTO;
import weg.heck_schools.controller.dto.professordto.ProfessorResponseDTO;
import weg.heck_schools.domain.models.Professor;
import weg.heck_schools.infra.repository.professorrepo.ProfessorRepository;
import weg.heck_schools.service.mapper.ProfessorMapper;

import java.sql.SQLException;
import java.util.List;

@Service
public class ProfessorService {

    public ProfessorRepository professorRepository;
    public ProfessorMapper professorMapper;

    public ProfessorService(ProfessorRepository professorRepository, ProfessorMapper professorMapper) {
        this.professorRepository = professorRepository;
        this.professorMapper = professorMapper;
    }

    public ProfessorResponseDTO cadastrarProfessor (ProfessorRequestDTO professorRequestDTO) throws SQLException {
        Professor professor = professorMapper.toEntity(professorRequestDTO);
        professorRepository.salvarProfessor(professor);
        if (professor == null) {
            throw new RuntimeException("Erro ao tentar salvar professor no banco de dados!");
        }
        return professorMapper.toResponse(professor);
    }

    public List<ProfessorResponseDTO> listarProfessors () throws SQLException {
        List<Professor> listaDeProfessors = professorRepository.listarProfessors();
        List<ProfessorResponseDTO> listaDeProfessorsDto;

        if (listaDeProfessors.isEmpty()) {
            throw new RuntimeException("Não há nenhum professor cadastrado!");
        }

        listaDeProfessorsDto = listaDeProfessors.stream().map(
                professorMapper::toResponse
        ).toList();

        return listaDeProfessorsDto;
    }

    public ProfessorResponseDTO buscarProfessor (long id) throws SQLException {
        Professor professorBuscado = professorRepository.buscarProfessor(id)
                .orElseThrow(() -> new RuntimeException("Nenhum professor com este id foi encontrado!"));

        return professorMapper.toResponse(professorBuscado);
    }

    public ProfessorResponseDTO atualizarProfessor (ProfessorRequestDTO professorRequestDTO, long id) throws SQLException {
        Professor professor = professorMapper.toEntity(professorRequestDTO);
        if (professorRepository.buscarProfessor(id).isEmpty()) {
            throw new RuntimeException("Não existe nenhum professor com esse id para atualizar!");
        }
        professor.setId(id);
        professorRepository.atualizarProfessor(professor);
        return professorMapper.toResponse(professor);
    }

    public void deletarProfessor (long id) throws SQLException {
        if (professorRepository.buscarProfessor(id).isEmpty()) {
            throw new RuntimeException("Não existe nenhum professor com esse id para deletar!");
        }
        professorRepository.deletarProfessor(id);
    }
}
