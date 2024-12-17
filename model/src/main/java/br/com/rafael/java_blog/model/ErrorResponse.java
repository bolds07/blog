package br.com.rafael.java_blog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

   @JsonProperty("stack_trace")
   private String stackTrace;

   @JsonProperty("message")
   private String message;

   @JsonProperty("severity")
   private Severity severity;

   public enum Severity {
      INFO, WARN, ERROR, FATAL;
   }

}
