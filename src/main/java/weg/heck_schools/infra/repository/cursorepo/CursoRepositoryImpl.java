package weg.heck_schools.infra.repository.cursorepo;

import org.springframework.stereotype.Repository;
import weg.heck_schools.domain.models.Curso;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CursoRepositoryImpl implements CursoRepository {
    public final DataSource dataSource;

    public CursoRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Curso salvarCurso (Curso curso) throws SQLException {
        String sql = """
                INSERT INTO curso
                    (nome,
                    codigo)
                VALUES
                    (?, ?)
                """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getCodigo());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if(rs.next()) {
                curso.setId(rs.getLong(1));
                return curso;
            }
            return null;
        }
    }

    @Override
    public Curso buscarCurso(long id) throws SQLException {
        String sql = """
                SELECT FROM curso
                    id,
                    nome,
                    codigo
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
                String codigo = rs.getString("codigo");

                return new Curso(idBuscado, nome, codigo);
            }
            return null;
        }
    }

    @Override
    public List<Curso> listarCursos() throws SQLException {
        String sql = """
                SELECT FROM curso
                    id,
                    nome,
                    codigo
                """;
        List<Curso> cursosList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                long idBuscado = rs.getLong("id");
                String nome = rs.getString("nome");
                String codigo = rs.getString("codigo");

                cursosList.add(new Curso(idBuscado, nome, codigo));
            }
            return cursosList;
        }
    }

    @Override
    public void atualizarCurso(Curso curso) throws SQLException {
        String sql = """
                UPDATE
                    curso
                SET
                    nome = ?,
                    codigo = ?
                WHERE
                    id = ?
                """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getCodigo());
            stmt.setLong(3, curso.getId());

            stmt.executeUpdate();

        }
    }

    @Override
    public void deletarCurso(long id) throws SQLException {
        String sql = """
                DELETE FROM
                    curso
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
