package com.project.artconnect.dao;
import com.project.artconnect.model.Booking;
import java.util.List;

public interface BookingDao {
    List<Booking> findAll();
    void save(Booking booking);
}