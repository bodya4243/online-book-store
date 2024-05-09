package com.example.onlinebookstore.validation;

import com.example.onlinebookstore.dto.UserRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, UserRequestDto> {

    @Override
    public boolean isValid(UserRequestDto userRequestDto,
                           ConstraintValidatorContext constraintValidatorContext) {
        return Objects.equals(userRequestDto.getPassword(), userRequestDto.getRepeatPassword());
    }
}
