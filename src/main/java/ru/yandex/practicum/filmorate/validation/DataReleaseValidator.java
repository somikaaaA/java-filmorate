package ru.yandex.practicum.filmorate.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class DataReleaseValidator implements ConstraintValidator<DataReleaseValid, LocalDate> {
    private static final LocalDate RELEASE = LocalDate.of(1895, 12, 28);

    @Override
    public void initialize(DataReleaseValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        return !date.isBefore(RELEASE);
    }
}
