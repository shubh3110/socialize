package com.socialize.socialize.rest;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.protocol.HttpContext;

public class ConfigurableKeepAliveStrategy extends DefaultConnectionKeepAliveStrategy {
  private int keepAliveMillis;

  public ConfigurableKeepAliveStrategy(int keepAliveDuration) {
    super();
    this.keepAliveMillis = keepAliveDuration;
  }

  @Override
  public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
    long keepAliveInResponse = super.getKeepAliveDuration(response, context);
    if (keepAliveInResponse < 0) {
      // header not set, use configured value
      return keepAliveMillis;
    }
    return keepAliveInResponse;
  }
}
