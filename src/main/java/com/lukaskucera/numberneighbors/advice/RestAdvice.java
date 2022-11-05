package com.lukaskucera.numberneighbors.advice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class RestAdvice extends ResponseEntityExceptionHandler {

  private static final Pattern FIELD_PATTERN = Pattern.compile(
    "\\[\"([\\w]+)\"\\]"
  );

  /**
   * inspired by <a href="https://stackoverflow.com/a/64576320">
   * stackoverflow.com/a/64576320</a>
   */
  private static final Pattern ENUM_VALUES_PATTERN = Pattern.compile(
    "values accepted for Enum class: (\\[[\\w\\s,]+\\]);"
  );

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
    MissingServletRequestParameterException ex,
    HttpHeaders headers,
    HttpStatus status,
    WebRequest request
  ) {
    logger.error("MissingServletRequestParameter: " + ex.getMessage());

    return handleExceptionInternal(
      ex,
      ex.getMessage(),
      headers,
      status,
      request
    );
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
    HttpMessageNotReadableException ex,
    HttpHeaders headers,
    HttpStatus status,
    WebRequest request
  ) {
    final String message = ex.getMessage();

    logger.error("HttpMessageNotReadable: " + message);

    String body = null;

    if (message != null && ex.getCause() instanceof InvalidFormatException) {
      Matcher fieldMatcher = FIELD_PATTERN.matcher(message);
      Matcher enumMatcher = ENUM_VALUES_PATTERN.matcher(message);

      if (fieldMatcher.find() && enumMatcher.find()) {
        body =
          fieldMatcher.group(1) + ": must be one of " + enumMatcher.group(1);
      }
    }

    return handleExceptionInternal(ex, body, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
    MethodArgumentNotValidException ex,
    HttpHeaders headers,
    HttpStatus status,
    WebRequest request
  ) {
    final BindingResult bindingResult = ex.getBindingResult();
    final List<String> errors = new ArrayList<>();

    for (final FieldError error : bindingResult.getFieldErrors()) {
      errors.add(error.getField() + ": " + error.getDefaultMessage());
    }

    for (final ObjectError error : bindingResult.getGlobalErrors()) {
      errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
    }

    logger.error("MethodArgumentNotValid: " + errors);

    return handleExceptionInternal(
      ex,
      errors.size() == 1 ? errors.get(0) : errors,
      headers,
      status,
      request
    );
  }
}
