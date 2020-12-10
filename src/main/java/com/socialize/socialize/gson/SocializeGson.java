package com.socialize.socialize.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

public class SocializeGson {
  private static final Logger LOGGER = LoggerFactory.getLogger(SocializeGson.class);

  private static Gson gson = null;

  static {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.serializeNulls();
    gson = gsonBuilder.create();
  }

  public static Gson getGson() {
    return gson;
  }

  public static <T> T fromJson(String jsonString, Class<T> type) {
    try {
      T obj = gson.fromJson(jsonString, type);
      return obj;
    } catch (JsonSyntaxException ex) {
      LOGGER.error("Json syntax error exception :: {}", ex);
      throw ex;
    }
  }

  public static <T> T fromJson(String jsonString, Type type) {
    try {
      T obj = gson.fromJson(jsonString, type);
      return obj;
    } catch (JsonSyntaxException ex) {
      LOGGER.error("Json syntax error exception :: {}", ex);
      throw ex;
    }
  }

  public static <T> String toJson(T obj) {
    try {
      return gson.toJson(obj);
    } catch (JsonIOException ex) {
      throw ex;
    }
  }
}
