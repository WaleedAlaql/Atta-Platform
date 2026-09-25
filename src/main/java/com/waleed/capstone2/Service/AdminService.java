package com.waleed.capstone2.Service;

import com.waleed.capstone2.Entity.Admin;
import com.waleed.capstone2.Entity.Badge;
import com.waleed.capstone2.Entity.Beneficiary;
import com.waleed.capstone2.Repository.AdminRepository;
import com.waleed.capstone2.Repository.BadgeRepository;
import com.waleed.capstone2.Repository.BeneficiaryRepository;
import com.waleed.capstone2.Repository.DonorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final BeneficiaryRepository beneficiaryRepository;
    private final BadgeRepository badgeRepository;
    private final DonorRepository donorRepository;
    private final EmailService emailService;

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public void addAdmin(Admin admin) {
        adminRepository.save(admin);
    }

    public boolean updateAdmin(Integer id, Admin admin) {
        Admin old = adminRepository.findById(id).orElse(null);
        if (old == null) {
            return false;
        }

        old.setName(admin.getName());
        old.setEmail(admin.getEmail());
        old.setPassword(admin.getPassword());
        adminRepository.save(old);
        return true;
    }

    public boolean deleteAdmin(Integer id) {
        if (!adminRepository.existsById(id)) {
            return false;
        }
        adminRepository.deleteById(id);
        return true;
    }

    // verify a beneficiary account
    public String verifyBeneficiary(Integer beneficiaryId) {
        Beneficiary beneficiary = beneficiaryRepository.findById(beneficiaryId).orElse(null);

        if (beneficiary == null) {
            return "Beneficiary not found";
        }

        // check if the account is already verified
        if (beneficiary.isVerified()) {
            return "Beneficiary account is already verified!";
        }

        // verify the account and save it
        beneficiary.setVerified(true);
        beneficiaryRepository.save(beneficiary);

        // send an email to the beneficiary
        if (beneficiary.getEmail() != null) {
            String subject = "Account Verified Successfully";
            String message = "Dear " + beneficiary.getName() + ",\n\n" +
                    "We are glad to inform you that your beneficiary account has been successfully verified by the administration. " +
                    "You can now submit and manage your donation requests smoothly.\n\n" +
                    "Best regards,\n" +
                    "Atta Team";

            emailService.sendEmail(beneficiary.getEmail(), subject, message);
        }

        return "Beneficiary verified successfully";
    }

    // give a badge to a donor
    public boolean giveBadgeToDonor(Integer donorId, String badgeTitle) {
        if (!donorRepository.existsById(donorId)) {
            return false; // donor not found
        }

        Badge badge = new Badge();
        badge.setDonorId(donorId);

        badge.setTitle(badgeTitle);

        badgeRepository.save(badge);
        return true;
    }
}