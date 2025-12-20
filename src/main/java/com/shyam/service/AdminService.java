package com.shyam.service;

import com.shyam.dto.request.*;
import com.shyam.dto.response.*;

public interface AdminService {
    AdminLogInResponseDTO logIn(AdminLogInRequestDTO adminLogInRequestDTO );

    VerifyAdminResponseDTO verifyOtp(VerifyAdminRequestDTO verifyAdminRequestDTO );

    AdminLogoutResponseDTO logout(AdminLogoutRequestDTO adminLogoutRequestDTO );

    EditAdminResponseDTO edit(EditAdminRequestDTO editAdminRequestDTO );

    ChangePasswordResponseDTO changePassword(ChangePasswordRequestDTO changePasswordRequestDTO );

    RegisterResponseDTO registerAdmin(RegisterRequestDTO registerRequestDTO );

    EditPhotoResponseDTO offerUpdate(EditPhotoRequestDTO editPhotoRequestDTO);

    GetOfferPhotoResponseDTO getOfferPhoto();

    GetAdminListResponseDTO getAllAdmin();

    DeleteAdminResponseDTO deleteAdmin(DeleteAdminRequestDTO deleteAdmin);

    GetAdminResponseDTO getAdmin(GetAdminRequestDTO getAdminRequestDTO);

    GetOfferPhotoResponseDTO getOffer();

    ForgetPasswordResponseDTO forgetPassword(ForgetPasswordRequestDTO forgetPasswordRequestDTO);

    VerifyForgetPasswordResponseDTO forgetVerifyOtp(VerifyAdminRequestDTO verifyAdminRequestDTO);
}
