package com.shyam.service.Imp;

import com.shyam.common.email.EmailService;
import com.shyam.common.exception.domain.SYMErrorType;
import com.shyam.common.exception.domain.SYMException;
import com.shyam.common.jwt.JwtUtil;
import com.shyam.common.redis.service.TokenBlacklistService;
import com.shyam.common.util.MessageSourceUtil;
import com.shyam.constants.ErrorCodeConstants;
import com.shyam.dao.AdminDAO;
import com.shyam.dto.request.*;
import com.shyam.dto.response.*;
import com.shyam.mapper.AdminMapper;
import com.shyam.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

import static com.shyam.constants.MessageConstant.*;

@Service
@RequiredArgsConstructor
public class AdminServiceImp implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImp.class);
    private final AdminMapper adminMapper;
    private final MessageSourceUtil messageSourceUtil;
    private final AdminDAO adminDAO;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenBlacklistService tokenBlacklistService;
    private final EmailService emailService;

    @Override
    public AdminLogInResponseDTO logIn(AdminLogInRequestDTO adminLogInRequestDTO) {
        adminMapper.logIn(adminLogInRequestDTO);
        return adminMapper.mapToAdminLogInMessage(messageSourceUtil
                .getMessage(MESSAGE_CODE_LOGIN_SEND_OTP));
    }
    @Override
    public ForgetPasswordResponseDTO forgetPassword(ForgetPasswordRequestDTO forgetPasswordRequestDTO) {
        var admin = adminDAO.findUserByEmail(forgetPasswordRequestDTO.getEmail());
        var otp = generateOTP();
        admin.setOtp(otp);
        admin.setOtpGeneratedTime(LocalDateTime.now());
        adminDAO.save(admin);
        sendVerificationEmail(forgetPasswordRequestDTO.getEmail(),otp);
        return adminMapper.mapToAdminForgetPasswordMessage(messageSourceUtil
                .getMessage(MESSAGE_CODE_FORGET_ADMIN_PASSWORD_SEND_OTP));
    }

    @Override
    public VerifyForgetPasswordResponseDTO forgetVerifyOtp(
            VerifyAdminRequestDTO verifyAdminRequestDTO) {

        var admin = adminDAO.findUserByEmail(verifyAdminRequestDTO.getEmail());
        try {
            if (admin.getOtpGeneratedTime() == null ||
                    admin.getOtpGeneratedTime()
                            .plusMinutes(5)
                            .isBefore(LocalDateTime.now())) {

                throw new SYMException(
                        HttpStatus.UNAUTHORIZED,
                        SYMErrorType.GENERIC_EXCEPTION,
                        ErrorCodeConstants.ERROR_CODE_AUTHZ_OTP_EXPIRED,
                        "OTP expired",
                        "OTP expired for email: " + verifyAdminRequestDTO.getEmail()
                );
            }

            if (!Objects.equals(verifyAdminRequestDTO.getOtp(), admin.getOtp())) {
                throw new SYMException(
                        HttpStatus.UNAUTHORIZED,
                        SYMErrorType.GENERIC_EXCEPTION,
                        ErrorCodeConstants.ERROR_CODE_AUTHZ_INVALID_OTP,
                        "Invalid OTP",
                        "Invalid OTP for email: " + verifyAdminRequestDTO.getEmail()
                );
            }

            admin.setPassword(passwordEncoder.encode(verifyAdminRequestDTO.getPassword()));
            admin.setOtp(null);
            admin.setOtpGeneratedTime(null);
            adminDAO.save(admin);
            logger.info("Password reset successful for email: {}",
                    verifyAdminRequestDTO.getEmail());
        } catch (SYMException e) {
            logger.error("Password reset SYMException: {}", e.getMessage());
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

        return adminMapper.mapToVerifyForgetOtpInMessage(
                messageSourceUtil.getMessage(MESSAGE_CODE_FORGET_ADMIN_PASSWORD)
        );
    }


    @Override
    public VerifyAdminResponseDTO verifyOtp(VerifyAdminRequestDTO verifyAdminRequestDTO) {
        logger.info("Processing for verifying the otp ");
        return adminMapper.verifyOTP(verifyAdminRequestDTO);
    }

    @Override
    public AdminLogoutResponseDTO logout(AdminLogoutRequestDTO adminLogoutRequestDTO) {
        logger.info("Processing for logout the admin user ");
        var expiryInSeconds = (JwtUtil.getExpiry(adminLogoutRequestDTO.getToken()).getTime()
                - System.currentTimeMillis()) / 1000;
        tokenBlacklistService.blacklistToken(adminLogoutRequestDTO.getToken(),expiryInSeconds);
        return adminMapper.mapToAdminLogoutInMessage(messageSourceUtil
                .getMessage(MESSAGE_CODE_LOG_OUT));
    }

    @Override
    public EditAdminResponseDTO edit(EditAdminRequestDTO editAdminRequestDTO ) {
        adminMapper.edit(editAdminRequestDTO);
        return adminMapper.mapToAdminEditInMessage(messageSourceUtil
                .getMessage(MESSAGE_CODE_EDIT_ADMIN));
    }

    @Override
    public ChangePasswordResponseDTO changePassword(ChangePasswordRequestDTO changePasswordRequestDTO) {
        adminMapper.changePassword(changePasswordRequestDTO);
        return adminMapper.mapToAdminChangePasswordInMessage(messageSourceUtil
                .getMessage(MESSAGE_CODE_CHANGE_PASSWORD_ADMIN));
    }

    @Override
    public RegisterResponseDTO registerAdmin(RegisterRequestDTO registerRequestDTO) {
        adminMapper.registerAdmin(registerRequestDTO);
        return adminMapper.mapToRegisterAdminInMessage(messageSourceUtil
                .getMessage(MESSAGE_CODE_REGISTER_ADMIN));
    }

    @Override
    public EditPhotoResponseDTO offerUpdate(EditPhotoRequestDTO editPhotoRequestDTO) {
        adminMapper.offerUpdate(editPhotoRequestDTO);
        return adminMapper.mapToEditPhotoRequestDTOAdminInMessage(messageSourceUtil
                .getMessage(MESSAGE_CODE_UPDATE_OFFER_ADMIN));

    }

    @Override
    public GetOfferPhotoResponseDTO getOfferPhoto() {
        return adminMapper.getOfferPhoto();
    }

    @Override
    public GetAdminListResponseDTO getAllAdmin() {
        return adminMapper.getAllAdmin();
    }

    @Override
    public DeleteAdminResponseDTO deleteAdmin(DeleteAdminRequestDTO deleteAdmin ) {
        adminMapper.deleteAdmin(deleteAdmin);
        return adminMapper.mapToDeleteAdminInMessage(messageSourceUtil
                .getMessage(MESSAGE_CODE_DELETE_ADMIN));
    }

    @Override
    public GetAdminResponseDTO getAdmin(GetAdminRequestDTO getAdminRequestDTO) {
        logger.info("Processing for getting admin ");
        return adminMapper.getAdmin(getAdminRequestDTO.getEmail());
    }

    @Override
    public GetOfferPhotoResponseDTO getOffer() {
        logger.info("Processing for getting offer photo ");
        return adminMapper.getOffer();
    }

    private String generateOTP() {
        Random random = new Random();
        int otpValue = 100000 + random.nextInt(900000); // 6-digit OTP
        return String.valueOf(otpValue);
    }

    private void sendVerificationEmail(String email, String otp) {
        var subject = "Shyam Jewellers Admin Login - OTP Verification";

        var body = "Dear Admin,\n\n" +
                "We received a request to log in to your Shyam Jewellers Admin account.\n\n" +
                "🔐 Your One-Time Password (OTP) is: " + otp + "\n\n" +
                "This OTP is valid for 5 minutes. Please do not share this code with anyone.\n\n" +
                "If you did not initiate this request, please contact support immediately.\n\n" +
                "Regards,\n" +
                "Shyam Jewellers Security Team";

        emailService.sendEmail(email, subject, body);
    }



}
