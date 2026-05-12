package com.project.artconnect.persistence;

import com.project.artconnect.dao.ArtistDao;
import com.project.artconnect.model.Artist;
import com.project.artconnect.config.DatabaseConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcArtistDao implements ArtistDao {

    @Override
    public List<Artist> findAll() {
        List<Artist> artists = new ArrayList<>();
        String sql = "SELECT * FROM Artist";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Artist a = new Artist();
                a.setName(rs.getString("name"));
                a.setBio(rs.getString("bio"));
                a.setBirthYear(rs.getInt("birthYear"));
                a.setContactEmail(rs.getString("contactEmail"));
                a.setPhone(rs.getString("phone"));
                a.setCity(rs.getString("city"));
                a.setWebsite(rs.getString("website"));
                a.setSocialMedia(rs.getString("socialMedia"));
                a.setActive(rs.getBoolean("isActive"));

                artists.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artists;
    }

    @Override
    public void save(Artist artist) {
        // We include all attributes of the Artist table
        String sql = "INSERT INTO Artist (artist_id, name, bio, birthYear, contactEmail, phone, city, website, socialMedia, isActive) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Generate a unique ID
            pstmt.setString(1, "ART-" + System.currentTimeMillis());

            // Map the existing attributes from the Artist object
            pstmt.setString(2, artist.getName());
            pstmt.setString(3, artist.getBio());
            pstmt.setInt(4, artist.getBirthYear());
            pstmt.setString(5, artist.getContactEmail());
            pstmt.setString(6, artist.getPhone());
            pstmt.setString(7, artist.getCity());
            pstmt.setString(8, artist.getWebsite());
            pstmt.setString(9, artist.getSocialMedia());
            pstmt.setBoolean(10, artist.isActive());

            pstmt.executeUpdate();
            System.out.println("Artist saved successfully to the database");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Artist artist) {
        String sql = "UPDATE Artist SET bio = ?, birthYear = ?, contactEmail = ?, phone = ?, city = ?, website = ?, socialMedia = ?, isActive = ? WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, artist.getBio());
            pstmt.setInt(2, artist.getBirthYear());
            pstmt.setString(3, artist.getContactEmail());
            pstmt.setString(4, artist.getPhone());
            pstmt.setString(5, artist.getCity());
            pstmt.setString(6, artist.getWebsite());
            pstmt.setString(7, artist.getSocialMedia());
            pstmt.setBoolean(8, artist.isActive());
            pstmt.setString(9, artist.getName());

            pstmt.executeUpdate();
            System.out.println("Artist updated successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String artistName) {
        String sql = "DELETE FROM Artist WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, artistName);
            pstmt.executeUpdate();
            System.out.println("Artist deleted successfully: " + artistName);

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    @Override
    public List<Artist> findByCity(String city) {
        List<Artist> artists = new ArrayList<>();
        String sql = "SELECT * FROM Artist WHERE city = ?";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, city);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Artist a = new Artist();
                a.setName(rs.getString("name"));
                a.setCity(rs.getString("city"));
                artists.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artists;
    }
}