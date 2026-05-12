package weg.heck_schools.controller.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weg.heck_schools.controller.dto.alunodto.AlunoRequestDTO;
import weg.heck_schools.controller.dto.alunodto.AlunoResponseDTO;
import weg.heck_schools.controller.dto.notadto.NotaResponseDTO;
import weg.heck_schools.service.services.AlunoService;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    public final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    public ResponseEntity<AlunoResponseDTO> cadastrarAluno (@RequestBody AlunoRequestDTO alunoRequestDTO) {
        try {
            AlunoResponseDTO alunoCadastrado = alunoService.cadastrarAluno(alunoRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(alunoCadastrado);
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<AlunoResponseDTO>> listarAlunos () {
        try {
            List<AlunoResponseDTO> listAlunos = alunoService.listarAlunos();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(listAlunos);
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> buscarAluno (@PathVariable long id) {
        try {
            AlunoResponseDTO aluno = alunoService.buscarAluno(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(aluno);
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/{id}/notas")
    public ResponseEntity<List<NotaResponseDTO>> buscarNotasAluno (@PathVariable long id) {
        try {
            List<NotaResponseDTO> listaNotas = alunoService.notasDoAluno(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(listaNotas);
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> atualizarAluno (@PathVariable long id,
                                                            @RequestBody AlunoRequestDTO alunoRequestDTO
    ) {
        try {
            AlunoResponseDTO aluno = alunoService.atualizarAluno(alunoRequestDTO, id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(aluno);
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> atualizarAluno (@PathVariable long id) {
        try {
            alunoService.deletarAluno(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
