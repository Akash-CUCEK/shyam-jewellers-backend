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
        int otpValue = 100000 + random.nextInt(900000); // 6-digit OTP
        return String.valueOf(otpValue);
    }

    private void sendVerificationEmail(String email, String otp) {
        var subject = "Shyam Jewellers - Email Verification OTP";

        var body = "Dear User,\n\n" +
                "Thank you for registering with Shyam Jewellers.\n\n" +
                "🔐 Your One-Time Password (OTP) is: " + otp + "\n\n" +
                "This OTP is valid for 5 minutes.\n" +
                "Please do not share this OTP with anyone for security reasons.\n\n" +
                "If you did not request this, please ignore this message.\n\n" +
                "Warm regards,\n" +
                "Shyam Jewellers Team";

        emailService.sendEmail(email, subject, body);
    }

    public OtpResponseDTO verifyOTP(OtpRequestDTO otpRequestDTO) {
        logger.debug("Calling the service to validate the OTP");

        var user = userDAO.findUser(otpRequestDTO.getEmail()); // may throw SYMException

        try {
            if (user.getOtpGeneratedTime() == null ||
                    user.getOtpGeneratedTime().plusMinutes(5).isBefore(LocalDateTime.now())) {

                throw new SYMException(
                        HttpStatus.UNAUTHORIZED,
                        SYMErrorType.GENERIC_EXCEPTION,
                        ErrorCodeConstants.ERROR_CODE_AUTHZ_OTP_EXPIRED,
                        "OTP expired",
                        String.format("OTP expired for email: %s", otpRequestDTO.getEmail())
                );
            }

            // Check if OTP is correct
            if (otpRequestDTO.getOtp().equals(user.getOtp())) {
                logger.info("Verified successfully");

                var generateToken = JwtUtil.generateAccessToken(
                        otpRequestDTO.getEmail(),"USER");

                return OtpResponseDTO.builder()
                        .message("Welcome to Shyam Jewellers!")
                        .token(generateToken)
                        .build();
            } else {
                throw new SYMException(
                        HttpStatus.UNAUTHORIZED,
                        SYMErrorType.GENERIC_EXCEPTION,
                        ErrorCodeConstants.ERROR_CODE_AUTHZ_INVALID_OTP,
                        "Invalid OTP",
                        String.format("OTP entered: %s is incorrect for email: %s",
                                otpRequestDTO.getOtp(), otpRequestDTO.getEmail())
                );
            }

        } catch (SYMException e) {
            logger.error("SYMException during OTP verification: {}", e.getMessage());
            throw e;

        } catch (Exception e) {
            logger.error("Unexpected error during OTP verification", e);
            throw new SYMException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    SYMErrorType.GENERIC_EXCEPTION,
                    ErrorCodeConstants.ERROR_CODE_AUTHZ_UNKNOWN,
                    "Something went wrong",
                    e.getMessage()
            );
        }
    }
    public void logout(String token) {
        logger.info("Processing in mapper");
        var expiryInSeconds = (JwtUtil.getExpiry(token).getTime()
                - System.currentTimeMillis()) / 1000;

        tokenBlacklistService.blacklistToken(token,expiryInSeconds);
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
