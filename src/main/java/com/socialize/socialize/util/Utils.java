package com.socialize.socialize.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.helpers.MessageFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {
  private static final Logger LOG = LogManager.getLogger(Utils.class);

  private static DateFormat gmtFormat;

  static {
    gmtFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    TimeZone gmtTime = TimeZone.getTimeZone("GMT");
    gmtFormat.setTimeZone(gmtTime);
  }

  public static Date convertToGmt(Date date) throws ParseException {
    return stringToGmtDate(gmtDateToString(date));
  }

  public static String gmtDateToString(Date date) {
    return gmtFormat.format(date);
  }

  public static Date stringToGmtDate(String date) throws ParseException {
    try {
      return gmtFormat.parse(date);
    } catch (ParseException e) {
      throw e;
    }
  }

  public static String exceptionFormatter(Throwable e) {
    StringBuilder sb = new StringBuilder();
    appendException(e, sb);
    Throwable cause = e.getCause();
    if (cause != null) {
      while (cause.getCause() != null) {
        appendException(cause, sb);
        cause = cause.getCause();
      }
    }
    return sb.toString();
  }

  private static void appendException(Throwable e, StringBuilder sb) {
    sb.append(" |||| Exception : " + e.getClass());
    if (e.getMessage() != null) {
      sb.append(" | Message : " + e.getMessage());
    }
    if (e.getCause() != null) {
      sb.append(" | Cause : " + e.getCause());
    }
    if (e.getStackTrace() != null) {
      sb.append(" | StackTrace : ");
      for (StackTraceElement ste : e.getStackTrace()) {
        sb.append(ste.toString());
        sb.append("                 ");
      }
    }
  }

  public static String replaceStringPlaceHolders(String message, String... arguments) {
    return MessageFormatter.arrayFormat(message, arguments).getMessage();
  }

  public static <K, V> Map<K, V> initializeEmptyIfNull(Map<K, V> map) {
    if (map == null) {
      return new HashMap<K, V>();
    } else {
      return map;
    }
  }

  public static <T> List<T> initializeEmptyIfNull(List<T> list) {
    if (list == null) {
      return new ArrayList<T>();
    } else {
      return list;
    }
  }

  public static boolean isNumber(Object number) {
    boolean isNumber = false;
    if (number instanceof Integer) {
      isNumber = true;
    }
    if (number instanceof String) {
      try {
        Long.parseLong((String) number);
        isNumber = true;
      } catch (NumberFormatException e) {
      }
    }
    return isNumber;
  }
}
