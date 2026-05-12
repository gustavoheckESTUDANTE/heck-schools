package weg.heck_schools.controller.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weg.heck_schools.controller.dto.auladto.AulaRequestDTO;
import weg.heck_schools.controller.dto.auladto.AulaResponseDTO;
import weg.heck_schools.service.services.AulaService;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/aula")
public class AulaController {

    public final AulaService aulaService;

    public AulaController(AulaService aulaService) {
        this.aulaService = aulaService;
    }

    @PostMapping
    public ResponseEntity<AulaResponseDTO> cadastrarAula (@RequestBody AulaRequestDTO aulaRequestDTO) {
        try {
            AulaResponseDTO aulaCadastrado = aulaService.cadastrarAula(aulaRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(aulaCadastrado);
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<AulaResponseDTO>> listarAulas () {
        try {
            List<AulaResponseDTO> listAulas = aulaService.listarAulas();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(listAulas);
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AulaResponseDTO> buscarAula (@PathVariable long id) {
        try {
            AulaResponseDTO aula = aulaService.buscarAula(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(aula);
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AulaResponseDTO> atualizarAula (@PathVariable long id,
                                                            @RequestBody AulaRequestDTO aulaRequestDTO
    ) {
        try {
            AulaResponseDTO aula = aulaService.atualizarAula(aulaRequestDTO, id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(aula);
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> atualizarAula (@PathVariable long id) {
        try {
            aulaService.deletarAula(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
