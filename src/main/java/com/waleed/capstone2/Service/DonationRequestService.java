package com.waleed.capstone2.Service;

import com.waleed.capstone2.Entity.Beneficiary;
import com.waleed.capstone2.Entity.DonationItem;
import com.waleed.capstone2.Entity.DonationRequest;
import com.waleed.capstone2.Repository.BeneficiaryRepository;
import com.waleed.capstone2.Repository.DonationItemRepository;
import com.waleed.capstone2.Repository.DonationRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DonationRequestService {

    private final DonationRequestRepository donationRequestRepository;
    private final DonationItemRepository donationItemRepository;
    private final BeneficiaryRepository beneficiaryRepository;
    private final WhatsAppService whatsAppService;

    public List<DonationRequest> getAllRequests() {
        return donationRequestRepository.findAll();
    }

    public String addRequest(DonationRequest request) {
        // check if the item is in the system
        DonationItem item = donationItemRepository.findById(request.getItemId()).orElse(null);
        if (item == null) {
            return "Item not found";
        }

        // check if the beneficiary is in the system
        Beneficiary beneficiary = beneficiaryRepository.findById(request.getBeneficiaryId()).orElse(null);
        if (beneficiary == null) {
            return "Beneficiary not found";
        }

        // check if the beneficiary is verified by the admin
        if (!beneficiary.isVerified()) {
            return "Beneficiary account is not verified by admin yet";
        }

        // set the request date and status
        request.setRequestDate(LocalDate.now());
        request.setStatus("CREATED");

        donationRequestRepository.save(request);

        // send a whatsapp message to the beneficiary
        String message = "Hello " + beneficiary.getName() + ", your donation request for (" + item.getTitle() + ") has been *CREATED* successfully! We will keep you updated as the status changes.";
        whatsAppService.sendWhatsAppMessage(beneficiary.getPhone(), message);

        return "Success";
    }

        // update the request status and send a whatsapp message to the beneficiary
        public boolean updateRequestStatus(Integer requestId) {
            DonationRequest request = donationRequestRepository.findById(requestId).orElse(null);
            if (request == null) {
                return false;
            }

            String currentStatus = request.getStatus();
            String nextStatus;

            if ("CREATED".equalsIgnoreCase(currentStatus)) {
                nextStatus = "IN_PROGRESS";
            } else if ("IN_PROGRESS".equalsIgnoreCase(currentStatus)) {
                nextStatus = "DELIVERED";
            } else if ("DELIVERED".equalsIgnoreCase(currentStatus)) {
                return false; // the request is already completed and cannot be changed
            } else {
                nextStatus = "CREATED"; // default status for safety
            }

            request.setStatus(nextStatus);
            donationRequestRepository.save(request);

            // get the beneficiary's information and send a whatsapp message with the new status
            Beneficiary beneficiary = beneficiaryRepository.findById(request.getBeneficiaryId()).orElse(null);
            if (beneficiary != null && beneficiary.getPhone() != null) {
                String message = "Hello " + beneficiary.getName() + ", your donation request status has been updated to: *" + nextStatus + "*.";
                whatsAppService.sendWhatsAppMessage(beneficiary.getPhone(), message);
            }

            return true;
        }

    public boolean deleteRequest(Integer id) {
        DonationRequest request = donationRequestRepository.findById(id).orElse(null);
        if (request == null) {
            return false;
        }

        donationRequestRepository.deleteById(id);
        return true;
    }
}