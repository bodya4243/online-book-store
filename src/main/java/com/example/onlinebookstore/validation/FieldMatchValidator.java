package com.example.onlinebookstore.validation;

import com.example.onlinebookstore.dto.UserRequestDto;
import com.example.onlinebookstore.exception.RegistrationException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.Objects;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, UserRequestDto> {
    @Override
    public boolean isValid(UserRequestDto userRequestDto,
                           ConstraintValidatorContext constraintValidatorContext) {
        Object field1Value = getFieldValue(userRequestDto, "password");
        Object field2Value = getFieldValue(userRequestDto, "repeatPassword");

        return Objects.equals(field1Value, field2Value);
    }

    private Object getFieldValue(UserRequestDto userRequestDto, String fieldName) {
        try {
            Field field = UserRequestDto.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(userRequestDto);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RegistrationException("can't find the appropriate field");
        }
    }
}
