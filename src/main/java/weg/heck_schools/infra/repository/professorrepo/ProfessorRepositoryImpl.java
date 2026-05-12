package weg.heck_schools.infra.repository.professorrepo;

import org.springframework.stereotype.Repository;
import weg.heck_schools.domain.models.Aluno;
import weg.heck_schools.domain.models.Curso;
import weg.heck_schools.domain.models.Professor;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Optional<Professor> buscarProfessor(long id) throws SQLException {
        String sql = """
                SELECT
                    id,
                    nome,
                    email,
                    disciplina
                FROM
                    professor
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

                return Optional.of(new Professor(idBuscado, nome, email, disciplina));
            }
            return Optional.empty();
        }
    }

    @Override
    public List<Professor> listarProfessors() throws SQLException {
        String sql = """
                SELECT
                    id,
                    nome,
                    email,
                    disciplina
                FROM
                    professor
                """;
        List<Professor> professoresList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                long idBuscado = rs.getLong("id");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String disciplina = rs.getString("disciplina");

                professoresList.add(new Professor(idBuscado, nome, email, disciplina));
            }
            return professoresList;
        }
    }

    public List<Professor> listarProfessoresPorVariosIds(List<Long> listaIds) throws SQLException {
        String sql = """
                SELECT
                    id,
                    nome,
                    email,
                    disciplina
                FROM
                    professor
                WHERE
                    id IN (?""";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < listaIds.size(); i++) {
            if (i != listaIds.size() - 1) {
                sb.append(", ?");
            } else {
                sb.append("?)");
            }
        }
        sql += sb.toString();
        List<Professor> professoresList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < listaIds.size(); i++) {
                stmt.setLong(i + 1, listaIds.get(i));
            }

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                long idBuscado = rs.getLong("id");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String disciplina = rs.getString("disciplina");

                professoresList.add(new Professor(idBuscado, nome, email, disciplina));
            }
            return professoresList;
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
    @Override
    public List<Professor> listarProfessoresDoCurso(long id) throws SQLException {
        String sql = """
                SELECT
                	p.id,
                    p.nome,
                    p.email,
                    p.disciplina
                FROM
                    curso AS c
                WHERE
                    c.id = ?
                JOIN
                    curso_professor AS cp
                	    ON c.id = cp.curso_id
                JOIN
                    professor AS p
                	    ON cp.professor_id = p.id
                """;
        List<Professor> professoresDoCurso = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                long idBuscado = rs.getLong("id");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String disciplina = rs.getString("disciplina");

                professoresDoCurso.add(new Professor(idBuscado, nome, email, disciplina));
            }
            return professoresDoCurso;
        }
    }

}
