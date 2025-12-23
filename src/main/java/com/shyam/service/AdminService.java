package com.shyam.service;

import com.shyam.dto.request.*;
import com.shyam.dto.response.*;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    AdminLogInResponseDTO logIn(AdminLogInRequestDTO adminLogInRequestDTO );

    ResponseEntity<VerifyAdminResponseDTO> verifyOtp(VerifyAdminRequestDTO verifyAdminRequestDTO );

    EditAdminResponseDTO edit(EditAdminRequestDTO editAdminRequestDTO );

    ChangePasswordResponseDTO changePassword(ChangePasswordRequestDTO changePasswordRequestDTO );

    RegisterResponseDTO registerAdmin(RegisterRequestDTO registerRequestDTO );

    EditPhotoResponseDTO offerUpdate(EditPhotoRequestDTO editPhotoRequestDTO);

    GetOfferPhotoResponseDTO getOfferPhoto();

    GetAdminListResponseDTO getAllAdmin();

    DeleteAdminResponseDTO deleteAdmin(DeleteAdminRequestDTO deleteAdmin);

    GetAdminResponseDTO getAdmin(GetAdminRequestDTO getAdminRequestDTO);

    ForgetPasswordResponseDTO forgetPassword(ForgetPasswordRequestDTO forgetPasswordRequestDTO);

    VerifyForgetPasswordResponseDTO forgetVerifyOtp(VerifyAdminRequestDTO verifyAdminRequestDTO);

    AdminLogoutResponseDTO logout(String accessToken, String refreshToken);
}
