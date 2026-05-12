package weg.heck_schools.service.mapper;

import org.springframework.stereotype.Component;
import weg.heck_schools.controller.dto.cursodto.CursoRequestDTO;
import weg.heck_schools.controller.dto.cursodto.CursoResponseDTO;
import weg.heck_schools.domain.models.Curso;

import java.util.List;

@Component
public class CursoMapper {

    public Curso toEntity (CursoRequestDTO cursoRequestDTO) {
        return new Curso(
                cursoRequestDTO.nome(),
                cursoRequestDTO.codigo()
        );
    }

    public CursoResponseDTO toResponse (Curso curso, List<String> listaProfessorNomes) {
        return new CursoResponseDTO(
                curso.getNome(),
                curso.getCodigo(),
                listaProfessorNomes
        );
    }
}
