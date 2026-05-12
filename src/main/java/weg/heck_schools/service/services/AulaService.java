package weg.heck_schools.service.services;

import org.springframework.stereotype.Service;
import weg.heck_schools.controller.dto.auladto.AulaRequestDTO;
import weg.heck_schools.controller.dto.auladto.AulaResponseDTO;
import weg.heck_schools.domain.models.Aula;
import weg.heck_schools.infra.repository.aularepo.AulaRepository;
import weg.heck_schools.service.mapper.AulaMapper;

import java.sql.SQLException;
import java.util.List;

@Service
public class AulaService {
    public AulaRepository aulaRepository;
    public AulaMapper aulaMapper;

    public AulaService(AulaRepository aulaRepository, AulaMapper aulaMapper) {
        this.aulaRepository = aulaRepository;
        this.aulaMapper = aulaMapper;
    }

    public AulaResponseDTO cadastrarAula (AulaRequestDTO aulaRequestDTO) throws SQLException {
        Aula aula = aulaMapper.toEntity(aulaRequestDTO);
        aulaRepository.salvarAula(aula);
        if (aula == null) {
            throw new RuntimeException("Erro ao tentar salvar aula no banco de dados!");
        }
        return aulaMapper.toResponse(aula);
    }

    public List<AulaResponseDTO> listarAulas () throws SQLException {
        List<Aula> listaDeAulas = aulaRepository.listarAulas();
        List<AulaResponseDTO> listaDeAulasDto;

        if (listaDeAulas.isEmpty()) {
            throw new RuntimeException("Não há nenhum aula cadastrado!");
        }

        listaDeAulasDto = listaDeAulas.stream().map(
                aulaMapper::toResponse
        ).toList();

        return listaDeAulasDto;
    }

    public AulaResponseDTO buscarAula (long id) throws SQLException {
        Aula aulaBuscado = aulaRepository.buscarAula(id)
                .orElseThrow(() -> new RuntimeException("Nenhum aula com este id foi encontrado!"));

        return aulaMapper.toResponse(aulaBuscado);
    }

    public AulaResponseDTO atualizarAula (AulaRequestDTO aulaRequestDTO, long id) throws SQLException {
        Aula aula = aulaMapper.toEntity(aulaRequestDTO);
        if (aulaRepository.buscarAula(id).isEmpty()) {
            throw new RuntimeException("Não existe nenhum aula com esse id para atualizar!");
        }
        aula.setId(id);
        aulaRepository.atualizarAula(aula);
        return aulaMapper.toResponse(aula);
    }

    public void deletarAula (long id) throws SQLException {
        if (aulaRepository.buscarAula(id).isEmpty()) {
            throw new RuntimeException("Não existe nenhum aula com esse id para deletar!");
        }
        aulaRepository.deletarAula(id);
    }
}
