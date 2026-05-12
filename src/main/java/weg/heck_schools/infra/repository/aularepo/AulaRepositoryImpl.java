package weg.heck_schools.infra.repository.aularepo;

import org.springframework.stereotype.Repository;
import weg.heck_schools.domain.models.Aula;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AulaRepositoryImpl implements AulaRepository{
    public final DataSource dataSource;

    public AulaRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Aula salvarAula (Aula aula) throws SQLException {
        String sql = """
                INSERT INTO aula
                    (turma_id,
                    data_hora,
                    assunto)
                VALUES
                    (?, ?, ?)
                """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, aula.getTurmaId());
            stmt.setTimestamp(2, Timestamp.valueOf(aula.getDataHora()));
            stmt.setString(3, aula.getAssunto());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if(rs.next()) {
                aula.setId(rs.getLong(1));
                return aula;
            }
            return null;
        }
    }

    @Override
    public Optional<Aula> buscarAula(long id) throws SQLException {
        String sql = """
                SELECT FROM aula
                    id,
                    turma_id,
                    data_hora,
                    assunto
                WHERE
                    id = ?
                """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                long idBuscado = rs.getLong("id");
                long turmaId = rs.getLong("turma_id");
                LocalDateTime dataHora =
                        (rs.getTimestamp("data_nascimento") != null) ? (rs.getTimestamp("data_nascimento").toLocalDateTime()) : (null);
                String assunto = rs.getString("assunto");

                return Optional.of(new Aula(idBuscado, turmaId, dataHora, assunto));
            }
            return Optional.empty();
        }
    }

    @Override
    public List<Aula> listarAulas() throws SQLException {
        String sql = """
                SELECT FROM aula
                    id,
                    turma_id,
                    data_hora,
                    assunto
                """;
        List<Aula> aulasList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                long idBuscado = rs.getLong("id");
                long turmaId = rs.getLong("turma_id");
                LocalDateTime dataHora =
                        (rs.getTimestamp("data_nascimento") != null) ? (rs.getTimestamp("data_nascimento").toLocalDateTime()) : (null);
                String assunto = rs.getString("assunto");

                aulasList.add(new Aula(idBuscado, turmaId, dataHora, assunto));
            }
            return aulasList;
        }
    }

    @Override
    public void atualizarAula(Aula aula) throws SQLException {
        String sql = """
                UPDATE
                    aula
                SET
                    turma_id = ?,
                    data_hora = ?,
                    assunto = ?
                WHERE
                    id = ?
                """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, aula.getTurmaId());
            stmt.setTimestamp(2, (aula.getDataHora() != null) ? (Timestamp.valueOf(aula.getDataHora())) : (null));
            stmt.setString(3, aula.getAssunto());
            stmt.setLong(5, aula.getId());

            stmt.executeUpdate();

        }
    }

    @Override
    public void deletarAula(long id) throws SQLException {
        String sql = """
                DELETE FROM
                    aula
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
