package com.socialize.socialize.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component("rest-client")
public class RestClient {

  private final Logger LOG = LoggerFactory.getLogger(RestClient.class);

  @Autowired private RestTemplateFactory restTemplateFactory;

  public <T, U> RestResponse<T> get(
      String url,
      String restTemplateType,
      Class<T> responseType,
      Map<String, String> paramMap,
      Map<String, String> headerMap,
      U body) {
    return execute(url, restTemplateType, HttpMethod.GET, responseType, paramMap, headerMap, body);
  }

  public <T, U> RestResponse<T> post(
      String url,
      String restTemplateType,
      Class<T> responseType,
      Map<String, String> paramMap,
      Map<String, String> headerMap,
      U body) {
    return execute(url, restTemplateType, HttpMethod.POST, responseType, paramMap, headerMap, body);
  }

  public <T, U> RestResponse<T> put(
      String url,
      String restTemplateType,
      Class<T> responseType,
      Map<String, String> paramMap,
      Map<String, String> headerMap,
      U body) {
    return execute(url, restTemplateType, HttpMethod.PUT, responseType, paramMap, headerMap, body);
  }

  public <T, U> RestResponse<T> delete(
      String url,
      String restTemplateType,
      Class<T> responseType,
      Map<String, String> paramMap,
      Map<String, String> headerMap,
      U body) {
    return execute(
        url, restTemplateType, HttpMethod.DELETE, responseType, paramMap, headerMap, body);
  }

  <T, U> RestResponse<T> execute(
      String url,
      String restTemplateType,
      HttpMethod method,
      Class<T> responseType,
      Map<String, String> uriVariables,
      Map<String, String> headerMap,
      U body) {
    if (CollectionUtils.isEmpty(uriVariables)) {
      uriVariables = new HashMap<>();
    }
    if (CollectionUtils.isEmpty(headerMap)) {
      headerMap = new HashMap<>();
    }

    RestResponse<T> restResponse = new RestResponse<T>();
    Long startTime = null;
    Long endTime = null;
    int responseStatusCode = 0;
    try {
      HttpHeaders requestHeaders = new HttpHeaders();
      if (headerMap != null && !headerMap.isEmpty()) {
        Set<Map.Entry<String, String>> entrySet = headerMap.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
          requestHeaders.set(entry.getKey(), entry.getValue());
        }
      }

      HttpEntity<U> httpEntity = null;
      if (body != null) {
        httpEntity = new HttpEntity<U>(body, requestHeaders);
      } else {
        httpEntity = new HttpEntity<>(requestHeaders);
      }

      startTime = System.currentTimeMillis();

      ResponseEntity<T> responseEntity =
          restTemplateFactory
              .getRestTemplate(restTemplateType)
              .exchange(url, method, httpEntity, responseType, uriVariables);

      responseStatusCode = responseEntity.getStatusCode().value();
      restResponse.setStatusCode(responseStatusCode);
      restResponse.setResponse(responseEntity.getBody());
      restResponse.setStatusMessage(responseEntity.getStatusCode().getReasonPhrase());
      restResponse.setSuccess(true);
      restResponse.setHttpHeaders(responseEntity.getHeaders());
      return restResponse;
    } catch (RestClientException ex) {
      if (ex instanceof HttpClientErrorException) {
        HttpClientErrorException httpClientErrorException = (HttpClientErrorException) ex;
        responseStatusCode = httpClientErrorException.getRawStatusCode();

        HttpStatus.Series httpStatusSeries = httpClientErrorException.getStatusCode().series();
        if (httpStatusSeries == HttpStatus.Series.SERVER_ERROR
            || httpStatusSeries == HttpStatus.Series.REDIRECTION
            || httpStatusSeries == HttpStatus.Series.INFORMATIONAL) {
          throw ex;
        }

        restResponse.setStatusCode(responseStatusCode);
        restResponse.setStatusMessage(httpClientErrorException.getStatusText());
        restResponse.setErrorResponseBody(httpClientErrorException.getResponseBodyAsString());
        restResponse.setHttpHeaders(httpClientErrorException.getResponseHeaders());
        restResponse.setSuccess(false);
        return restResponse;

      } else {
        responseStatusCode = 500;
        throw ex;
      }
    } finally {
      endTime = System.currentTimeMillis();
      LOG.info(
          "Total time for response "
              + restTemplateType
              + " with status code "
              + responseStatusCode
              + " and time "
              + (endTime - startTime));
    }
  }
}
