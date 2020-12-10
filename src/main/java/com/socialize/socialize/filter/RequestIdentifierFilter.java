package com.socialize.socialize.filter;

import com.socialize.socialize.constant.ThreadLocalConstant;
import com.socialize.socialize.context.ThreadLocalContext;
import com.socialize.socialize.util.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.UUID;

@Component
@Order(1)
public class RequestIdentifierFilter extends OncePerRequestFilter {

  private static final Logger LOGGER = LogManager.getLogger(RequestIdentifierFilter.class);

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String requestId = "c-" + System.nanoTime() + UUID.randomUUID();
    String requestTime = DateUtils.getGmtZonedDateTime(ZonedDateTime.now(DateUtils.GMT_ZONE_ID));

    MDC.put(ThreadLocalConstant.REQUEST_ID, requestId);
    request.setAttribute(ThreadLocalConstant.REQUEST_ID_LOGGING, requestId);

    ThreadLocalContext.putValue(ThreadLocalConstant.REQUEST_ID, requestId);
    ThreadLocalContext.putValue(ThreadLocalConstant.REQUEST_TIME, requestTime);

    try {
      filterChain.doFilter(request, response);
      populateRequestAttribute(request);
    } finally {
      ThreadLocalContext.clear();
      MDC.clear();
    }
  }

  private void populateRequestAttribute(HttpServletRequest request) {
    String apiIdentifier =
        (String) ThreadLocalContext.getValue(ThreadLocalConstant.API_IDENTIFIER_KEY);
    String client = (String) ThreadLocalContext.getValue(ThreadLocalConstant.CLIENT_KEY);
    Integer customerId = (Integer) ThreadLocalContext.getValue(ThreadLocalConstant.CUSTOMER_ID);
    if (apiIdentifier != null) {
      request.setAttribute(ThreadLocalConstant.API_IDENTIFIER_KEY, apiIdentifier);
    }
    if (client != null) {
      request.setAttribute(ThreadLocalConstant.CLIENT_KEY, client);
    }
    if (customerId != null) {
      request.setAttribute(ThreadLocalConstant.CUSTOMER_ID, customerId);
    }
  }
}
