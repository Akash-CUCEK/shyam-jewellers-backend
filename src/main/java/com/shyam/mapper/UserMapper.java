package com.shyam.mapper;

import com.shyam.common.email.EmailService;
import com.shyam.common.exception.domain.SYMErrorType;
import com.shyam.common.exception.domain.SYMException;
import com.shyam.common.jwt.JwtUtil;
import com.shyam.common.redis.service.TokenBlacklistService;
import com.shyam.constants.ErrorCodeConstants;
import com.shyam.dao.UserDAO;
import com.shyam.dto.request.OtpRequestDTO;
import com.shyam.dto.request.logInRequestDTO;
import com.shyam.dto.response.LogInResponseDTO;
import com.shyam.dto.response.LogoutResponseDTO;
import com.shyam.dto.response.OtpResponseDTO;
import com.shyam.entity.Users;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private static final Logger logger = LoggerFactory.getLogger(UserMapper.class);
    private final UserDAO userDAO;
    private final EmailService emailService;
    private final TokenBlacklistService tokenBlacklistService;

    public void logInMapper(logInRequestDTO dto) {
        logger.info("mapper");
        logger.debug("Calling the logIn feature to send mail");
        Optional<Users> existingUserOpt = userDAO.findOnlyUser(dto.getEmail());

        var otp = generateOTP();
        if (existingUserOpt.isPresent()) {
            Users existingUser = existingUserOpt.get();
            existingUser.setOtp(otp);
            existingUser.setOtpGeneratedTime(LocalDateTime.now());
            Users savedUser = userDAO.save(existingUser);
            sendVerificationEmail(savedUser.getEmail(), otp);
        } else {
            Users newUser = Users.builder()
                    .email(dto.getEmail())
                    .otp(otp)
                    .otpGeneratedTime(LocalDateTime.now())
                    .build();
            Users savedUser = userDAO.save(newUser);
            sendVerificationEmail(savedUser.getEmail(), otp);
        }
    }

    private String generateOTP() {
        Random random = new Random();
        int otpValue = 100000 + random.nextInt(900000);
        return String.valueOf(otpValue);
    }

    private void sendVerificationEmail(String email, String otp) {
        var subject = "Shyam Jewellers - Email Verification OTP";

        var body =
                "Dear User,\n\n" +
                        "We received a request to sign in to your Shyam Jewellers account.\n\n" +
                        "━━━━━━━━━━━━━━━━━━━━\n" +
                        "🔐 Your One-Time Password (OTP)\n" +
                        otp + "\n" +
                        "━━━━━━━━━━━━━━━━━━━━\n\n" +
                        "⏱ This OTP is valid for 5 minutes.\n" +
                        "⚠ Please do not share this code with anyone.\n\n" +
                        "If you did not request this login, please ignore this message or contact our support team.\n\n" +
                        "Regards,\n" +
                        "Shyam Jewellers\n" +
                        "Security Team";


        emailService.sendEmail(email, subject, body);
    }

    public LogInResponseDTO mapToUserLogInMessage(String successMessage){
        return LogInResponseDTO.builder()
                .message(successMessage)
                .build();
    }

    public LogoutResponseDTO mapToUserLogoutInMessage(String successMessage) {
        return LogoutResponseDTO.builder()
                .message(successMessage)
                .build();
    }
}
