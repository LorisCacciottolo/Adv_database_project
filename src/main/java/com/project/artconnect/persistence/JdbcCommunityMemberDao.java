package com.project.artconnect.persistence;

import com.project.artconnect.dao.CommunityMemberDao;
import com.project.artconnect.model.CommunityMember;
import com.project.artconnect.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcCommunityMemberDao implements CommunityMemberDao {

    @Override
    public Optional<CommunityMember> findById(Long id) {
        String sql = "SELECT * FROM CommunityMember WHERE member_id = ?";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, String.valueOf(id));
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                CommunityMember m = new CommunityMember();
                m.setName(rs.getString("name"));
                m.setEmail(rs.getString("email"));
                m.setCity(rs.getString("city"));

                return Optional.of(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<CommunityMember> findAll() {
        List<CommunityMember> members = new ArrayList<>();

        String sql = "SELECT c.*, GROUP_CONCAT(w.title SEPARATOR ', ') AS enrolled_workshops " +
                "FROM CommunityMember c " +
                "LEFT JOIN Booking b ON c.member_id = b.member_id AND b.paymentStatus != 'Annulé' " +
                "LEFT JOIN Workshop w ON b.workshop_id = w.workshop_id " +
                "GROUP BY c.member_id";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                CommunityMember m = new CommunityMember();

                m.setName(rs.getString("name"));
                m.setEmail(rs.getString("email"));
                m.setCity(rs.getString("city"));
                m.setMembershipType(rs.getString("membershipType"));
                m.setEnrolledWorkshops(rs.getString("enrolled_workshops"));

                members.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }


    public void save(CommunityMember member) {
        String sql = "INSERT INTO CommunityMember (member_id, name, email, city) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "MEM-" + System.currentTimeMillis());
            pstmt.setString(2, member.getName());
            pstmt.setString(3, member.getEmail());
            pstmt.setString(4, member.getCity());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(CommunityMember member) {
        String sql = "UPDATE CommunityMember SET email = ?, city = ? WHERE name = ?";
        try (Connection conn = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, member.getEmail());
            pstmt.setString(2, member.getCity());
            pstmt.setString(3, member.getName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String name) {
        String sql = "DELETE FROM CommunityMember WHERE name = ?";
        try (Connection conn = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}