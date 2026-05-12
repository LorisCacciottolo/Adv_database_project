package com.project.artconnect.persistence;

import com.project.artconnect.dao.WorkshopDao;
import com.project.artconnect.model.Workshop;
import com.project.artconnect.config.DatabaseConfig;
import com.project.artconnect.model.Artist;
import com.project.artconnect.util.ConnectionManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcWorkshopDao implements WorkshopDao {

    @Override
    public Optional<Workshop> findById(Long id) {
        String sql = "SELECT * FROM Workshop WHERE workshop_id = ?";
        try (Connection conn = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, String.valueOf(id));
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Workshop w = new Workshop();
                w.setTitle(rs.getString("title"));
                w.setPrice(rs.getDouble("price"));
                w.setLocation(rs.getString("location"));
                return Optional.of(w);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Workshop> findAll() {
        List<Workshop> workshops = new ArrayList<>();
        String sql = "SELECT w.*, a.name AS instructor_name FROM Workshop w " +
                "LEFT JOIN Artist a ON w.artist_id = a.artist_id";

        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Workshop w = new Workshop();
                w.setTitle(rs.getString("title"));
                w.setPrice(rs.getDouble("price"));

                w.setLevel(rs.getString("level"));

                Artist instructor = new Artist();
                String name = rs.getString("instructor_name");
                instructor.setName(name != null ? name : "Unknown");
                w.setInstructor(instructor);

                workshops.add(w);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return workshops;
    }


    public void save(Workshop workshop) {
        String sql = "INSERT INTO Workshop (workshop_id, title, price, location, artist_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "WK-" + System.currentTimeMillis());
            pstmt.setString(2, workshop.getTitle());
            pstmt.setDouble(3, workshop.getPrice());
            pstmt.setString(4, workshop.getLocation());
            pstmt.setString(5, "ART-01");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Workshop workshop) {
        String sql = "UPDATE Workshop SET price = ?, location = ? WHERE title = ?";
        try (Connection conn = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, workshop.getPrice());
            pstmt.setString(2, workshop.getLocation());
            pstmt.setString(3, workshop.getTitle());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String title) {
        String sql = "DELETE FROM Workshop WHERE title = ?";
        try (Connection conn = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}