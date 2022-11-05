package com.lukaskucera.numberneighbors.validation;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidatorConstraint
  implements ConstraintValidator<EnumValidator, String> {

  private Set<String> values;

  @Override
  public void initialize(EnumValidator constraintAnnotation) {
    values =
      Stream
        .of(constraintAnnotation.value().getEnumConstants())
        .map(Enum::name)
        .collect(Collectors.toSet());
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return values.contains(value);
  }
}
