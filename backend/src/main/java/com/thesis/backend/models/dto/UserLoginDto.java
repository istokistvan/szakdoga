package com.thesis.backend.models.dto;

import com.thesis.backend.annotations.ValidPassword;
import com.thesis.backend.annotations.ValidUsername;
import lombok.Data;

@Data
public class UserLoginDto {

    @ValidUsername
    private String userName;

    @ValidPassword
    private String password;
}
