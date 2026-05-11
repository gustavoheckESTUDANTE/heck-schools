package weg.heck_schools.infra.repository.professorrepo;

import weg.heck_schools.domain.models.Professor;

import java.sql.SQLException;
import java.util.List;

public interface ProfessorRepository {

    Professor salvarProfessor (Professor professor) throws SQLException;
    Professor buscarProfessor (long id) throws SQLException;
    List<Professor> listarProfessors () throws SQLException;
    void atualizarProfessor (Professor professor) throws SQLException;
    void deletarProfessor (long id) throws SQLException;
}
