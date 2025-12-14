package com.shyam.service.Imp;

import com.shyam.common.util.MessageSourceUtil;
import com.shyam.dto.request.LogoutRequestDTO;
import com.shyam.dto.request.OtpRequestDTO;
import com.shyam.dto.request.logInRequestDTO;
import com.shyam.dto.response.LogInResponseDTO;
import com.shyam.dto.response.LogoutResponseDTO;
import com.shyam.dto.response.OtpResponseDTO;
import com.shyam.mapper.UserMapper;
import com.shyam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.shyam.constants.MessageConstant.*;


@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);
    private final UserMapper userMapper;
    private final MessageSourceUtil messageSourceUtil;

    @Override
    public LogInResponseDTO logIn(logInRequestDTO logInRequestDTO, String requestId) {
        logger.info("Processing the requestId : {} for logIn ",requestId);
        userMapper.logInMapper(logInRequestDTO);
        return userMapper.mapToUserLogInMessage(messageSourceUtil
                .getMessage(MESSAGE_CODE_LOGIN_SEND_OTP));
    }

    @Override
    public OtpResponseDTO verify(OtpRequestDTO otpRequestDTO, String requestId) {
        logger.info("Processing the requestId : {} for verifying the otp ",requestId);
        return userMapper.verifyOTP(otpRequestDTO);
    }

    @Override
    public LogoutResponseDTO logout(LogoutRequestDTO logoutRequestDTO, String requestId) {
        logger.info("Processing the requestId : {} for logout the user ",requestId);
        userMapper.logout(logoutRequestDTO.getToken());
        logger.info("done");
        return userMapper.mapToUserLogoutInMessage(messageSourceUtil
                .getMessage(MESSAGE_CODE_LOG_OUT));
    }

}
