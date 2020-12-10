package com.socialize.socialize.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.socialize.socialize.error.ErrorCodes;
import com.socialize.socialize.exception.SocializeRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonUtils {
  private static final ObjectMapper OBJECT_MAPPER;
  private static final TypeFactory TYPE_FACTORY;

  private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

  static {
    OBJECT_MAPPER = new ObjectMapper();
    OBJECT_MAPPER.enable(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS);
    OBJECT_MAPPER.enable(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS);
    OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    OBJECT_MAPPER.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    OBJECT_MAPPER.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    OBJECT_MAPPER.configure(DeserializationFeature.ACCEPT_FLOAT_AS_INT, false);
    TYPE_FACTORY = TypeFactory.defaultInstance();
  }

  public static <T> T parseJson(String data, Class<T> clazz) {
    try {
      return OBJECT_MAPPER.readValue(data, clazz);
    } catch (IOException e) {
      LOGGER.error("Json syntax error with clazz exception : {} ", Utils.exceptionFormatter(e));
      throw new SocializeRuntimeException(
          ErrorCodes.SOCIALIZE_5000.name(),
          ErrorCodes.SOCIALIZE_5000.getMessage(),
          ErrorCodes.SOCIALIZE_5000.getHttpStatus(),
          e);
    }
  }

  public static <T> T parseJson(String data, TypeReference type) {
    try {
      return (T) OBJECT_MAPPER.readValue(data, type);
    } catch (IOException e) {
      LOGGER.error("Json syntax error with type exception : {} ", Utils.exceptionFormatter(e));
      throw new SocializeRuntimeException(
          ErrorCodes.SOCIALIZE_5000.name(),
          ErrorCodes.SOCIALIZE_5000.getMessage(),
          ErrorCodes.SOCIALIZE_5000.getHttpStatus(),
          e);
    }
  }

  public static String serialiseJson(Object data) {
    try {
      return OBJECT_MAPPER.writeValueAsString(data);
    } catch (JsonProcessingException e) {
      LOGGER.error("error occured in serialing object : {}", Utils.exceptionFormatter(e));
      throw new SocializeRuntimeException(
          ErrorCodes.SOCIALIZE_5000.name(),
          ErrorCodes.SOCIALIZE_5000.getMessage(),
          ErrorCodes.SOCIALIZE_5000.getHttpStatus(),
          e);
    }
  }
}
