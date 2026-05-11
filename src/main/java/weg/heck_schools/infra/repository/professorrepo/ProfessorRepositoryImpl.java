package weg.heck_schools.infra.repository.professorrepo;

import org.springframework.stereotype.Repository;
import weg.heck_schools.domain.models.Professor;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProfessorRepositoryImpl implements ProfessorRepository {
    public final DataSource dataSource;

    public ProfessorRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Professor salvarProfessor(Professor professor) throws SQLException {
        String sql = """
                INSERT INTO professor
                    (nome,
                    email,
                    disciplina)
                VALUES
                    (?, ?, ?)
                """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getEmail());
            stmt.setString(3, professor.getDisciplina());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if(rs.next()) {
                professor.setId(rs.getLong(1));
                return professor;
            }
            return null;
        }
    }

    @Override
    public Professor buscarProfessor(long id) throws SQLException {
        String sql = """
                SELECT FROM professor
                    id,
                    nome,
                    email,
                    disciplina
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
                String email = rs.getString("email");
                String disciplina = rs.getString("disciplina");

                return new Professor(idBuscado, nome, email, disciplina);
            }
            return null;
        }
    }

    @Override
    public List<Professor> listarProfessors() throws SQLException {
        String sql = """
                SELECT FROM professor
                    id,
                    nome,
                    email,
                    disciplina
                """;
        List<Professor> professorsList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                long idBuscado = rs.getLong("id");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String disciplina = rs.getString("disciplina");

                professorsList.add(new Professor(idBuscado, nome, email, disciplina));
            }
            return professorsList;
        }
    }

    @Override
    public void atualizarProfessor(Professor professor) throws SQLException {
        String sql = """
                UPDATE
                    professor
                SET
                    nome = ?,
                    email = ?,
                    disciplina  = ?
                WHERE
                    id = ?
                """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getEmail());
            stmt.setString(3, professor.getDisciplina());
            stmt.setLong(4, professor.getId());

            stmt.executeUpdate();

        }
    }

    @Override
    public void deletarProfessor(long id) throws SQLException {
        String sql = """
                DELETE FROM
                    professor
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
