package com.shyam.controller;

import com.shyam.common.exception.dto.BaseResponseDTO;
import com.shyam.dto.request.LogoutRequestDTO;
import com.shyam.dto.request.OtpRequestDTO;
import com.shyam.dto.request.logInRequestDTO;
import com.shyam.dto.response.LogInResponseDTO;
import com.shyam.dto.response.LogoutResponseDTO;
import com.shyam.dto.response.OtpResponseDTO;
import com.shyam.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Operation(summary = "Login a user", description = "Login a User.")
    @PostMapping("/logIn")
    public BaseResponseDTO<LogInResponseDTO> register(
            @RequestHeader String requestId,
            @RequestBody logInRequestDTO logInRequestDTO
            ){
        logger.info("Received request for sigIn for requestId : {}",requestId);
        var response = userService.logIn(logInRequestDTO,requestId);
        return new BaseResponseDTO<>(response,null,requestId);
    }

    @Operation(summary = "Verify a user", description = "Verify a User.")
    @PostMapping("/verify")
    public BaseResponseDTO<OtpResponseDTO> verify(
            @RequestHeader String requestId,
            @RequestBody OtpRequestDTO otpRequestDTO
    ){
        logger.info("Received request for verify for requestId : {}",requestId);
        var response = userService.verify(otpRequestDTO,requestId);
        return new BaseResponseDTO<>(response, null,requestId);
    }

    @Operation(summary = "Logout a user", description = "Logout a User.")
    @PostMapping("/logout")
    public BaseResponseDTO<LogoutResponseDTO> logout(
            @RequestHeader String requestId,
            @RequestBody LogoutRequestDTO logoutRequestDTO
    ){
        logger.info("Received request for logout for requestId : {}",requestId);
        var response = userService.logout(logoutRequestDTO,requestId);
        return new BaseResponseDTO<>(response, null,requestId);
    }
}
