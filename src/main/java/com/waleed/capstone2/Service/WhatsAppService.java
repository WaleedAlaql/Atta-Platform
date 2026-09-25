package com.waleed.capstone2.Service;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {

    // this method is used to send a WhatsApp message to a specific number
    public void sendWhatsAppMessage(String to, String messageBody) {
        try {
            String formattedPhone = formatPhoneNumber(to);

            HttpResponse<String> response = Unirest.post("https://api.ultramsg.com/instance192463/messages/chat")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .field("token", "bjlsrpqe44zvpv55")
                    .field("to", formattedPhone)
                    .field("body", messageBody)
                    .asString();
            System.out.println(response.getBody());

        } catch (UnirestException e) {
            throw new RuntimeException("Failed to send WhatsApp message: " + e.getMessage());
        }
    }

    // helper method to format the phone number
    private String formatPhoneNumber(String phone) {
        if (phone == null) {
            return "";
        }

        // remove any spaces or plus signs if found
        phone = phone.trim().replace("+", "");

        // if the phone number starts with 0 (like 0532188998), remove the 0 and add 966 at the beginning
        if (phone.startsWith("0")) {
            phone = "966" + phone.substring(1);
        }

        return phone;
    }
}