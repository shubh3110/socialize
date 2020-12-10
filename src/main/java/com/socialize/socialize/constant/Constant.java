package com.socialize.socialize.constant;

import java.util.TimeZone;

public class Constant {
  public static final String RESPONSE_ERROR_HEADER = "X-ERROR-CODE";
  public static final String RESPONSE_API_NAME_HEADER = "METHOD-NAME";
  public static final String SERVICE_IS_HEALTHY = "The service is healthy";
  public static final String SERVICE_IS_NOT_HEALTHY = "The service is not healthy";
  public static final String SUCCESS = "success";
  public static final String FAILURE = "failure";
  public static final String OK = "OK";
  public static final String FAIL = "FAIL";
  public static final TimeZone UTC_TIMEZONE = TimeZone.getTimeZone("UTC");
  public static final String UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

  public static final String SPRING_LOCAL_PROFILE = "local";
  public static final String SPRING_DEV1_PROFILE = "dev1";
  public static final String SPRING_DEV2_PROFILE = "dev2";
  public static final String SPRING_STAGING_PROFILE = "staging";
  public static final String SPRING_PROD_PROFILE = "production-mb";
  public static final String INTERNAL_SERVER_ERROR_MESSAGE =
      "Something went wrong, please try again";

  // month string calculation variables
  public static final int DAYS_IN_MONTH = 30;
  public static final String MONTH_STRING = "month";
  public static final String MONTHS_STRING = "months";
  public static final String DATE_FORMAT = "dd MMMM YYYY";
  public static final String VALID_TILL_STRING = "Valid till %s";
}
