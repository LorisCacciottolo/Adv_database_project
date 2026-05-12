package com.project.artconnect.persistence;
import com.project.artconnect.dao.ReviewDao;
import com.project.artconnect.model.Review;
import com.project.artconnect.config.DatabaseConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcReviewDao implements ReviewDao {
    @Override
    public List<Review> findAll() {
        List<Review> list = new ArrayList<>();
        String sql = "SELECT * FROM Review";
        try (Connection conn = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Review r = new Review();
                r.setRating(rs.getInt("rating"));
                r.setComment(rs.getString("comment"));
                list.add(r);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public void save(Review r) {
        String sql = "INSERT INTO Review (artwork_id, member_id, rating, comment, reviewDate) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "AW-DEFAULT");
            pstmt.setString(2, "MEM-DEFAULT");
            pstmt.setInt(3, r.getRating());
            pstmt.setString(4, r.getComment());
            pstmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}