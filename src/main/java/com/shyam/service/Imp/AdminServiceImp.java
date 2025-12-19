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
    public AdminLogInResponseDTO logIn(AdminLogInRequestDTO adminLogInRequestDTO) {
        adminMapper.logIn(adminLogInRequestDTO);
        return adminMapper.mapToAdminLogInMessage(messageSourceUtil
                .getMessage(MESSAGE_CODE_LOGIN_SEND_OTP));
    }
    @Override
    public ForgetPasswordResponseDTO forgetPassword(ForgetPasswordRequestDTO forgetPasswordRequestDTO) {
        adminMapper.forgetPassword(forgetPasswordRequestDTO);
        return adminMapper.mapToAdminForgetPasswordMessage(messageSourceUtil
                .getMessage(MESSAGE_CODE_FORGET_ADMIN_PASSWORD_SEND_OTP));
    }

//    @Override
//    public VerifyForgetPasswordResponseDTO forgetVerifyOtp(VerifyAdminRequestDTO verifyAdminRequestDTO, String ) {
//        adminMapper.forgetVerifyOtp(verifyAdminRequestDTO);
//        return adminMapper.mapToVerifyForgetOtpInMessage(messageSourceUtil
//                .getMessage(MESSAGE_CODE_FORGET_ADMIN_PASSWORD));
//    }

    @Override
    public VerifyAdminResponseDTO verifyOtp(VerifyAdminRequestDTO verifyAdminRequestDTO) {
        logger.info("Processing for verifying the otp ");
        return adminMapper.verifyOTP(verifyAdminRequestDTO);
    }

    @Override
    public AdminLogoutResponseDTO logout(AdminLogoutRequestDTO adminLogoutRequestDTO) {
        logger.info("Processing for logout the admin user ");
        adminMapper.logout(adminLogoutRequestDTO.getToken());
        logger.info("done");
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

}
