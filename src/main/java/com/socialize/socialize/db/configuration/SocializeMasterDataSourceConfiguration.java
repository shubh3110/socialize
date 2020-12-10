package com.socialize.socialize.db.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
    basePackages = "com.socialize.socialize.dao.master",
    entityManagerFactoryRef = "socializeMasterEntityManagerFactory",
    transactionManagerRef = "socializeMasterEntityTransactionManager")
public class SocializeMasterDataSourceConfiguration {

  private static final Logger log =
      LoggerFactory.getLogger(SocializeMasterDataSourceConfiguration.class);

  @Resource Environment env;

  @Primary
  @Bean(name = "socializeMasterEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean socializeMasterEntityManagerFactory(
      final EntityManagerFactoryBuilder builder,
      final @Qualifier("socialize-master-db") DataSource dataSource) {

    Map<String, Object> properties = new HashMap<>();

    properties.put(
        "hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto").trim());
    properties.put(
        "hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
    properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));

    return builder
        .dataSource(dataSource)
        .packages("com.socialize.socialize.entity")
        .persistenceUnit("socializeMasterDB")
        .properties(properties)
        .build();
  }

  @Primary
  @Bean(name = "socialize-master-db")
  public DataSource socializeMasterDatasource() {
    String url = env.getProperty("database.socialize.master.datasource.url");
    String username = env.getProperty("database.socialize.master.datasource.username");
    String password = env.getProperty("database.socialize.master.datasource.password");
    log.info("Socialize db url: {}, username: {} and password: {}", url, username, password);

    HikariConfig poolConfig = new HikariConfig();
    poolConfig.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
    poolConfig.setJdbcUrl(url);
    poolConfig.setUsername(username);
    poolConfig.setPassword(password);

    // https://github.com/brettwooldridge/HikariCP
    poolConfig.setPoolName("masterPool");
    poolConfig.setMaximumPoolSize(10);
    poolConfig.setMinimumIdle(10);
    poolConfig.setConnectionTimeout(6000);
    poolConfig.setIdleTimeout(300000);
    // wait_timeout at prod = 1200 sec
    poolConfig.setMaxLifetime(1100000);

    return new HikariDataSource(poolConfig);
  }

  @Primary
  @Bean(name = "socializeMasterEntityTransactionManager")
  public PlatformTransactionManager socializeMasterTransactionManager(
      @Qualifier("socializeMasterEntityManagerFactory")
          EntityManagerFactory socializeMasterEntityTransactionManager) {

    return new JpaTransactionManager(socializeMasterEntityTransactionManager);
  }
}
