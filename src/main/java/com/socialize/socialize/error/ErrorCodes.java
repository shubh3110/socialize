package com.socialize.socialize.error;

import org.springframework.http.HttpStatus;

public enum ErrorCodes {
  SOCIALIZE_4000("Something went wrong, please try again", HttpStatus.BAD_REQUEST),
  SOCIALIZE_4001("Something went wrong, please try again", HttpStatus.BAD_REQUEST),

  SOCIALIZE_5000("Something went wrong, please try again", HttpStatus.BAD_REQUEST),
  SOCIALIZE_5001("Something went wrong, please try again", HttpStatus.BAD_REQUEST),
  SOCIALIZE_5002("Something went wrong, please try again", HttpStatus.BAD_REQUEST);

  private String message;

  private HttpStatus httpStatus;

  private ErrorCodes(String message, HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
    this.message = message;
  }

  public String getMessage() {

    return message;
  }

  public HttpStatus getHttpStatus() {

    return httpStatus;
  }
}
