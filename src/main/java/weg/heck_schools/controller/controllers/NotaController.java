package weg.heck_schools.controller.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weg.heck_schools.controller.dto.notadto.NotaRequestDTO;
import weg.heck_schools.controller.dto.notadto.NotaResponseDTO;
import weg.heck_schools.service.services.NotaService;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/nota")
public class NotaController {

    public final NotaService notaService;

    public NotaController(NotaService notaService) {
        this.notaService = notaService;
    }

    @PostMapping
    public ResponseEntity<NotaResponseDTO> cadastrarNota (@RequestBody NotaRequestDTO notaRequestDTO) {
        try {
            NotaResponseDTO notaCadastrado = notaService.cadastrarNota(notaRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(notaCadastrado);
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<NotaResponseDTO>> listarNotas () {
        try {
            List<NotaResponseDTO> listNotas = notaService.listarNotas();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(listNotas);
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotaResponseDTO> buscarNota (@PathVariable long id) {
        try {
            NotaResponseDTO nota = notaService.buscarNota(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(nota);
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotaResponseDTO> atualizarNota (@PathVariable long id,
                                                            @RequestBody NotaRequestDTO notaRequestDTO
    ) {
        try {
            NotaResponseDTO nota = notaService.atualizarNota(notaRequestDTO, id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(nota);
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> atualizarNota (@PathVariable long id) {
        try {
            notaService.deletarNota(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
