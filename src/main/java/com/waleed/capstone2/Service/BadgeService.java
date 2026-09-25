package com.waleed.capstone2.Service;

import com.waleed.capstone2.Entity.Badge;
import com.waleed.capstone2.Repository.BadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BadgeService {

    private final BadgeRepository badgeRepository;

    public List<Badge> getAllBadges() {
        return badgeRepository.findAll();
    }

    public List<Badge> getBadgesByDonor(Integer donorId) {
        return badgeRepository.findByDonorId(donorId);
    }

    public boolean deleteBadge(Integer id) {
        if (!badgeRepository.existsById(id)) {
            return false;
        }
        badgeRepository.deleteById(id);
        return true;
    }
}