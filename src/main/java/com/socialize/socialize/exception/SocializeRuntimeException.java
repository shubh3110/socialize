package com.socialize.socialize.exception;

import org.springframework.http.HttpStatus;

public class SocializeRuntimeException extends RuntimeException {
  private String errorCode;
  private HttpStatus httpStatus;

  /** Should be used when thrown in API response */
  public SocializeRuntimeException(String errorCode, String message, HttpStatus httpStatus) {
    super(message);
    this.errorCode = errorCode;
    this.httpStatus = httpStatus;
  }

  /** Should be used when thrown in API response */
  public SocializeRuntimeException(
      String errorCode, String message, HttpStatus httpStatus, Throwable cause) {
    super(message, cause);
    this.errorCode = errorCode;
    this.httpStatus = httpStatus;
  }

  /** Only for internal usage and not to be thrown in API response */
  public SocializeRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  /** Only for internal usage and not to be thrown in API response */
  public SocializeRuntimeException(String message) {
    super(message);
  }

  public String getErrorCode() {
    return errorCode;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}
