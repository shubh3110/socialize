package com.socialize.socialize.rest;

import org.springframework.http.HttpHeaders;

public class RestResponse<T> {
  private T response;
  private int statusCode;
  private String statusMessage;
  private boolean success;

  private String url;
  private HttpHeaders httpHeaders;
  private String errorResponseBody;

  public T getResponse() {
    return response;
  }

  public void setResponse(T response) {
    this.response = response;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getStatusMessage() {
    return statusMessage;
  }

  public void setStatusMessage(String statusMessage) {
    this.statusMessage = statusMessage;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public HttpHeaders getHttpHeaders() {
    return httpHeaders;
  }

  public void setHttpHeaders(HttpHeaders httpHeaders) {
    this.httpHeaders = httpHeaders;
  }

  public String getErrorResponseBody() {
    return errorResponseBody;
  }

  public void setErrorResponseBody(String errorResponseBody) {
    this.errorResponseBody = errorResponseBody;
  }

  @Override
  public String toString() {
    return "RestResponse{"
        + "response="
        + response
        + ", statusCode="
        + statusCode
        + ", statusMessage='"
        + statusMessage
        + '\''
        + ", success="
        + success
        + ", url='"
        + url
        + '\''
        + ", httpHeaders="
        + httpHeaders
        + ", errorResponseBody='"
        + errorResponseBody
        + '\''
        + '}';
  }
}
