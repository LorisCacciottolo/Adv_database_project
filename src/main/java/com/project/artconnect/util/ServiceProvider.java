package com.project.artconnect.util;

import com.project.artconnect.persistence.*;
import com.project.artconnect.service.*;
import com.project.artconnect.service.impl.*;

public class ServiceProvider {
    private static final ArtistService artistService;
    private static final ArtworkService artworkService;
    private static final GalleryService galleryService;
    private static final WorkshopService workshopService;
    private static final CommunityService communityService;

    static {
        JdbcArtistDao artistDao = new JdbcArtistDao();
        JdbcDisciplineDao disciplineDao = new JdbcDisciplineDao();
        JdbcArtworkDao artworkDao = new JdbcArtworkDao();
        JdbcGalleryDao galleryDao = new JdbcGalleryDao();
        JdbcExhibitionDao exhibitionDao = new JdbcExhibitionDao();
        JdbcWorkshopDao workshopDao = new JdbcWorkshopDao();
        JdbcBookingDao bookingDao = new JdbcBookingDao();
        JdbcCommunityMemberDao memberDao = new JdbcCommunityMemberDao();
        JdbcReviewDao reviewDao = new JdbcReviewDao();

        artistService = new JdbcArtistService(artistDao, disciplineDao);
        artworkService = new JdbcArtworkService(artworkDao);
        galleryService = new JdbcGalleryService(galleryDao, exhibitionDao);
        workshopService = new JdbcWorkshopService(workshopDao, bookingDao);
        communityService = new JdbcCommunityService(memberDao, reviewDao);
    }

    public static ArtistService getArtistService() {
        return artistService;
    }

    public static ArtworkService getArtworkService() {
        return artworkService;
    }

    public static GalleryService getGalleryService() {
        return galleryService;
    }

    public static WorkshopService getWorkshopService() {
        return workshopService;
    }

    public static CommunityService getCommunityService() {
        return communityService;
    }
}