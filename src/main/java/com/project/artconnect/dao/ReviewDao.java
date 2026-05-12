package com.project.artconnect.dao;
import com.project.artconnect.model.Review;
import java.util.List;

public interface ReviewDao {
    List<Review> findAll();
    void save(Review review);
}