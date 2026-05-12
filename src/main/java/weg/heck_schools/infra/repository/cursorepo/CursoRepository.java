package weg.heck_schools.infra.repository.cursorepo;

import weg.heck_schools.domain.models.Curso;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CursoRepository {

    Curso salvarCurso (Curso curso) throws SQLException;
    void associarProfessorAoCurso (long cursoId, long professorId) throws SQLException;
    Optional<Curso> buscarCurso (long id) throws SQLException;
    List<Curso> listarCursos () throws SQLException;
    void atualizarCurso (Curso curso) throws SQLException;
    void deletarCurso (long id) throws SQLException;
}
