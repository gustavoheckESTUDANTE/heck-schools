package weg.heck_schools.infra.repository.turmarepo;

import weg.heck_schools.domain.models.Aluno;
import weg.heck_schools.domain.models.Turma;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface TurmaRepository {

    Turma salvarTurma (Turma turma) throws SQLException;
    void associarAlunoATurma (long turmaId, long alunoId) throws SQLException;
    Optional<Turma> buscarTurma (long id) throws SQLException;
    List<Turma> listarTurmas () throws SQLException;
    List<Turma> listarTurmasPorIdCurso (long id) throws SQLException;
    void atualizarTurma (Turma turma) throws SQLException;
    void deletarTurma (long id) throws SQLException;
}
