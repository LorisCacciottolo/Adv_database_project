package com.project.artconnect.service.impl;

import com.project.artconnect.dao.CommunityMemberDao;
import com.project.artconnect.dao.ReviewDao;
import com.project.artconnect.model.CommunityMember;
import com.project.artconnect.model.Review;
import com.project.artconnect.service.CommunityService;
import java.util.List;
import java.util.Optional;

public class JdbcCommunityService implements CommunityService {
    private final CommunityMemberDao memberDao;
    private final ReviewDao reviewDao;

    public JdbcCommunityService(CommunityMemberDao memberDao, ReviewDao reviewDao) {
        this.memberDao = memberDao;
        this.reviewDao = reviewDao;
    }

    @Override
    public List<CommunityMember> getAllMembers() { return memberDao.findAll(); }

    @Override
    public Optional<CommunityMember> getMemberByName(String name) {
        return memberDao.findAll().stream().filter(m -> m.getName().equalsIgnoreCase(name)).findFirst();
    }

    @Override
    public List<Review> getReviewsByMember(CommunityMember member) {
        return reviewDao.findAll();
    }
}