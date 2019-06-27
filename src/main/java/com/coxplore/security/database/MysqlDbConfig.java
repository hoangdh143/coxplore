//package com.coxplore.security.database;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.env.Environment;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", basePackages = {"com.coxplore.repository"})
//public class MysqlDbConfig {
//    @Autowired
//    private Environment env;
//
//    @Primary
//    @Bean(name = "dataSource")
////    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource dataSource() {
////        DriverManagerDataSource dataSource = new DriverManagerDataSource();
////        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
////        dataSource.setUrl(env.getProperty("spring.datasource.url"));
////        dataSource.setUsername(env.getProperty("spring.datasource.username"));
////        dataSource.setPassword(env.getProperty("spring.datasource.password"));
//
//        HikariConfig config = new HikariConfig();
//        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        config.setSchema("coxplore");
//        config.setJdbcUrl(String.format("jdbc:mysql:///%s", "coxplore"));
//        config.setUsername("root"); // e.g. "root", "postgres"
//        config.setPassword("root"); // e.g. "my-password"
//        config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.mysql.SocketFactory");
//        config.addDataSourceProperty("cloudSqlInstance", "coxplore-244009:us-west1:coxplore-mysql-db-3");
//        config.addDataSourceProperty("useSSL", "false");
//
//        return new HikariDataSource(config);
//    }
//
//    @Primary
//    @Bean(name = "entityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
//                                                                       @Qualifier("dataSource") DataSource dataSource) {
//        return builder.dataSource(dataSource).packages("com.coxplore.model").build();
//    }
//
//    @Primary
//    @Bean(name = "transactionManager")
//    public PlatformTransactionManager transactionManager(
//            @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
//}
