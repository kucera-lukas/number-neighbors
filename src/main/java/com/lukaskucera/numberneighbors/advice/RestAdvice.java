package com.lukaskucera.numberneighbors.advice;

import java.util.ArrayList;
import java.util.List;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class RestAdvice extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
    MethodArgumentNotValidException ex,
    HttpHeaders headers,
    HttpStatus status,
    WebRequest request
  ) {
    logger.info(ex.getClass().getName());

    final BindingResult bindingResult = ex.getBindingResult();
    final List<String> errors = new ArrayList<>();

    for (final FieldError error : bindingResult.getFieldErrors()) {
      errors.add(error.getField() + ": " + error.getDefaultMessage());
    }

    for (final ObjectError error : bindingResult.getGlobalErrors()) {
      errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
    }

    return handleExceptionInternal(
      ex,
      errors.size() == 1 ? errors.get(0) : errors,
      headers,
      status,
      request
    );
  }
}
