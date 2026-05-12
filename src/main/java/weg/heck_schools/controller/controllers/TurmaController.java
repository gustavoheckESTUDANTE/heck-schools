package weg.heck_schools.controller.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weg.heck_schools.controller.dto.alunodto.AlunoResponseDTO;
import weg.heck_schools.controller.dto.turmadto.TurmaRequestDTO;
import weg.heck_schools.controller.dto.turmadto.TurmaResponseDTO;
import weg.heck_schools.service.services.TurmaService;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/turma")
public class TurmaController {

    public final TurmaService turmaService;

    public TurmaController(TurmaService turmaService) {
        this.turmaService = turmaService;
    }

    @PostMapping
    public ResponseEntity<TurmaResponseDTO> cadastrarTurma (@RequestBody TurmaRequestDTO turmaRequestDTO) {
        try {
            TurmaResponseDTO turmaCadastrado = turmaService.cadastrarTurma(turmaRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(turmaCadastrado);
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<TurmaResponseDTO>> listarTurmas () {
        try {
            List<TurmaResponseDTO> listTurmas = turmaService.listarTurmas();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(listTurmas);
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmaResponseDTO> buscarTurma (@PathVariable long id) {
        try {
            TurmaResponseDTO turma = turmaService.buscarTurma(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(turma);
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/{id}/alunos")
    public ResponseEntity<List<AlunoResponseDTO>> buscarAlunosTurma (@PathVariable long id) {
        try {
            List<AlunoResponseDTO> listaAlunos = turmaService.alunosDaTurma(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(listaAlunos);
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurmaResponseDTO> atualizarTurma (@PathVariable long id,
                                                            @RequestBody TurmaRequestDTO turmaRequestDTO
    ) {
        try {
            TurmaResponseDTO turma = turmaService.atualizarTurma(turmaRequestDTO, id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(turma);
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> atualizarTurma (@PathVariable long id) {
        try {
            turmaService.deletarTurma(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
