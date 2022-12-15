//package me.hjhng.springdatajdbcpractice;
//
//import javax.sql.DataSource;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
//import org.springframework.transaction.TransactionManager;
//
//@Configuration
//public class AppConfig extends AbstractJdbcConfiguration {
//
//  @Bean
//  public DataSource dataSource() {
//    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
//    return builder.setType(EmbeddedDatabaseType.H2)
//        .build();
//  }
//
//  @Bean
//  public NamedParameterJdbcOperations namedParameterJdbcOperations(DataSource dataSource) {
//    return new NamedParameterJdbcTemplate(dataSource);
//  }
//
//  @Bean
//  public TransactionManager transactionManager(DataSource dataSource) {
//    return new DataSourceTransactionManager(dataSource);
//  }
//}
