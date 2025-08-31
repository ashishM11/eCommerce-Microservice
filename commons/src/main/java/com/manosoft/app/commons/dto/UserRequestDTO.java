package com.manosoft.app.commons.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public class UserRequestDTO {

    @NotEmpty(message = "First Name cannot be Empty.")
    @NotBlank(message = "First Name cannot be Blank.")
    @NotNull(message = "First Name cannot be Null.")
    private String userFName;

    @NotEmpty(message = "Last Name cannot be Empty.")
    @NotBlank(message = "Last Name cannot be Blank.")
    @NotNull(message = "Last Name cannot be Null.")
    private String userLName;

    @NotEmpty(message = "Mobile number cannot be Empty.")
    @NotBlank(message = "Mobile number cannot be Blank.")
    @NotNull(message = "Mobile number cannot be Null.")
    @Length(max = 10, min = 10, message = "Mobile Number should be of 10 digit.")
    private String userMobile;

    @NotEmpty(message = "EMail Id cannot be Empty.")
    @NotBlank(message = "EMail Id cannot be Blank.")
    @NotNull(message = "EMail Id cannot be Null.")
    @Email(message = "Email Id must be in correct format.")
    private String userEmail;

    @NotEmpty(message = "Please share your gender.")
    @NotBlank(message = "Please share your gender.")
    @NotNull(message = "Please share your gender.")
    private String userGender;

    @NotNull(message = "Date of Birth is required field.")
    private LocalDate userDOB;
    
    @NotNull(message = "Password & Retype password field cannot be Null.")
    private PasswordRequestDTO password;
}
