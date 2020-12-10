package com.socialize.socialize.rest;

public class HttpClientProperties {

  int defaultMaxPerRoute;

  int maxTotalConnections;

  int soTimeout;

  int keepAlive = 0;

  String cookiePolicy;

  public int getDefaultMaxPerRoute() {
    return defaultMaxPerRoute;
  }

  public void setDefaultMaxPerRoute(int defaultMaxPerRoute) {
    this.defaultMaxPerRoute = defaultMaxPerRoute;
  }

  public int getMaxTotalConnections() {
    return maxTotalConnections;
  }

  public void setMaxTotalConnections(int maxTotalConnections) {
    this.maxTotalConnections = maxTotalConnections;
  }

  public int getSoTimeout() {
    return soTimeout;
  }

  public void setSoTimeout(int soTimeout) {
    this.soTimeout = soTimeout;
  }

  public int getKeepAlive() {
    return keepAlive;
  }

  public void setKeepAlive(int keepAlive) {
    this.keepAlive = keepAlive;
  }

  public String getCookiePolicy() {
    return cookiePolicy;
  }

  public void setCookiePolicy(String cookiePolicy) {
    this.cookiePolicy = cookiePolicy;
  }
}
