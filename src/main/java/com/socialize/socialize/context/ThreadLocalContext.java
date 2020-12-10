package com.socialize.socialize.context;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalContext {
  private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<>();

  public static Object getValue(String key) {
    Map<String, Object> stringObjectMap = THREAD_LOCAL.get();
    if (stringObjectMap != null) {
      return stringObjectMap.get(key);
    }
    return null;
  }

  public static void putValue(String key, Object value) {

    if (StringUtils.isEmpty(key) || value == null) {
      return;
    }

    Map<String, Object> stringObjectMap = THREAD_LOCAL.get();
    if (stringObjectMap == null) {
      stringObjectMap = new HashMap<>();
      THREAD_LOCAL.set(stringObjectMap);
    }
    stringObjectMap.put(key, value);
  }

  public static void clear() {
    Map<String, Object> stringObjectMap = THREAD_LOCAL.get();
    if (stringObjectMap != null) {
      stringObjectMap.clear();
    }
    THREAD_LOCAL.remove();
  }
}
