package weg.heck_schools.service.services;

import org.springframework.stereotype.Service;
import weg.heck_schools.controller.dto.notadto.NotaRequestDTO;
import weg.heck_schools.controller.dto.notadto.NotaResponseDTO;
import weg.heck_schools.domain.models.Nota;
import weg.heck_schools.infra.repository.notarepo.NotaRepository;
import weg.heck_schools.service.mapper.NotaMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotaService {
    public final NotaRepository notaRepository;
    public final NotaMapper notaMapper;

    public NotaService(NotaRepository notaRepository, NotaMapper notaMapper) {
        this.notaRepository = notaRepository;
        this.notaMapper = notaMapper;
    }

    public NotaResponseDTO cadastrarNota (NotaRequestDTO notaRequestDTO) throws SQLException {
        Nota nota = notaMapper.toEntity(notaRequestDTO);
        if (nota == null) {
            throw new RuntimeException("Erro ao tentar salvar nota no banco de dados!");
        }
        notaRepository.salvarNota(nota);
        return notaMapper.toResponse(nota);
    }

    public List<NotaResponseDTO> listarNotas () throws SQLException {
        List<Nota> listaDeNotas = notaRepository.listarNotas();
        List<NotaResponseDTO> listaDeNotasDto = new ArrayList<>();

        if (listaDeNotas.isEmpty()) {
            throw new RuntimeException("Não há nenhum nota cadastrado!");
        }

        for (Nota nota : listaDeNotas) {
            NotaResponseDTO response = notaMapper.toResponse(nota);
            listaDeNotasDto.add(response);
        }

        return listaDeNotasDto;
    }

    public NotaResponseDTO buscarNota (long id) throws SQLException {
        Nota notaBuscado = notaRepository.buscarNota(id)
                .orElseThrow(() -> new RuntimeException("Nenhum nota com este id foi encontrado!"));

        return notaMapper.toResponse(notaBuscado);
    }

    public NotaResponseDTO atualizarNota (NotaRequestDTO notaRequestDTO, long id) throws SQLException {
        Nota nota = notaMapper.toEntity(notaRequestDTO);
        if (notaRepository.buscarNota(id).isEmpty()) {
            throw new RuntimeException("Não existe nenhum nota com esse id para atualizar!");
        }
        nota.setId(id);
        notaRepository.atualizarNota(nota);
        return notaMapper.toResponse(nota);
    }

    public void deletarNota (long id) throws SQLException {
        if (notaRepository.buscarNota(id).isEmpty()) {
            throw new RuntimeException("Não existe nenhum nota com esse id para deletar!");
        }
        notaRepository.deletarNota(id);
    }
}
