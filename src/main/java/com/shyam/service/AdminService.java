package com.shyam.service;

import com.shyam.dto.request.*;
import com.shyam.dto.response.*;

public interface AdminService {
    AdminLogInResponseDTO logIn(AdminLogInRequestDTO adminLogInRequestDTO, String requestId);

    VerifyAdminResponseDTO verifyOtp(VerifyAdminRequestDTO verifyAdminRequestDTO, String requestId);

    AdminLogoutResponseDTO logout(AdminLogoutRequestDTO adminLogoutRequestDTO, String requestId);

    EditAdminResponseDTO edit(EditAdminRequestDTO editAdminRequestDTO, String requestId);

    ChangePasswordResponseDTO changePassword(ChangePasswordRequestDTO changePasswordRequestDTO, String requestId);

    RegisterResponseDTO registerAdmin(RegisterRequestDTO registerRequestDTO, String requestId);

    EditPhotoResponseDTO offerUpdate(EditPhotoRequestDTO editPhotoRequestDTO, String requestId);

    GetOfferPhotoResponseDTO getOfferPhoto(String requestId);

    GetAdminListResponseDTO getAllAdmin(String requestId);

    DeleteAdminResponseDTO deleteAdmin(DeleteAdminRequestDTO deleteAdmin, String requestId);

    GetAdminResponseDTO getAdmin(GetAdminRequestDTO getAdminRequestDTO, String requestId);

    GetOfferPhotoResponseDTO getOffer(String requestId);

    ForgetPasswordResponseDTO forgetPassword(ForgetPasswordRequestDTO forgetPasswordRequestDTO, String requestId);

//    VerifyForgetPasswordResponseDTO forgetVerifyOtp(VerifyAdminRequestDTO verifyAdminRequestDTO, String requestId);
}
