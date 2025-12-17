package com.shyam.controller;

import com.shyam.common.exception.dto.BaseResponseDTO;
import com.shyam.dto.request.*;
import com.shyam.dto.response.*;
import com.shyam.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
            @RequestHeader String requestId,
            @RequestBody AdminLogInRequestDTO adminLogInRequestDTO
    ){
        logger.info("Received request for sigIn for requestId : {}",requestId);
        var response = adminService.logIn(adminLogInRequestDTO,requestId);
        return new BaseResponseDTO<>(response,null,requestId);
    }

    @Operation(summary = "Verify a admin user", description = "Verify a Admin User.")
    @PostMapping("/verifyOtp")
    public BaseResponseDTO<VerifyAdminResponseDTO> verify(
            @RequestHeader String requestId,
            @RequestBody VerifyAdminRequestDTO verifyAdminRequestDTO
    ){
        logger.info("Received request for verify for requestId : {}",requestId);
        var response = adminService.verifyOtp(verifyAdminRequestDTO,requestId);
        return new BaseResponseDTO<>(response,null,requestId);
    }

    @Operation(summary = "Reset Password", description = "Reset Password")
    @PostMapping("/forgetPassword")
    public BaseResponseDTO<ForgetPasswordResponseDTO> forgetPasswordOtp(
            @RequestHeader String requestId,
            @RequestBody ForgetPasswordRequestDTO forgetPasswordRequestDTO
    ){
        logger.info("Received request for password reset for requestId : {}",requestId);
        var response = adminService.forgetPassword(forgetPasswordRequestDTO,requestId);
        return new BaseResponseDTO<>(response,null,requestId);
    }

//    @Operation(summary = "Verify otp for password reset", description = "Verify otp for password reset")
//    @PostMapping("/verifyPasswordOtp")
//    public BaseResponseDTO<VerifyForgetPasswordResponseDTO> forgetVerifyPassword(
//            @RequestHeader String requestId,
//            @RequestBody VerifyAdminRequestDTO verifyAdminRequestDTO
//    ){
//        logger.info("Received request for verify otp for password reset for requestId : {}",requestId);
//        var response = adminService.forgetVerifyOtp(verifyAdminRequestDTO,requestId);
//        return new BaseResponseDTO<>(response,null,requestId);
//    }


    @Operation(summary = "Offer Section", description = "Adding offer photo.")
    @PostMapping("/addOfferPhoto")
    public BaseResponseDTO<EditPhotoResponseDTO> offerUpdate(
            @RequestHeader String requestId,
            @RequestBody EditPhotoRequestDTO editPhotoRequestDTO
    ){
        logger.info("Received request for offer update for requestId : {}",requestId);
        var response = adminService.offerUpdate(editPhotoRequestDTO,requestId);
        return new BaseResponseDTO<>(response,null,requestId);
    }

    @Operation(summary = "Get Offer Section", description = "Get offer photo.")
    @PostMapping("/get OfferPhoto")
    public BaseResponseDTO<GetOfferPhotoResponseDTO> getoffer(
            @RequestHeader(value = "requestId", required = false) String requestId
    ){
        logger.info("Received request to get offer photo for requestId : {}",requestId);
        var response = adminService.getOffer(requestId);
        return new BaseResponseDTO<>(response,null,requestId);
    }

    @Operation(summary = "Get offer Section", description = "Getting offer photo.")
    @PostMapping("/getOfferPhoto")
    public BaseResponseDTO<GetOfferPhotoResponseDTO> offerUpdate(
            @RequestHeader(value = "requestId", required = false) String requestId
    ){
        logger.info("Received request for get offer photo for requestId : {}",requestId);
        var response = adminService.getOfferPhoto(requestId);
        return new BaseResponseDTO<>(response,null,requestId);
    }

    @Operation(summary = "Logout a admin user", description = "Logout a Admin User.")
        @PostMapping("/logout")
    public BaseResponseDTO<AdminLogoutResponseDTO> logout(
            @RequestHeader String requestId,
            @RequestBody AdminLogoutRequestDTO adminLogoutRequestDTO
    ){
        logger.info("Received request for logout for requestId : {}",requestId);
        var response = adminService.logout(adminLogoutRequestDTO,requestId);
        return new BaseResponseDTO<>(response, null,requestId);
    }

    @Operation(summary = "Edit admin user", description = "Edit Admin User.")
    @PostMapping("/editAdmin")
    public BaseResponseDTO<EditAdminResponseDTO> edit(
            @RequestHeader String requestId,
            @RequestBody EditAdminRequestDTO editAdminRequestDTO
    ){
        logger.info("Received request for edit for requestId : {}",requestId);
        var response = adminService.edit(editAdminRequestDTO,requestId);
        return new BaseResponseDTO<>(response,null,requestId);
    }

    @Operation(summary = "Change password", description = "Admin change password.")
    @PostMapping("/changePassword")
    public BaseResponseDTO<ChangePasswordResponseDTO> changePassword(
            @RequestHeader String requestId,
            @RequestBody ChangePasswordRequestDTO changePasswordRequestDTO
    ){
        logger.info("Received request for change password for requestId : {}",requestId);
        var response = adminService.changePassword(changePasswordRequestDTO,requestId);
        return new BaseResponseDTO<>(response,null,requestId);
    }

    @Operation(summary = "Register new Admin", description = "new admin register.")
    @PostMapping("/registerAdmin")
    public BaseResponseDTO<RegisterResponseDTO> registerAdmin(
            @RequestHeader String requestId,
            @RequestBody RegisterRequestDTO registerRequestDTO,
            @RequestHeader("Authorization") String authHeader
    ){
        logger.info("Received request for register admin for requestId : {}",requestId);
        var response = adminService.registerAdmin(registerRequestDTO,requestId);
        return new BaseResponseDTO<>(response,null,requestId);
    }

    @Operation(summary = "Get Admin", description = "Get Admin.")
    @PostMapping("/getAdminByEmail")
    public BaseResponseDTO<GetAdminResponseDTO> getAllAdmin(
            @RequestHeader String requestId,
            @RequestBody GetAdminRequestDTO getAdminRequestDTO
    ){
        logger.info("Received request for getting admin for requestId : {}",requestId);
        var response = adminService.getAdmin(getAdminRequestDTO,requestId);
        return new BaseResponseDTO<>(response,null,requestId);
    }

    @Operation(summary = "Get All Admin", description = "Get All Admin.")
    @PostMapping("/getAllAdmin")
    public BaseResponseDTO<GetAdminListResponseDTO> getAllAdmin(
            @RequestHeader(value = "requestId", required = false) String requestId
    ){
        logger.info("Received request for getting all admin for requestId : {}",requestId);
        var response = adminService.getAllAdmin(requestId);
        return new BaseResponseDTO<>(response,null,requestId);
    }

    @Operation(summary = "delete Admin", description = "Delete Admin.")
    @PostMapping("/deleteAdmin")
    public BaseResponseDTO<DeleteAdminResponseDTO> deleteAdmin(
            @RequestHeader String requestId,
            @RequestBody DeleteAdminRequestDTO deleteAdmin
    ){
        logger.info("Received request for delete admin for requestId : {}",requestId);
        var response = adminService.deleteAdmin(deleteAdmin,requestId);
        return new BaseResponseDTO<>(response,null,requestId);
    }
}
