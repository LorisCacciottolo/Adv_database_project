package com.project.artconnect.service.impl;

import com.project.artconnect.dao.WorkshopDao;
import com.project.artconnect.dao.BookingDao;
import com.project.artconnect.model.Workshop;
import com.project.artconnect.model.Booking;
import com.project.artconnect.model.CommunityMember;
import com.project.artconnect.service.WorkshopService;
import java.util.List;
import java.util.Optional;

public class JdbcWorkshopService implements WorkshopService {
    private final WorkshopDao workshopDao;
    private final BookingDao bookingDao;

    public JdbcWorkshopService(WorkshopDao workshopDao, BookingDao bookingDao) {
        this.workshopDao = workshopDao;
        this.bookingDao = bookingDao;
    }

    @Override
    public List<Workshop> getAllWorkshops() { return workshopDao.findAll(); }

    @Override
    public Optional<Workshop> getWorkshopByTitle(String title) {
        return workshopDao.findAll().stream().filter(w -> w.getTitle().equalsIgnoreCase(title)).findFirst();
    }

    @Override
    public void bookWorkshop(Workshop workshop, CommunityMember member) {
        Booking booking = new Booking(workshop, member);
        bookingDao.save(booking);
    }

    @Override
    public List<Booking> getBookingsByMember(CommunityMember member) {
        return bookingDao.findAll();
    }
}