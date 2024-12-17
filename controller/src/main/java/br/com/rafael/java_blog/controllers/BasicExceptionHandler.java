package br.com.rafael.java_blog.controllers;


import br.com.rafael.java_blog.model.ErrorResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import java.io.PrintWriter;
import java.io.StringWriter;


@ControllerAdvice
@RequiredArgsConstructor
public class BasicExceptionHandler extends ResponseEntityExceptionHandler {

   @Qualifier("debug")
   private final boolean debug;

   @Override
   protected ResponseEntity<Object> handleExceptionInternal(@NonNull final Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

      if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
         request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
      }
      return buildResponseEntity(ex.getMessage(), ErrorResponse.Severity.ERROR, ex, HttpStatus.INTERNAL_SERVER_ERROR);
   }

   @ExceptionHandler({ResponseStatusException.class})
   protected ResponseEntity<Object> httpResponseStatusException(@NonNull final ResponseStatusException ex) {
      return buildResponseEntity(StringUtils.hasText(ex.getReason()) ? ex.getReason() : "", ErrorResponse.Severity.ERROR, ex, ex.getStatus());
   }


   @ExceptionHandler({Exception.class})
   protected ResponseEntity<Object> exception(@NonNull final Exception ex) {
      return buildResponseEntity(StringUtils.hasText(ex.getMessage()) ? ex.getMessage() : ex.getClass().getSimpleName(), ErrorResponse.Severity.ERROR, ex, HttpStatus.INTERNAL_SERVER_ERROR);
   }


   private ResponseEntity<Object> buildResponseEntity(@NonNull final String message, @NonNull final ErrorResponse.Severity severity, @NonNull final Exception ex, @NonNull final HttpStatus status) {

      return ResponseEntity.status(status).body(ErrorResponse.builder().message(message).stackTrace(debug ? stackTraceToString(ex) : null).severity(severity).build());
   }


   public static String stackTraceToString(Exception e) {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      e.printStackTrace(pw);
      return sw.toString();
   }
}
