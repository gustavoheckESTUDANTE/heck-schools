package weg.heck_schools.infra.repository.turmarepo;

import weg.heck_schools.domain.models.Turma;

import java.sql.SQLException;
import java.util.List;

public interface TurmaRepository {

    Turma salvarTurma (Turma turma) throws SQLException;
    Turma buscarTurma (long id) throws SQLException;
    List<Turma> listarTurmas () throws SQLException;
    void atualizarTurma (Turma turma) throws SQLException;
    void deletarTurma (long id) throws SQLException;
}
