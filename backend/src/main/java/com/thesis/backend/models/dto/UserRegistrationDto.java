package com.thesis.backend.models.dto;

import com.thesis.backend.annotations.ValidEmail;
import com.thesis.backend.annotations.ValidFullname;
import com.thesis.backend.annotations.ValidPassword;
import com.thesis.backend.annotations.ValidUsername;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDto {

    @ValidFullname
    private String fullName;

    @ValidUsername
    private String userName;

    @ValidEmail
    private String email;

    @ValidPassword
    private String password;
}
