package com.shyam.mapper;

import com.shyam.common.constants.Role;
import com.shyam.common.email.EmailService;
import com.shyam.common.exception.domain.SYMErrorType;
import com.shyam.common.exception.domain.SYMException;
import com.shyam.common.jwt.JwtUtil;
import com.shyam.common.redis.service.TokenBlacklistService;
import com.shyam.common.util.MapperUtil;
import com.shyam.constants.ErrorCodeConstants;
import com.shyam.dao.AdminDAO;
import com.shyam.dto.request.*;
import com.shyam.dto.response.*;
import com.shyam.entity.AdminUsers;
import com.shyam.entity.OfferPhoto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AdminMapper {

    private static final Logger logger = LoggerFactory.getLogger(AdminMapper.class);
    private final AdminDAO adminDAO;
    private final TokenBlacklistService tokenBlacklistService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;


    public void logIn(AdminLogInRequestDTO adminLogInRequestDTO) {
        logger.info("Received request in mapping for login");
        logger.info("OTP sent successfully");
    }

    public void forgetPassword(ForgetPasswordRequestDTO forgetPasswordRequestDTO) {

    }
    public ForgetPasswordResponseDTO mapToAdminForgetPasswordMessage(String message) {
        return ForgetPasswordResponseDTO.builder()
                .response(message)
                .build();
    }

    public VerifyForgetPasswordResponseDTO mapToVerifyForgetOtpInMessage(String message) {
        return VerifyForgetPasswordResponseDTO.builder()
                .response(message)
                .build();
    }
    private String generateOTP() {
        Random random = new Random();
        int otpValue = 100000 + random.nextInt(900000); // 6-digit OTP
        return String.valueOf(otpValue);
    }

    public AdminLogInResponseDTO mapToAdminLogInMessage(String message) {
        return AdminLogInResponseDTO.builder()
                .message(message)
                .build();
    }
    public AdminLogoutResponseDTO mapToAdminLogoutInMessage(String message) {
        return AdminLogoutResponseDTO.builder()
                .message(message)
                .build();
    }

    public EditAdminResponseDTO mapToAdminEditInMessage(String message) {
        return EditAdminResponseDTO.builder()
                .response(message)
                .build();
    }

    public void edit(EditAdminRequestDTO editAdminRequestDTO) {
        logger.info("Processing in mapper ");
        var adminUser = adminDAO.findUserByEmail(editAdminRequestDTO.getEmail());
        adminUser.setName(editAdminRequestDTO.getName());
        adminUser.setPhoneNumber(editAdminRequestDTO.getPhoneNumber());
        adminUser.setImageUrl(editAdminRequestDTO.getImageUrl());
        adminDAO.save(adminUser);
    }

    public void changePassword(ChangePasswordRequestDTO changePasswordRequestDTO) {
        logger.info("Processing change password in mapper");

        var adminUser = adminDAO.findUserByEmail(changePasswordRequestDTO.getEmail());
        if (!passwordEncoder.matches(changePasswordRequestDTO.getPassword(), adminUser.getPassword())) {
            throw new SYMException(
                    HttpStatus.UNAUTHORIZED,
                    SYMErrorType.GENERIC_EXCEPTION,
                    ErrorCodeConstants.ERROR_CODE_PASSWORD_NOT_MATCHING,
                    "Please enter correct password !",
                    "Entered password is wrong, please enter correct password"
            );
        }

        if (passwordEncoder.matches(changePasswordRequestDTO.getNewPassword(), adminUser.getPassword())) {
            throw new SYMException(
                    HttpStatus.BAD_REQUEST,
                    SYMErrorType.GENERIC_EXCEPTION,
                    ErrorCodeConstants.ERROR_CODE_SAME_AS_OLD_PASSWORD,
                    "New password cannot be same as the old password!",
                    "Same password used while attempting to change password"
            );
        }

        adminUser.setPassword(passwordEncoder.encode(changePasswordRequestDTO.getNewPassword()));
        adminDAO.save(adminUser);
    }

    public ChangePasswordResponseDTO mapToAdminChangePasswordInMessage(String message) {
        return ChangePasswordResponseDTO.builder()
                .response(message)
                .build();
    }

    public void registerAdmin(RegisterRequestDTO registerRequestDTO ) {
        if (adminDAO.findByEmail(registerRequestDTO.getEmail()).isPresent()) {
            throw new SYMException(
                    HttpStatus.CONFLICT,
                    SYMErrorType.GENERIC_EXCEPTION,
                    ErrorCodeConstants.ERROR_CODE_EMAIL_ALREADY_EXISTS,
                    "Email already registered!",
                    "Attempted to register with existing email: " + registerRequestDTO.getEmail()
            );
        }

        AdminUsers newUser = new AdminUsers();
       newUser.setName(registerRequestDTO.getName());
       newUser.setEmail(registerRequestDTO.getEmail());
       newUser.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
       newUser.setPhoneNumber(registerRequestDTO.getPhoneNumber());
       newUser.setRole(MapperUtil.parseRole("ADMIN"));

        adminDAO.save(newUser);
        var subject = "Welcome to Shyam Jewellers Admin Portal";
        var body = "Hello " + registerRequestDTO.getName() + ",\n\n" +
                "Welcome to Shyam Jewellers Admin Panel.\n" +
                "Your admin account has been created successfully.\n\n" +
                "Login Email: " + registerRequestDTO.getEmail() + "\n\n" +
                "Your temporary password will be shared with you personally.\n" +
                "Please log in and change your password after the first login.\n\n" +
                "Regards,\nTeam Shyam Jewellers";

        emailService.sendEmail(registerRequestDTO.getEmail(), subject, body);
    }

    public RegisterResponseDTO mapToRegisterAdminInMessage(String message) {
        return RegisterResponseDTO.builder()
                .response(message)
                .build();
    }

    public void offerUpdate(EditPhotoRequestDTO editPhotoRequestDTO ) {
        OfferPhoto offer = adminDAO.getLatestOfferPhoto();
        if (offer == null) {
            offer = new OfferPhoto();
        }

        switch (editPhotoRequestDTO.getPosition()) {
            case 1 -> offer.setImgUrl1(editPhotoRequestDTO.getImgUrl());
            case 2 -> offer.setImgUrl2(editPhotoRequestDTO.getImgUrl());
            case 3 -> offer.setImgUrl3(editPhotoRequestDTO.getImgUrl());
            case 4 -> offer.setImgUrl4(editPhotoRequestDTO.getImgUrl());
            case 5 -> offer.setImgUrl5(editPhotoRequestDTO.getImgUrl());
            default -> throw new IllegalArgumentException("Invalid image position: " + editPhotoRequestDTO.getPosition());
        }
        offer.setCreatedAt(LocalDateTime.now());
        offer.setUpdatedAt(LocalDateTime.now());


        adminDAO.saveOffer(offer);
    }

    public EditPhotoResponseDTO mapToEditPhotoRequestDTOAdminInMessage(String message) {
        return EditPhotoResponseDTO.builder()
                .response(message)
                .build();
    }

    public GetOfferPhotoResponseDTO getOfferPhoto() {
        var offer = adminDAO.getLatestOfferPhoto();
        if (offer == null) {
            logger.info("No offer photo found in DB.");
            return GetOfferPhotoResponseDTO.builder()
                    .imgUrl1(null)
                    .imgUrl2(null)
                    .imgUrl3(null)
                    .imgUrl4(null)
                    .imgUrl5(null)
                    .build();
        }
        return GetOfferPhotoResponseDTO.builder()
                .imgUrl1(offer.getImgUrl1())
                .imgUrl2(offer.getImgUrl2())
                .imgUrl3(offer.getImgUrl3())
                .imgUrl4(offer.getImgUrl4())
                .imgUrl5(offer.getImgUrl5())
                .build();
    }

    public GetAdminListResponseDTO getAllAdmin() {
        List<AdminUsers> admins = adminDAO.findByRole(Role.ADMIN);
        List<GetAllAdminResponseDTO> responseDTOList = admins.stream()
                .map(this::mapToGetAllAdminDTO)
                .collect(Collectors.toList());

        return GetAdminListResponseDTO.builder()
                .getAllAdminResponseDTOList(responseDTOList)
                .build();
    }


    private GetAllAdminResponseDTO mapToGetAllAdminDTO(AdminUsers user) {
        return GetAllAdminResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public void deleteAdmin(DeleteAdminRequestDTO deleteAdmin) {
        var admin = adminDAO.findUserByEmail(deleteAdmin.getEmail());
        adminDAO.delete(admin);
    }

    public DeleteAdminResponseDTO mapToDeleteAdminInMessage(String message) {
        return DeleteAdminResponseDTO.builder()
                .response(message)
                .build();
    }

    public GetAdminResponseDTO getAdmin(String email) {
        logger.debug("Mapping for getting admin");
        var admin = adminDAO.findUserByEmail(email);
        return GetAdminResponseDTO.builder()
                .name(admin.getName())
                .phoneNumber(admin.getPhoneNumber())
                .imageUrl(admin.getImageUrl())
                .build();
    }

    public GetOfferPhotoResponseDTO getOffer() {
        logger.debug("Mapping for getting offer photo");
        var photos = adminDAO.getLatestOfferPhoto();
        return GetOfferPhotoResponseDTO.builder()
                .imgUrl1(photos.getImgUrl1())
                .imgUrl2(photos.getImgUrl2())
                .imgUrl3(photos.getImgUrl3())
                .imgUrl4(photos.getImgUrl4())
                .imgUrl5(photos.getImgUrl5())
                .build();
    }
}
