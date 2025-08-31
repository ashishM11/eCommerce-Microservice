package com.manosoft.app.commons.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class PasswordRequestDTO {

    @NotEmpty(message = "Password cannot be Empty.")
    @NotBlank(message = "Password cannot be Blank.")
    @NotNull(message = "Password cannot be Null.")
    private String password;

    @NotEmpty(message = "Retype Password filed cannot be Empty.")
    @NotBlank(message = "Retype Password field cannot be Blank.")
    @NotNull(message = "Retype Password field cannot be Null.")
    private String retypePassword;

}
