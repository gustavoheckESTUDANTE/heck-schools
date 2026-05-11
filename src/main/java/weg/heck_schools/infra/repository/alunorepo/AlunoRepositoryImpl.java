package weg.heck_schools.infra.repository.alunorepo;

import org.springframework.stereotype.Repository;
import weg.heck_schools.domain.models.Aluno;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    public Aluno buscarAluno(long id) throws SQLException {
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

                return new Aluno(idBuscado, nome, email, matricula, dataNascimento);
            }
            return null;
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
}
