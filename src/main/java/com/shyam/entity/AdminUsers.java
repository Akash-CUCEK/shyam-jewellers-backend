package com.shyam.entity;

import com.shyam.common.constants.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String password;
    private String otp;
    @Column(name = "otp_generated_time")
    private LocalDateTime otpGeneratedTime;
    private String name;
    private String phoneNumber;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private Role role;
}
