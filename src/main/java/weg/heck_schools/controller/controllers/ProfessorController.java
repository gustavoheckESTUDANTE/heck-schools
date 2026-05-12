package weg.heck_schools.controller.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weg.heck_schools.controller.dto.professordto.ProfessorRequestDTO;
import weg.heck_schools.controller.dto.professordto.ProfessorResponseDTO;
import weg.heck_schools.service.services.ProfessorService;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

    public final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @PostMapping
    public ResponseEntity<ProfessorResponseDTO> cadastrarProfessor (@RequestBody ProfessorRequestDTO professorRequestDTO) {
        try {
            ProfessorResponseDTO professorCadastrado = professorService.cadastrarProfessor(professorRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(professorCadastrado);
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ProfessorResponseDTO>> listarProfessors () {
        try {
            List<ProfessorResponseDTO> listProfessors = professorService.listarProfessors();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(listProfessors);
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> buscarProfessor (@PathVariable long id) {
        try {
            ProfessorResponseDTO professor = professorService.buscarProfessor(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(professor);
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> atualizarProfessor (@PathVariable long id,
                                                            @RequestBody ProfessorRequestDTO professorRequestDTO
    ) {
        try {
            ProfessorResponseDTO professor = professorService.atualizarProfessor(professorRequestDTO, id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(professor);
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> atualizarProfessor (@PathVariable long id) {
        try {
            professorService.deletarProfessor(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
