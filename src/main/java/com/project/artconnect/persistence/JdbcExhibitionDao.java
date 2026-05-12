package com.project.artconnect.persistence;

import com.project.artconnect.dao.ExhibitionDao;
import com.project.artconnect.model.Exhibition;
import com.project.artconnect.config.DatabaseConfig;
import com.project.artconnect.model.Gallery;
import com.project.artconnect.util.ConnectionManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcExhibitionDao implements ExhibitionDao {

    @Override
    public List<Exhibition> findAll() {
        List<Exhibition> exhibitions = new ArrayList<>();
        String sql = "SELECT e.*, g.name AS gallery_name FROM Exhibition e " +
                "LEFT JOIN Gallery g ON e.gallery_id = g.gallery_id";

        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Exhibition ex = new Exhibition();
                ex.setTitle(rs.getString("title"));
                ex.setTheme(rs.getString("theme"));

                Date sDate = rs.getDate("startDate");
                if (sDate != null) {
                    ex.setStartDate(sDate.toLocalDate());
                }

                Gallery gallery = new Gallery();
                gallery.setName(rs.getString("gallery_name"));
                ex.setGallery(gallery);

                exhibitions.add(ex);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exhibitions;
    }

    @Override
    public void save(Exhibition exhibition) {
        String sql = "INSERT INTO Exhibition (exhibition_id, title, theme, gallery_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "EXH-" + System.currentTimeMillis());
            pstmt.setString(2, exhibition.getTitle());
            pstmt.setString(3, exhibition.getTheme());
            pstmt.setString(4, "GAL-01");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Exhibition exhibition) {
        String sql = "UPDATE Exhibition SET theme = ? WHERE title = ?";
        try (Connection conn = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, exhibition.getTheme());
            pstmt.setString(2, exhibition.getTitle());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String title) {
        String sql = "DELETE FROM Exhibition WHERE title = ?";
        try (Connection conn = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}