package com.thesis.backend.models.dto;

import com.thesis.backend.annotations.ValidPassword;
import com.thesis.backend.annotations.ValidUsername;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {

    @ValidUsername
    private String userName;

    @ValidPassword
    private String password;
}
