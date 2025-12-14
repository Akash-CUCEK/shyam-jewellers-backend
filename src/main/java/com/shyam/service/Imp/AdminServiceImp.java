package com.shyam.service.Imp;

import com.shyam.common.util.MessageSourceUtil;
import com.shyam.dto.request.*;
import com.shyam.dto.response.*;
import com.shyam.mapper.AdminMapper;
import com.shyam.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.shyam.constants.MessageConstant.*;

@Service
@RequiredArgsConstructor
public class AdminServiceImp implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImp.class);
    private final AdminMapper adminMapper;
    private final MessageSourceUtil messageSourceUtil;

    @Override
    public AdminLogInResponseDTO logIn(AdminLogInRequestDTO adminLogInRequestDTO, String requestId) {
        adminMapper.logIn(adminLogInRequestDTO);
        return adminMapper.mapToAdminLogInMessage(messageSourceUtil
                .getMessage(MESSAGE_CODE_LOGIN_SEND_OTP));
    }
    @Override
    public ForgetPasswordResponseDTO forgetPassword(ForgetPasswordRequestDTO forgetPasswordRequestDTO, String requestId) {
        adminMapper.forgetPassword(forgetPasswordRequestDTO);
        return adminMapper.mapToAdminForgetPasswordMessage(messageSourceUtil
                .getMessage(MESSAGE_CODE_FORGET_ADMIN_PASSWORD_SEND_OTP));
    }

//    @Override
//    public VerifyForgetPasswordResponseDTO forgetVerifyOtp(VerifyAdminRequestDTO verifyAdminRequestDTO, String requestId) {
//        adminMapper.forgetVerifyOtp(verifyAdminRequestDTO);
//        return adminMapper.mapToVerifyForgetOtpInMessage(messageSourceUtil
//                .getMessage(MESSAGE_CODE_FORGET_ADMIN_PASSWORD));
//    }

    @Override
    public VerifyAdminResponseDTO verifyOtp(VerifyAdminRequestDTO verifyAdminRequestDTO, String requestId) {
        logger.info("Processing the requestId : {} for verifying the otp ",requestId);
        return adminMapper.verifyOTP(verifyAdminRequestDTO);
    }

    @Override
    public AdminLogoutResponseDTO logout(AdminLogoutRequestDTO adminLogoutRequestDTO, String requestId) {
        logger.info("Processing the requestId : {} for logout the admin user ",requestId);
        adminMapper.logout(adminLogoutRequestDTO.getToken());
        logger.info("done");
        return adminMapper.mapToAdminLogoutInMessage(messageSourceUtil
                .getMessage(MESSAGE_CODE_LOG_OUT));
    }

    @Override
    public EditAdminResponseDTO edit(EditAdminRequestDTO editAdminRequestDTO, String requestId) {
        adminMapper.edit(editAdminRequestDTO,requestId);
        return adminMapper.mapToAdminEditInMessage(messageSourceUtil
                .getMessage(MESSAGE_CODE_EDIT_ADMIN));
    }

    @Override
    public ChangePasswordResponseDTO changePassword(ChangePasswordRequestDTO changePasswordRequestDTO, String requestId) {
        adminMapper.changePassword(changePasswordRequestDTO,requestId);
        return adminMapper.mapToAdminChangePasswordInMessage(messageSourceUtil
                .getMessage(MESSAGE_CODE_CHANGE_PASSWORD_ADMIN));
    }

    @Override
    public RegisterResponseDTO registerAdmin(RegisterRequestDTO registerRequestDTO, String requestId) {
        adminMapper.registerAdmin(registerRequestDTO,requestId);
        return adminMapper.mapToRegisterAdminInMessage(messageSourceUtil
                .getMessage(MESSAGE_CODE_REGISTER_ADMIN));
    }

    @Override
    public EditPhotoResponseDTO offerUpdate(EditPhotoRequestDTO editPhotoRequestDTO, String requestId) {
        adminMapper.offerUpdate(editPhotoRequestDTO,requestId);
        return adminMapper.mapToEditPhotoRequestDTOAdminInMessage(messageSourceUtil
                .getMessage(MESSAGE_CODE_UPDATE_OFFER_ADMIN));

    }

    @Override
    public GetOfferPhotoResponseDTO getOfferPhoto(String requestId) {
        return adminMapper.getOfferPhoto(requestId);
    }

    @Override
    public GetAdminListResponseDTO getAllAdmin(String requestId) {
        return adminMapper.getAllAdmin(requestId);
    }

    @Override
    public DeleteAdminResponseDTO deleteAdmin(DeleteAdminRequestDTO deleteAdmin, String requestId) {
        adminMapper.deleteAdmin(deleteAdmin,requestId);
        return adminMapper.mapToDeleteAdminInMessage(messageSourceUtil
                .getMessage(MESSAGE_CODE_DELETE_ADMIN));
    }

    @Override
    public GetAdminResponseDTO getAdmin(GetAdminRequestDTO getAdminRequestDTO, String requestId) {
        logger.info("Processing the requestId : {} for getting admin ",requestId);
        return adminMapper.getAdmin(getAdminRequestDTO.getEmail());
    }

    @Override
    public GetOfferPhotoResponseDTO getOffer(String requestId) {
        logger.info("Processing the requestId : {} for getting offer photo ",requestId);
        return adminMapper.getOffer();
    }

}
