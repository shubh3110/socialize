package com.socialize.socialize.response.sro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties
public class ApiError {
  private final String code;

  private final String message;

  public ApiError(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return "ApiError{" + "code='" + code + '\'' + ", message='" + message + '\'' + '}';
  }
}
