package weg.heck_schools.infra.repository.alunorepo;

import org.springframework.stereotype.Repository;
import weg.heck_schools.domain.models.Aluno;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AlunoRepositoryImpl implements AlunoRepository {

    public final DataSource dataSource;

    public AlunoRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Aluno salvarAluno(Aluno aluno) throws SQLException {
        String sql = """
                INSERT INTO aluno
                    (nome,
                    email,
                    matricula,
                    data_nascimento)
                VALUES
                    (?, ?, ?, ?, ?)
                """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEmail());
            stmt.setString(3, aluno.getMatricula());
            stmt.setDate(4, Date.valueOf(aluno.getDataNascimento()));

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if(rs.next()) {
                aluno.setId(rs.getLong(1));
                return aluno;
            }
            return null;
        }
    }

    @Override
    public Optional<Aluno> buscarAluno(long id) throws SQLException {
        String sql = """
                SELECT FROM aluno
                    id,
                    nome,
                    email,
                    matricula,
                    data_nascimento
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
                String matricula = rs.getString("matricula");
                LocalDate dataNascimento =
                        (rs.getDate("data_nascimento") != null) ? (rs.getDate("data_nascimento").toLocalDate()) : (null);

                return Optional.of(new Aluno(idBuscado, nome, email, matricula, dataNascimento));
            }
            return Optional.empty();
        }
    }

    @Override
    public List<Aluno> listarAlunos() throws SQLException {
        String sql = """
                SELECT FROM aluno
                    id,
                    nome,
                    email,
                    matricula,
                    data_nascimento
                """;
        List<Aluno> alunosList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                long idBuscado = rs.getLong("id");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String matricula = rs.getString("matricula");
                LocalDate dataNascimento =
                        (rs.getDate("data_nascimento") != null) ? (rs.getDate("data_nascimento").toLocalDate()) : (null);

                alunosList.add(new Aluno(idBuscado, nome, email, matricula, dataNascimento));
            }
            return alunosList;
        }
    }

    public List<Aluno> listarAlunosPorVariosIds(List<Long> listaIds) throws SQLException {
        String sql = """
                SELECT FROM aluno
                    id,
                    nome,
                    email,
                    matricula,
                    data_nascimento
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
        List<Aluno> alunosList = new ArrayList<>();
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
                String matricula = rs.getString("matricula");
                LocalDate dataNascimento =
                        (rs.getDate("data_nascimento") != null) ? (rs.getDate("data_nascimento").toLocalDate()) : (null);

                alunosList.add(new Aluno(idBuscado, nome, email, matricula, dataNascimento));
            }
            return alunosList;
        }
    }

    @Override
    public void atualizarAluno(Aluno aluno) throws SQLException {
        String sql = """
                UPDATE
                    aluno
                SET
                    nome = ?
                    email = ?
                    matricula = ?
                    data_nascimento = ?
                WHERE
                    id = ?
                """;
        try (Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEmail());
            stmt.setString(3, aluno.getMatricula());
            stmt.setDate(4, ( aluno.getDataNascimento() != null) ? (Date.valueOf(aluno.getDataNascimento())) : (null));
            stmt.setLong(5, aluno.getId());

            stmt.executeUpdate();

        }
    }

    @Override
    public void deletarAluno(long id) throws SQLException {
        String sql = """
                DELETE FROM
                    aluno
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
    public List<Aluno> listarAlunosDaTurma(long id) throws SQLException {
        String sql = """
                SELECT
                	a.id,
                    a.nome,
                    a.email,
                    a.matricula,
                    a.data_nascimento
                FROM
                    turma AS t
                WHERE
                    t.id = ?
                JOIN
                    turma_aluno AS ta
                	    ON t.id = ta.turma_id
                JOIN
                    aluno AS a
                	    ON ta.aluno_id = a.id
                """;
        List<Aluno> alunosDaTurma = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                long idBuscado = rs.getLong("id");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String matricula = rs.getString("matricula");
                LocalDate dataNascimento = (rs.getDate("data_nascimento") != null) ?
                        (rs.getDate("data_nascimento").toLocalDate()) :
                        (null);

                alunosDaTurma.add(new Aluno(idBuscado, nome, email, matricula, dataNascimento));
            }
            return alunosDaTurma;
        }
    }
}
