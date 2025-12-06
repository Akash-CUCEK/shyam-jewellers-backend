package com.shyam.common.exception.domain;

import com.shyam.common.exception.dto.ErrorMessagesDTO;
import com.shyam.common.exception.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Collections;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(SYMException.class)
    public ResponseEntity<ErrorResponseDTO> handleSRYException(SYMException sym){
        // Create a user-friendly error message
        var errorMessagesDTO = new ErrorMessagesDTO(sym.getMessage());

        // Prepare ErrorResponseDTO with necessary details
        var errorResponseDTO = new ErrorResponseDTO(
                Collections.singletonList(errorMessagesDTO),  // List of messages
                LocalDateTime.now(),                          // Current timestamp
                sym.getErrorType()                             // Error type
        );

        return new ResponseEntity<>(errorResponseDTO, sym.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        ErrorMessagesDTO errorMessagesDTO = new ErrorMessagesDTO("Something went wrong. Please try again later.");

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                Collections.singletonList(errorMessagesDTO),
                LocalDateTime.now(),
                SYMErrorType.GENERIC_EXCEPTION
        );

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
