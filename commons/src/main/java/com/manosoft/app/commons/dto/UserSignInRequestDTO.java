package com.manosoft.app.commons.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserSignInRequestDTO {

    @NotEmpty(message = "Email cannot be empty")
    @NotBlank(message = "Email cannot be blank.")
    @NotNull(message = "Email cannot be Null.")
    private String userEmailOrMobile;

    @NotEmpty(message = "Password field cannot be Empty.")
    @NotBlank(message = "Password field cannot be Blank.")
    @NotNull(message = "Password field cannot be Null.")
    private String userPassword;

}
