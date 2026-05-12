package weg.heck_schools.infra.repository.notarepo;

import org.springframework.stereotype.Repository;
import weg.heck_schools.domain.models.Nota;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class NotaRepositoryImpl implements NotaRepository {
    public final DataSource dataSource;

    public NotaRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Nota salvarNota(Nota nota) throws SQLException {
        String sql = """
                INSERT INTO nota
                    (aluno_id,
                    aula_id,
                    valor)
                VALUES
                    (?, ?, ?)
                """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, nota.getAlunoId());
            stmt.setLong(2, nota.getAulaId());
            stmt.setDouble(3, nota.getValor());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if(rs.next()) {
                nota.setId(rs.getLong(1));
                return nota;
            }
            return null;
        }
    }

    @Override
    public Optional<Nota> buscarNota(long id) throws SQLException {
        String sql = """
                SELECT FROM nota
                    id,
                    aluno_id,
                    aula_id,
                    valor
                WHERE
                    id = ?
                """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                long idBuscado = rs.getLong("id");
                long alunoId = rs.getLong("aluno_id");
                long aulaId = rs.getLong("aula_id");
                double valor = rs.getDouble("valor");

                return Optional.of(new Nota(idBuscado, alunoId, aulaId, valor));
            }
            return Optional.empty();
        }
    }

    @Override
    public List<Nota> listarNotas() throws SQLException {
        String sql = """
                SELECT FROM nota
                    id,
                    aluno_id,
                    aula_id,
                    valor
                """;
        List<Nota> notasList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                long idBuscado = rs.getLong("id");
                long alunoId = rs.getLong("aluno_id");
                long aulaId = rs.getLong("aula_id");
                double valor = rs.getDouble("valor");

                notasList.add(new Nota(idBuscado, alunoId, aulaId, valor));
            }
            return notasList;
        }
    }

    @Override
    public List<Nota> listarNotasPorIdAluno(long id) throws SQLException {
        String sql = """
                SELECT FROM nota
                    id,
                    aluno_id,
                    aula_id,
                    valor
                WHERE
                    aluno_id = ?
                """;
        List<Nota> notasList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                long idBuscado = rs.getLong("id");
                long alunoId = rs.getLong("aluno_id");
                long aulaId = rs.getLong("aula_id");
                double valor = rs.getDouble("valor");

                notasList.add(new Nota(idBuscado, alunoId, aulaId, valor));
            }
            return notasList;
        }
    }

    @Override
    public void atualizarNota(Nota nota) throws SQLException {
        String sql = """
                UPDATE
                    nota
                SET
                    aluno_id = ?,
                    aula_id = ?,
                    valor = ?
                WHERE
                    id = ?
                """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, nota.getAlunoId());
            stmt.setLong(2, nota.getAulaId());
            stmt.setDouble(3, nota.getValor());
            stmt.setLong(4, nota.getId());

            stmt.executeUpdate();

        }
    }

    @Override
    public void deletarNota(long id) throws SQLException {
        String sql = """
                DELETE FROM
                    nota
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
