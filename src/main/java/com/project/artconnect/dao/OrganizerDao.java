package com.project.artconnect.dao;
import com.project.artconnect.model.Organizer;
import java.util.List;

public interface OrganizerDao {
    List<Organizer> findAll();
    void save(Organizer organizer);
}