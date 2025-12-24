package com.shyam.controller;

import com.shyam.common.exception.dto.BaseResponseDTO;
import com.shyam.dto.request.*;
import com.shyam.dto.response.*;
import com.shyam.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final AdminService adminService;

    @Operation(summary = "Login a admin user", description = "Login a Admin User.")
    @PostMapping("/logIn")
    public BaseResponseDTO<AdminLogInResponseDTO> logIn(
            @RequestBody AdminLogInRequestDTO adminLogInRequestDTO
    ){
        logger.info("Received request for login admin");
        var response = adminService.logIn(adminLogInRequestDTO);
        return new BaseResponseDTO<>(response,null);
    }

    @PostMapping("/verifyOtp")
    public ResponseEntity<BaseResponseDTO<VerifyAdminResponseDTO>> verify(
            @RequestBody VerifyAdminOtpRequestDTO verifyAdminRequestDTO
    ){
        logger.info("Received request for verify otp");

        ResponseEntity<VerifyAdminResponseDTO> responseEntity =
                adminService.verifyOtp(verifyAdminRequestDTO);

        return ResponseEntity
                .status(responseEntity.getStatusCode())
                .headers(responseEntity.getHeaders())
                .body(new BaseResponseDTO<>(
                        responseEntity.getBody(),
                        null
                ));
    }


    @Operation(summary = "Reset Password", description = "Reset Password")
    @PostMapping("/forgetPassword")
    public BaseResponseDTO<ForgetPasswordResponseDTO> forgetPasswordOtp(
            @RequestBody ForgetPasswordRequestDTO forgetPasswordRequestDTO
    ){
        logger.info("Received request for password reset");
        var response = adminService.forgetPassword(forgetPasswordRequestDTO);
        return new BaseResponseDTO<>(response,null);
    }

    @Operation(summary = "Verify otp for password reset", description = "Verify otp for password reset")
    @PostMapping("/verifyPasswordOtp")
    public BaseResponseDTO<VerifyForgetPasswordResponseDTO> forgetVerifyPassword(
            @RequestBody VerifyAdminRequestDTO verifyAdminRequestDTO
    ){
        logger.info("Received request for verify otp for password reset");
        var response = adminService.forgetVerifyOtp(verifyAdminRequestDTO);
        return new BaseResponseDTO<>(response,null);
    }


    @Operation(summary = "Offer Section", description = "Adding offer photo.")
    @PostMapping("/addOfferPhoto")
    public BaseResponseDTO<EditPhotoResponseDTO> offerUpdate(
            @RequestBody EditPhotoRequestDTO editPhotoRequestDTO
    ){
        logger.info("Received request for offer update");
        var response = adminService.offerUpdate(editPhotoRequestDTO);
        return new BaseResponseDTO<>(response,null);
    }

    @Operation(summary = "Get Offer Section", description = "Get offer photo.")
    @PostMapping("/getOfferPhoto")
    public BaseResponseDTO<GetOfferPhotoResponseDTO> getoffer(
    ){
        logger.info("Received request to get offer photo");
        var response = adminService.getOfferPhoto();
        return new BaseResponseDTO<>(response,null);
    }

    @Operation(summary = "Logout a admin user", description = "Logout a Admin User.")
    @PostMapping("/logout")
    public ResponseEntity<BaseResponseDTO<AdminLogoutResponseDTO>> logout(
            @RequestHeader("Authorization") String authorization,
            @CookieValue(value = "refreshToken", required = false) String refreshToken
    ) {
        logger.info("Received request for logout");
        String accessToken = authorization.replace("Bearer ", "");

        AdminLogoutResponseDTO response =
                adminService.logout(accessToken, refreshToken);

        ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .body(new BaseResponseDTO<>(response, null));
    }


    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PostMapping("/editAdmin")
    public BaseResponseDTO<EditAdminResponseDTO> edit(
            @RequestBody EditAdminRequestDTO editAdminRequestDTO
    ) {
        logger.info("Received request for edit");
        var response = adminService.edit(editAdminRequestDTO);
        return new BaseResponseDTO<>(response, null);
    }

    @Operation(summary = "Change password", description = "Admin change password.")
    @PostMapping("/changePassword")
    public BaseResponseDTO<ChangePasswordResponseDTO> changePassword(
            @RequestBody ChangePasswordRequestDTO changePasswordRequestDTO
    ){
        logger.info("Received request for change password");
        var response = adminService.changePassword(changePasswordRequestDTO);
        return new BaseResponseDTO<>(response,null);
    }

    @Operation(summary = "Register new Admin", description = "new admin register.")
    @PostMapping("/registerAdmin")
    public BaseResponseDTO<RegisterResponseDTO> registerAdmin(
            @RequestBody RegisterRequestDTO registerRequestDTO,
            @RequestHeader("Authorization") String authHeader
    ){
        logger.info("Received request for register admin for");
        var response = adminService.registerAdmin(registerRequestDTO);
        return new BaseResponseDTO<>(response,null);
    }

    @Operation(summary = "Get Admin", description = "Get Admin.")
    @PostMapping("/getAdminByEmail")
    public BaseResponseDTO<GetAdminResponseDTO> getAllAdmin(
            @RequestBody GetAdminRequestDTO getAdminRequestDTO
    ){
        logger.info("Received request for getting admin ");
        var response = adminService.getAdmin(getAdminRequestDTO);
        return new BaseResponseDTO<>(response,null);
    }

    @Operation(summary = "Get All Admin", description = "Get All Admin.")
    @PostMapping("/getAllAdmin")
    public BaseResponseDTO<GetAdminListResponseDTO> getAllAdmin(

    ){
        logger.info("Received request for getting all admin");
        var response = adminService.getAllAdmin();
        return new BaseResponseDTO<>(response,null);
    }

    @Operation(summary = "delete Admin", description = "Delete Admin.")
    @PostMapping("/deleteAdmin")
    public BaseResponseDTO<DeleteAdminResponseDTO> deleteAdmin(
            @RequestBody DeleteAdminRequestDTO deleteAdmin
    ){
        logger.info("Received request for delete admin");
        var response = adminService.deleteAdmin(deleteAdmin);
        return new BaseResponseDTO<>(response,null);
    }
}
