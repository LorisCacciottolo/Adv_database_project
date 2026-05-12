package com.project.artconnect.persistence;
import com.project.artconnect.dao.DisciplineDao;
import com.project.artconnect.model.Discipline;
import com.project.artconnect.config.DatabaseConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcDisciplineDao implements DisciplineDao {
    @Override
    public List<Discipline> findAll() {
        List<Discipline> list = new ArrayList<>();
        String sql = "SELECT * FROM Discipline";
        try (Connection conn = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Discipline d = new Discipline();
                d.setName(rs.getString("name"));
                list.add(d);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public void save(Discipline d) {
        String sql = "INSERT INTO Discipline (discipline_id, name) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "DISC-" + System.currentTimeMillis());
            pstmt.setString(2, d.getName());
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}