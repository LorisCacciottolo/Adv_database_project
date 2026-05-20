package com.project.artconnect.persistence;

import com.project.artconnect.dao.ArtworkDao;
import com.project.artconnect.model.Artwork;
import com.project.artconnect.model.Artist;
import com.project.artconnect.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcArtworkDao implements ArtworkDao {

    @Override
    public List<Artwork> findAll() {
        List<Artwork> artworks = new ArrayList<>();
        String sql = "SELECT aw.*, ar.name AS artist_name, AVG(r.rating) AS average_rating " +
                "FROM Artwork aw " +
                "LEFT JOIN Artist ar ON aw.artist_id = ar.artist_id " +
                "LEFT JOIN Review r ON aw.artwork_id = r.artwork_id " +
                "GROUP BY aw.artwork_id";

        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Artwork a = new Artwork();
                a.setTitle(rs.getString("title"));
                a.setPrice(rs.getDouble("price"));
                a.setType(rs.getString("type"));

                Artist artist = new Artist();
                artist.setName(rs.getString("artist_name"));
                a.setArtist(artist);

                String dbStatus = rs.getString("status");
                if (dbStatus != null) {
                    try {
                        a.setStatus(Artwork.Status.valueOf(dbStatus.toUpperCase()));
                    } catch (IllegalArgumentException e) {
                        a.setStatus(Artwork.Status.FOR_SALE);
                    }
                }

                double avg = rs.getDouble("average_rating");
                if (rs.wasNull()) {
                    a.setAverageRating(null);
                } else {
                    a.setAverageRating(Math.round(avg * 10.0) / 10.0);
                }

                artworks.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artworks;
    }

    @Override
    public List<Artwork> findByArtistName(String artistName) {
        List<Artwork> artworks = new ArrayList<>();
        String sql = "SELECT aw.*, ar.name AS artist_name FROM Artwork aw " +
                "JOIN Artist ar ON aw.artist_id = ar.artist_id " +
                "WHERE ar.name = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, artistName);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Artwork a = new Artwork();
                    a.setTitle(rs.getString("title"));
                    a.setPrice(rs.getDouble("price"));
                    a.setType(rs.getString("type"));

                    Artist artist = new Artist();
                    artist.setName(rs.getString("artist_name"));
                    a.setArtist(artist);

                    String dbStatus = rs.getString("status");
                    if (dbStatus != null) {
                        try {
                            a.setStatus(Artwork.Status.valueOf(dbStatus.toUpperCase()));
                        } catch (IllegalArgumentException e) {
                            a.setStatus(Artwork.Status.FOR_SALE);
                        }
                    }
                    artworks.add(a);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artworks;
    }

    @Override
    public void save(Artwork artwork) {
        String sql = "INSERT INTO Artwork (artwork_id, title, creationYear, type, medium, dimensions, price, status, artist_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "AW-" + System.currentTimeMillis());
            pstmt.setString(2, artwork.getTitle());
            pstmt.setInt(3, artwork.getCreationYear() != null ? artwork.getCreationYear() : 2026);
            pstmt.setString(4, artwork.getType());
            pstmt.setString(5, artwork.getMedium());
            pstmt.setString(6, artwork.getDimensions());
            pstmt.setDouble(7, artwork.getPrice());
            pstmt.setString(8, artwork.getStatus() != null ? artwork.getStatus().name() : "FOR_SALE");
            pstmt.setString(9, "ART-01");

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Artwork artwork) {
        String sql = "UPDATE Artwork SET price = ?, status = ? WHERE title = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, artwork.getPrice());
            pstmt.setString(2, artwork.getStatus() != null ? artwork.getStatus().name() : "FOR_SALE");
            pstmt.setString(3, artwork.getTitle());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String title) {
        String sql = "DELETE FROM Artwork WHERE title = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, title);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}