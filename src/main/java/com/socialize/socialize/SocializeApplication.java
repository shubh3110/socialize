package com.socialize.socialize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.socialize.socialize"})
@SpringBootApplication
public class SocializeApplication {

  private static final Logger log = LoggerFactory.getLogger(SocializeApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(SocializeApplication.class, args);
  }
}
