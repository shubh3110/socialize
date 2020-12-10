package com.socialize.socialize.rest;

import com.socialize.socialize.constant.RestClientConstant;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.ClientHttpRequestFactorySupplier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Component
public class RestTemplateFactory {

  public static final String STOREFRONT_HEALTH = "STOREFRONT_HEALTH";

  public Map<String, RestTemplate> restTemplates = new HashMap<>();

  private RestTemplate defaultRestTemplate = null;

  @Autowired private RestTemplateBuilder restTemplateBuilder;

  @PostConstruct
  public void init() {

    Duration defaultConnectionTimeout = Duration.ofMillis(5000L);
    Duration defaultReadTimeout = Duration.ofMillis(5000L);

    populateHealthProperties(defaultConnectionTimeout, defaultReadTimeout);

    defaultRestTemplate =
        restTemplateBuilder
            .setConnectTimeout(defaultConnectionTimeout)
            .setReadTimeout(defaultReadTimeout)
            .build();
    defaultRestTemplate.setRequestFactory(createHttpRequestFactory(getDefaultApiHttpProperties()));
  }

  private void populateHealthProperties(
      Duration defaultConnectionTimeout, Duration defaultReadTimeout) {
    RestTemplate restTemplatePromo =
        restTemplateBuilder
            .setConnectTimeout(defaultConnectionTimeout)
            .setReadTimeout(defaultReadTimeout)
            .requestFactory(new ClientHttpRequestFactorySupplier())
            .build();
    restTemplates.put(STOREFRONT_HEALTH, restTemplatePromo);
  }

  public RestTemplate getRestTemplate(String restTemplateType) {

    RestTemplate restTemplate = restTemplates.get(restTemplateType);
    if (null != restTemplate) {
      return restTemplate;
    } else {
      return defaultRestTemplate;
    }
  }

  private HttpClientProperties getDefaultApiHttpProperties() {
    HttpClientProperties properties = new HttpClientProperties();
    properties.setDefaultMaxPerRoute(RestClientConstant.HTTP_CLIENT_POOL_MAX_PER_ROUTE);
    properties.setMaxTotalConnections(RestClientConstant.HTTP_CLIENT_POOL_MAX_CONNECTIONS);
    properties.setSoTimeout(RestClientConstant.HTTP_CLIENT_SOCKET_TIMEOUT);
    return properties;
  }

  private PoolingHttpClientConnectionManager createPoolingHttpClientConnectionManager(
      HttpClientProperties httpClientProperties) {
    PoolingHttpClientConnectionManager httpClientConnectionManager =
        new PoolingHttpClientConnectionManager();
    httpClientConnectionManager.setDefaultConnectionConfig(ConnectionConfig.DEFAULT);
    httpClientConnectionManager.setMaxTotal(httpClientProperties.getMaxTotalConnections());
    httpClientConnectionManager.setDefaultMaxPerRoute(httpClientProperties.getDefaultMaxPerRoute());
    httpClientConnectionManager.setDefaultSocketConfig(
        SocketConfig.custom().setSoTimeout(httpClientProperties.getSoTimeout()).build());
    return httpClientConnectionManager;
  }

  private ConnectionKeepAliveStrategy createConnectionKeepAliveStrategy(
      HttpClientProperties httpClientProperties) {
    if (httpClientProperties.getKeepAlive() > 0) {
      return new ConfigurableKeepAliveStrategy(httpClientProperties.getKeepAlive());
    } else {
      return DefaultConnectionKeepAliveStrategy.INSTANCE;
    }
  }

  private ClientHttpRequestFactory createHttpRequestFactory(
      HttpClientProperties httpClientProperties) {
    // HttpClientProperties httpClientProperties = getDefaultApiHttpProperties();
    RequestConfig requestConfig =
        RequestConfig.custom()
            .setCookieSpec(
                httpClientProperties.getCookiePolicy() == null
                        || httpClientProperties.getCookiePolicy().length() == 0
                    ? CookieSpecs.DEFAULT
                    : httpClientProperties.getCookiePolicy())
            .build();

    CloseableHttpClient httpClient =
        HttpClients.custom()
            .setConnectionManager(createPoolingHttpClientConnectionManager(httpClientProperties))
            .setKeepAliveStrategy(createConnectionKeepAliveStrategy(httpClientProperties))
            .setDefaultRequestConfig(requestConfig)
            .build();

    return new HttpComponentsClientHttpRequestFactory(httpClient);
  }
}
