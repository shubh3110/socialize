package com.socialize.socialize.db.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "com.socialize.socialize.dao.slave",
    entityManagerFactoryRef = "socializeSlaveEntityManagerFactory",
    transactionManagerRef = "socializeSlaveEntityTransactionManager")
public class SocializeSlaveDataSourceConfiguration {

  @Resource Environment env;

  @Bean(name = "socializeSlaveEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean socializeSlaveEntityManagerFactory(
      final EntityManagerFactoryBuilder builder,
      final @Qualifier("socialize-slave-db") DataSource dataSource) {

    Map<String, Object> properties = new HashMap<>();

    properties.put(
        "hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto").trim());
    properties.put(
        "hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
    properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));

    return builder
        .dataSource(dataSource)
        .packages("com.socialize.socialize.entity")
        .persistenceUnit("socializeSlaveDB")
        .properties(properties)
        .build();
  }

  @Bean(name = "socialize-slave-db")
  public DataSource socializeSlaveDatasource() {

    HikariConfig poolConfig = new HikariConfig();
    poolConfig.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
    poolConfig.setJdbcUrl(env.getProperty("database.socialize.slave.datasource.url"));
    poolConfig.setUsername(env.getProperty("database.socialize.slave.datasource.username"));
    poolConfig.setPassword(env.getProperty("database.socialize.slave.datasource.password"));

    // https://github.com/brettwooldridge/HikariCP
    poolConfig.setPoolName("slavePool");
    poolConfig.setMaximumPoolSize(10);
    poolConfig.setMinimumIdle(10);
    poolConfig.setConnectionTimeout(6000);
    poolConfig.setIdleTimeout(300000);
    // wait_timeout at prod = 1200 sec
    poolConfig.setMaxLifetime(1100000);

    return new HikariDataSource(poolConfig);
  }

  @Bean(name = "socializeSlaveEntityTransactionManager")
  public PlatformTransactionManager socializeSlaveTransactionManager(
      @Qualifier("socializeSlaveEntityManagerFactory")
          EntityManagerFactory socializeSlaveEntityTransactionManager) {

    return new JpaTransactionManager(socializeSlaveEntityTransactionManager);
  }
}
