package com.socialize.socialize.constant;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Configuration
public class ConstantEnv {

  @Resource public Environment env;

  public Integer CORE_POOL_SIZE;
  public Integer MAX_POOL_SIZE;
  public Integer KEEP_ALIVE_SEC;
  public Integer TIME_OUT_SEC;

  // environment
  public String SOCIALIZE_ENV;

  @PostConstruct
  void init() {

    CORE_POOL_SIZE = Integer.valueOf(env.getProperty("CORE_POOL_SIZE").trim());
    MAX_POOL_SIZE = Integer.valueOf(env.getProperty("MAX_POOL_SIZE").trim());
    KEEP_ALIVE_SEC = Integer.valueOf(env.getProperty("KEEP_ALIVE_SEC").trim());
    TIME_OUT_SEC = Integer.valueOf(env.getProperty("TIME_OUT_SEC").trim());

    SOCIALIZE_ENV = env.getProperty("SOCIALIZE_ENV");
  }
}
