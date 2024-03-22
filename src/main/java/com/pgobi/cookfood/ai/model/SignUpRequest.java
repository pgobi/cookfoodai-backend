package com.pgobi.cookfood.ai.model;

import com.pgobi.cookfood.ai.validation.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "Firstname is required")
    private String firstname;

    @NotBlank(message = "Lastname is required")
    private String lastname;

    @NotBlank(message = "Email is required")
    @Email(message = "email format is not valid")
    private String email;

    @NotBlank(message = "Password is required")
    @StrongPassword
    private String password;

}
