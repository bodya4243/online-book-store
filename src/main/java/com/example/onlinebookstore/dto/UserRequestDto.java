package com.example.onlinebookstore.dto;

import com.example.onlinebookstore.validation.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@FieldMatch(first = "password",
        second = "repeatPassword",
        message = "The password fields must match")
@Data
public class UserRequestDto {
    @Email
    private String email;

    @NotNull
    @Length(min = 8, max = 20)
    private String password;

    @NotNull
    @Length(min = 8, max = 20)
    private String repeatPassword;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String shippingAddress;
}
