package weg.heck_schools.infra.repository.turmarepo;

import org.springframework.stereotype.Repository;
import weg.heck_schools.domain.models.Turma;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TurmaRepositoryImpl implements TurmaRepository {
    public final DataSource dataSource;

    public TurmaRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Turma salvarTurma(Turma turma) throws SQLException {
        String sql = """
                INSERT INTO turma
                    (nome,
                    curso_id,
                    professor_id)
                VALUES
                    (?, ?, ?)
                """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, turma.getNome());
            stmt.setLong(2, turma.getCursoId());
            stmt.setLong(3, turma.getProfessorId());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if(rs.next()) {
                turma.setId(rs.getLong(1));
                return turma;
            }
            return null;
        }
    }

    @Override
    public void associarAlunoATurma(long turmaId, long alunoId) throws SQLException {
        String sql = """
                INSERT INTO turma_aluno
                    (turma_id,
                    aluno_id)
                VALUES
                    (?, ?)
                """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, turmaId);
            stmt.setLong(2, alunoId);

            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Turma> buscarTurma(long id) throws SQLException {
        String sql = """
                SELECT
                    id,
                    nome,
                    curso_id,
                    professor_id
                FROM
                    turma
                WHERE
                    id = ?
                """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                long idBuscado = rs.getLong("id");
                String nome = rs.getString("nome");
                long cursoId = rs.getLong("curso_id");
                long professorId = rs.getLong("professor_id");

                return Optional.of(new Turma(idBuscado, nome, cursoId, professorId));
            }
            return Optional.empty();
        }
    }

    @Override
    public List<Turma> listarTurmas() throws SQLException {
        String sql = """
                SELECT
                    id,
                    nome,
                    curso_id,
                    professor_id
                FROM
                    turma
                """;
        List<Turma> turmasList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                long idBuscado = rs.getLong("id");
                String nome = rs.getString("nome");
                long cursoId = rs.getLong("curso_id");
                long professorId = rs.getLong("professor_id");

                turmasList.add(new Turma(idBuscado, nome, cursoId, professorId));
            }
            return turmasList;
        }
    }

    @Override
    public List<Turma> listarTurmasPorIdCurso(long id) throws SQLException {
        String sql = """
                SELECT
                    id,
                    nome,
                    curso_id,
                    professor_id
                FROM
                    turma
                WHERE
                    curso_id = ?
                """;
        List<Turma> turmasList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                long idBuscado = rs.getLong("id");
                String nome = rs.getString("nome");
                long cursoId = rs.getLong("curso_id");
                long professorId = rs.getLong("professor_id");

                turmasList.add(new Turma(idBuscado, nome, cursoId, professorId));
            }
            return turmasList;
        }
    }

    @Override
    public void atualizarTurma(Turma turma) throws SQLException {
        String sql = """
                UPDATE
                    turma
                SET
                    nome = ?,
                    curso_id = ?,
                    professor_id = ?
                WHERE
                    id = ?
                """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, turma.getNome());
            stmt.setLong(2, turma.getCursoId());
            stmt.setLong(3, turma.getProfessorId());
            stmt.setLong(4, turma.getId());

            stmt.executeUpdate();

        }
    }

    @Override
    public void deletarTurma(long id) throws SQLException {
        String sql = """
                DELETE FROM
                    turma
                WHERE
                    id = ?
                """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            stmt.executeUpdate();

        }
    }


}
