package com.project.artconnect.persistence;

import com.project.artconnect.dao.BookingDao;
import com.project.artconnect.model.Booking;
import com.project.artconnect.model.Workshop;
import com.project.artconnect.model.CommunityMember;
import com.project.artconnect.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcBookingDao implements BookingDao {

    @Override
    public List<Booking> findAll() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM Booking";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Booking b = new Booking();

                Date sqlDate = rs.getDate("bookingDate");
                if (sqlDate != null) {
                    b.setBookingDate(sqlDate.toLocalDate().atStartOfDay());
                }

                b.setPaymentStatus(rs.getString("paymentStatus"));

                b.setWorkshop(new Workshop());
                b.setMember(new CommunityMember());

                list.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void save(Booking b) {
        String sql = "INSERT INTO Booking (workshop_id, member_id, bookingDate, paymentStatus) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD)) {

            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, "WK-DEFAULT");
                pstmt.setString(2, "MEM-DEFAULT");

                if (b.getBookingDate() != null) {
                    pstmt.setDate(3, java.sql.Date.valueOf(b.getBookingDate().toLocalDate()));
                } else {
                    pstmt.setDate(3, new java.sql.Date(System.currentTimeMillis()));
                }

                pstmt.setString(4, b.getPaymentStatus());

                pstmt.executeUpdate();

                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Error during booking, transaction stopped.");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}