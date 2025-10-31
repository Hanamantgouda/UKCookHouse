package com.project.UKCookHouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import jakarta.mail.internet.MimeMessage; // ✅ use jakarta for Spring Boot 3+

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ForgotPasswordController {

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/send-otp")
    public Map<String, Object> sendOtpEmail(@RequestBody Map<String, String> requestData) {
        Map<String, Object> response = new HashMap<>();

        try {
            String email = requestData.get("email");
            String otp = requestData.get("otp");

            if (email == null || otp == null || email.isEmpty() || otp.isEmpty()) {
                response.put("status", "error");
                response.put("message", "Email and OTP are required!");
                return response;
            }

            String subject = "🔐 Your UK Cook House Password Reset OTP";
            String htmlBody = """
<html>
  <body style='font-family: Poppins, sans-serif; background-color: #fdf6f2; padding: 20px; margin: 0;'>
    <div style='max-width: 600px; margin: auto; background-color: #ffffff; border-radius: 10px; box-shadow: 0 4px 12px rgba(0,0,0,0.08); padding: 30px; text-align: center;'>
      
      <h2 style='color: #ff5722; margin-bottom: 10px;'>UK Cook House</h2>
      
      <p style='font-size: 16px; color: #333; margin-bottom: 20px;'>
        Use the following OTP to reset your password:
      </p>
      
      <div style='font-size: 32px; font-weight: bold; color: #ff5722; letter-spacing: 3px; margin: 20px 0;'>
        %s
      </div>
      
      <p style='font-size: 15px; color: #555; margin-bottom: 25px;'>
        This OTP is valid for <strong>5 minutes</strong>. Do not share it with anyone.
      </p>
      
      <hr style='border: none; border-top: 1px solid #f0f0f0; margin: 25px 0;'>
      
      <p style='font-size: 13px; color: #888; margin-top: 10px;'>
        If you didn’t request this, please ignore this email.<br>
        © 2025 <span style='color: #ff5722;'>UK Cook House</span>. All rights reserved.
      </p>
    </div>
  </body>
</html>
""".formatted(otp);


            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            helper.setFrom("yourgmail@gmail.com");

            mailSender.send(message);

            response.put("status", "success");
            response.put("message", "OTP sent successfully to " + email);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Failed to send OTP: " + e.getMessage());
        }

        return response;
    }
}
