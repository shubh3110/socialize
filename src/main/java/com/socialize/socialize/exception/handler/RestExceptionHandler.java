package com.socialize.socialize.exception.handler;

import com.socialize.socialize.constant.Constant;
import com.socialize.socialize.enums.APIResponseStatus;
import com.socialize.socialize.error.ErrorCodes;
import com.socialize.socialize.exception.SocializeException;
import com.socialize.socialize.exception.SocializeRuntimeException;
import com.socialize.socialize.response.sro.ApiError;
import com.socialize.socialize.response.sro.ApiResponse;
import com.socialize.socialize.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class RestExceptionHandler {
  private static final Logger LOG = LogManager.getLogger(RestExceptionHandler.class);

  @Autowired MessageSource messageSource;

  @ExceptionHandler(
      value = {MissingServletRequestParameterException.class, MissingRequestHeaderException.class})
  public ResponseEntity<ApiResponse> handleMissingServletRequestParameterException(
      final MissingServletRequestParameterException exception, WebRequest webRequest) {

    ApiError apiError =
        new ApiError(ErrorCodes.SOCIALIZE_4000.name(), ErrorCodes.SOCIALIZE_4000.getMessage());
    log(exception, webRequest, apiError);
    ApiResponse apiResponse =
        new ApiResponse(
            APIResponseStatus.ERROR.getValue(),
            ErrorCodes.SOCIALIZE_4000.getHttpStatus(),
            apiError);
    return new ResponseEntity<ApiResponse>(apiResponse, ErrorCodes.SOCIALIZE_4000.getHttpStatus());
  }

  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ResponseEntity<Object> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex, WebRequest request) {

    ApiError apiError =
        new ApiError(ErrorCodes.SOCIALIZE_4000.name(), ErrorCodes.SOCIALIZE_4000.getMessage());
    log(ex, request, apiError);
    ApiResponse<Void> apiResponse =
        new ApiResponse<>(APIResponseStatus.ERROR.getValue(), HttpStatus.BAD_REQUEST, apiError);
    return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException ex, WebRequest request) {

    ApiError apiError =
        new ApiError(ErrorCodes.SOCIALIZE_4001.name(), ErrorCodes.SOCIALIZE_4001.getMessage());
    log(ex, request, apiError);
    ApiResponse<Void> apiResponse =
        new ApiResponse<>(APIResponseStatus.ERROR.getValue(), HttpStatus.BAD_REQUEST, apiError);
    return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
  }

  /** Generic Bad Request exceptions */
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(
      IllegalArgumentException ex, WebRequest request) {

    ApiError apiError =
        new ApiError(ErrorCodes.SOCIALIZE_4001.name(), ErrorCodes.SOCIALIZE_4001.getMessage());
    log(ex, request, apiError);
    ApiResponse<Void> apiResponse =
        new ApiResponse<>(APIResponseStatus.ERROR.getValue(), HttpStatus.BAD_REQUEST, apiError);
    return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ApiResponse<Void>> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException ex, WebRequest request) {

    ApiError apiError =
        new ApiError(ErrorCodes.SOCIALIZE_4001.name(), ErrorCodes.SOCIALIZE_4001.getMessage());
    log(ex, request, apiError);
    ApiResponse<Void> apiResponse =
        new ApiResponse<>(APIResponseStatus.ERROR.getValue(), HttpStatus.BAD_REQUEST, apiError);
    return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ApiResponse<Void>> handleConstraintViolationException(
      ConstraintViolationException ex, WebRequest request) {

    ApiError apiError =
        new ApiError(ErrorCodes.SOCIALIZE_4001.name(), ErrorCodes.SOCIALIZE_4001.getMessage());
    log(ex, request, apiError);
    ApiResponse<Void> apiResponse =
        new ApiResponse<>(APIResponseStatus.ERROR.getValue(), HttpStatus.BAD_REQUEST, apiError);
    return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ApiResponse<Void>> handleHttpMediaTypeNotSupportedException(
      HttpMediaTypeNotSupportedException ex, WebRequest request) {

    ApiError apiError = new ApiError(ErrorCodes.SOCIALIZE_4001.name(), ex.getMessage());
    log(ex, request, apiError);
    ApiResponse<Void> apiResponse =
        new ApiResponse<>(APIResponseStatus.ERROR.getValue(), HttpStatus.BAD_REQUEST, apiError);
    return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
  }

  /** handling our Custom Applications Exceptions */
  @ExceptionHandler(SocializeRuntimeException.class)
  @ResponseBody
  public ResponseEntity<ApiResponse<Void>> handleStorefrontRuntimeException(
      SocializeRuntimeException ex, WebRequest request) {

    ApiError apiError = new ApiError(ex.getErrorCode(), ex.getMessage());
    log(ex, request, apiError);
    ApiResponse<Void> apiResponse =
        new ApiResponse<>(APIResponseStatus.ERROR.getValue(), ex.getHttpStatus(), apiError);
    return new ResponseEntity<>(apiResponse, ex.getHttpStatus());
  }

  @ExceptionHandler(SocializeException.class)
  @ResponseBody
  public ResponseEntity<ApiResponse<Void>> handleStorefrontException(
      SocializeException ex, WebRequest request) {

    ApiError apiError = new ApiError(ex.getErrorCode(), Constant.INTERNAL_SERVER_ERROR_MESSAGE);
    log(ex, request, apiError);
    ApiResponse<Void> apiResponse =
        new ApiResponse<>(APIResponseStatus.ERROR.getValue(), ex.getHttpStatus(), apiError);
    return new ResponseEntity<>(apiResponse, ex.getHttpStatus());
  }

  @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException ex, WebRequest request) {

    ApiError apiError =
        new ApiError(ErrorCodes.SOCIALIZE_4001.name(), ErrorCodes.SOCIALIZE_4001.getMessage());
    log(ex, request, apiError);
    ApiResponse<Void> apiResponse =
        new ApiResponse<>(APIResponseStatus.ERROR.getValue(), HttpStatus.BAD_REQUEST, apiError);
    return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
  }

  /**
   * Generic Internal server Error If any Exception is not handled by above handlers it will be
   * handled here
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ApiResponse<Void>> handleException(Exception ex, WebRequest request) {

    ApiError apiError =
        new ApiError(ErrorCodes.SOCIALIZE_4001.name(), ErrorCodes.SOCIALIZE_4001.getMessage());
    log(ex, request, apiError);
    ApiResponse<Void> apiResponse =
        new ApiResponse<>(
            APIResponseStatus.ERROR.getValue(), HttpStatus.INTERNAL_SERVER_ERROR, apiError);
    return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /** Common code for Exception Handlers */
  private void log(Exception e, WebRequest request, ApiError apiError) {
    if (request != null) LOG.error("for REQUEST {}", request.toString());
    LOG.error("Rest Exception Handler- apiError :{} , {} ", apiError, Utils.exceptionFormatter(e));
    // TODO : push data to kafka
  }
}
