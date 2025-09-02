package com.manosoft.app.commons.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ChangePasswordRequestDTO {

    @NotEmpty(message = "Email cannot be empty")
    @NotBlank(message = "Email cannot be blank.")
    @NotNull(message = "Email cannot be Null.")
    private String userEmail;

    @NotEmpty(message = "Old password filed cannot be Empty.")
    @NotBlank(message = "Old password field cannot be Blank.")
    @NotNull(message = "Old password field cannot be Null.")
    private String oldPassword;

    @NotEmpty(message = "New password filed cannot be Empty.")
    @NotBlank(message = "New password field cannot be Blank.")
    @NotNull(message = "New password field cannot be Null.")
    private String newPassword;

    @NotEmpty(message = "Confirm password filed cannot be Empty.")
    @NotBlank(message = "Confirm password field cannot be Blank.")
    @NotNull(message = "Confirm password field cannot be Null.")
    private String confirmPassword;
}
