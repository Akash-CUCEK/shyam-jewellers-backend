package com.shyam.service;

import com.shyam.dto.request.LogoutRequestDTO;
import com.shyam.dto.request.OtpRequestDTO;
import com.shyam.dto.request.logInRequestDTO;
import com.shyam.dto.response.LogInResponseDTO;
import com.shyam.dto.response.LogoutResponseDTO;
import com.shyam.dto.response.OtpResponseDTO;

public interface UserService {
    LogInResponseDTO logIn(logInRequestDTO logInRequestDTO);

    OtpResponseDTO verify(OtpRequestDTO otpRequestDTO);

    LogoutResponseDTO logout(LogoutRequestDTO logoutRequestDTO);
}
