package weg.heck_schools.infra.repository.professorrepo;

import weg.heck_schools.domain.models.Aluno;
import weg.heck_schools.domain.models.Curso;
import weg.heck_schools.domain.models.Professor;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProfessorRepository {

    Professor salvarProfessor (Professor professor) throws SQLException;
    Optional<Professor> buscarProfessor (long id) throws SQLException;
    List<Professor> listarProfessors () throws SQLException;
    List<Professor> listarProfessoresPorVariosIds (List<Long> listaIds) throws SQLException;
    void atualizarProfessor (Professor professor) throws SQLException;
    void deletarProfessor (long id) throws SQLException;
    List<Professor> listarProfessoresDoCurso (long id) throws SQLException;
}
