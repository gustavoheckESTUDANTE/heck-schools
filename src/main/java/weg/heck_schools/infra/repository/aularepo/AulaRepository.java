package weg.heck_schools.infra.repository.aularepo;

import weg.heck_schools.domain.models.Aula;

import java.sql.SQLException;
import java.util.List;

public interface AulaRepository {

    Aula salvarAula (Aula aula) throws SQLException;
    Aula buscarAula (long id) throws SQLException;
    List<Aula> listarAulas () throws SQLException;
    void atualizarAula (Aula aula) throws SQLException;
    void deletarAula (long id) throws SQLException;
}
