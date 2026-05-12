package weg.heck_schools.infra.repository.alunorepo;

import weg.heck_schools.domain.models.Aluno;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AlunoRepository {

    Aluno salvarAluno (Aluno aluno) throws SQLException;
    Optional<Aluno> buscarAluno (long id) throws SQLException;
    List<Aluno> listarAlunos () throws SQLException;
    List<Aluno> listarAlunosPorVariosIds (List<Long> listaIds) throws SQLException;
    void atualizarAluno (Aluno aluno) throws SQLException;
    void deletarAluno (long id) throws SQLException;
    List<Aluno> listarAlunosDaTurma (long id) throws SQLException;
}