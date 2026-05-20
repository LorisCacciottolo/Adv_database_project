package com.project.artconnect.persistence;

import com.project.artconnect.dao.ExhibitionDao;
import com.project.artconnect.model.Exhibition;
import com.project.artconnect.config.DatabaseConfig;
import com.project.artconnect.model.Gallery;
import com.project.artconnect.model.Organizer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcExhibitionDao implements ExhibitionDao {

    @Override
    public List<Exhibition> findAll() {
        List<Exhibition> exhibitions = new ArrayList<>();
        String sql = "SELECT * FROM vw_ExhibitionDetails";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Organizer o = new Organizer();
                o.setName(rs.getString("organizer_name"));

                Gallery g = new Gallery();
                g.setName(rs.getString("gallery_name"));
                g.setOrganizer(o);

                Exhibition ex = new Exhibition();
                ex.setTitle(rs.getString("exhibition_title"));
                ex.setTheme(rs.getString("theme"));

                Date sDate = rs.getDate("startDate");
                if (sDate != null) {
                    ex.setStartDate(sDate.toLocalDate());
                }
                Date eDate = rs.getDate("endDate");
                if (eDate != null) {
                    ex.setEndDate(eDate.toLocalDate());
                }

                ex.setGallery(g);

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