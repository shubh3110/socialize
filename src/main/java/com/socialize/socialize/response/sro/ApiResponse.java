package com.socialize.socialize.response.sro;

import com.google.gson.Gson;
import com.socialize.socialize.constant.ThreadLocalConstant;
import com.socialize.socialize.context.ThreadLocalContext;
import org.springframework.http.HttpStatus;

public class ApiResponse<T> {
  private static final long serialVersionUID = 1L;

  private int status;

  private ApiError error;

  private T data;

  private HttpStatus httpStatus;

  private String requestId;

  private String requestTime;

  public ApiResponse(int status, HttpStatus httpStatus, T data) {
    this.status = status;
    this.data = data;
    this.httpStatus = httpStatus;
    populateRequestData();
  }

  public ApiResponse(int status, HttpStatus httpStatus, ApiError error) {
    this.status = status;
    this.error = error;
    this.httpStatus = httpStatus;
    populateRequestData();
  }

  private void populateRequestData() {
    requestTime = (String) ThreadLocalContext.getValue(ThreadLocalConstant.REQUEST_TIME);
    requestId = (String) ThreadLocalContext.getValue(ThreadLocalConstant.REQUEST_ID);
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public ApiError getError() {
    return error;
  }

  public void setError(ApiError error) {
    this.error = error;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public void setHttpStatus(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
  }

  public String toGsonString(Gson gson) {
    return gson.toJson(this);
  }

  public String getRequestId() {
    return requestId;
  }

  public String getRequestTime() {
    return requestTime;
  }

  @Override
  public String toString() {
    return "ApiResponse{"
        + "status="
        + status
        + ", error="
        + error
        + ", data="
        + data
        + ", httpStatus="
        + httpStatus
        + ", requestId='"
        + requestId
        + '\''
        + ", requestTime='"
        + requestTime
        + '\''
        + '}';
  }
}
