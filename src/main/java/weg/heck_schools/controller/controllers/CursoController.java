package weg.heck_schools.controller.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weg.heck_schools.controller.dto.cursodto.CursoRequestDTO;
import weg.heck_schools.controller.dto.cursodto.CursoResponseDTO;
import weg.heck_schools.controller.dto.turmadto.TurmaResponseDTO;
import weg.heck_schools.service.services.CursoService;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/curso")
public class CursoController {

    public final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @PostMapping
    public ResponseEntity<CursoResponseDTO> cadastrarCurso (@RequestBody CursoRequestDTO cursoRequestDTO) {
        try {
            CursoResponseDTO cursoCadastrado = cursoService.cadastrarCurso(cursoRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(cursoCadastrado);
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<CursoResponseDTO>> listarCursos () {
        try {
            List<CursoResponseDTO> listCursos = cursoService.listarCursos();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(listCursos);
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> buscarCurso (@PathVariable long id) {
        try {
            CursoResponseDTO curso = cursoService.buscarCurso(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(curso);
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/{id}/turmas")
    public ResponseEntity<List<TurmaResponseDTO>> buscarTurmasCurso (@PathVariable long id) {
        try {
            List<TurmaResponseDTO> listaTurmas = cursoService.listarTurmasPorCurso(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(listaTurmas);
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> atualizarCurso (@PathVariable long id,
                                                            @RequestBody CursoRequestDTO cursoRequestDTO
    ) {
        try {
            CursoResponseDTO curso = cursoService.atualizarCurso(cursoRequestDTO, id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(curso);
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> atualizarCurso (@PathVariable long id) {
        try {
            cursoService.deletarCurso(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
