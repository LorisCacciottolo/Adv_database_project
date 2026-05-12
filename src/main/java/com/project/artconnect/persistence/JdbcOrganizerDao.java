package com.project.artconnect.persistence;
import com.project.artconnect.dao.OrganizerDao;
import com.project.artconnect.model.Organizer;
import com.project.artconnect.config.DatabaseConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcOrganizerDao implements OrganizerDao {
    @Override
    public List<Organizer> findAll() {
        List<Organizer> list = new ArrayList<>();
        String sql = "SELECT * FROM Organizer";
        try (Connection conn = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Organizer o = new Organizer();
                o.setName(rs.getString("name"));
                o.setEmail(rs.getString("email"));
                o.setOrganization(rs.getString("organization"));
                list.add(o);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public void save(Organizer o) {
        String sql = "INSERT INTO Organizer (organizer_id, name, email, phone, organization) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "ORG-" + System.currentTimeMillis());
            pstmt.setString(2, o.getName());
            pstmt.setString(3, o.getEmail());
            pstmt.setString(4, o.getPhone());
            pstmt.setString(5, o.getOrganization());
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}