package weg.heck_schools.infra.repository.notarepo;

import weg.heck_schools.domain.models.Nota;

import java.sql.SQLException;
import java.util.List;

public interface NotaRepository {

    Nota salvarNota (Nota nota) throws SQLException;
    Nota buscarNota (long id) throws SQLException;
    List<Nota> listarNotas () throws SQLException;
    void atualizarNota (Nota nota) throws SQLException;
    void deletarNota (long id) throws SQLException;
}
