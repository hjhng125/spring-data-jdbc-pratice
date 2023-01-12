package me.hjhng.springdatajdbcpractice.config;

import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import me.hjhng.springdatajdbcpractice.UUIDBinaryUtil;

@Configuration
public class DataJdbcConfiguration {

  @Bean
  public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  @Bean
  public NamedParameterJdbcOperations namedParameterJdbcOperations(DataSource dataSource) {
    return new NamedParameterJdbcTemplate(dataSource);
  }

//  @Override
//  public JdbcCustomConversions jdbcCustomConversions() {
//    return new JdbcCustomConversions(List.of(UUIDToBinary16Converter.INSTANCE, Binary16ToUUIDConverter.INSTANCE));
//  }

  @WritingConverter
  enum UUIDToBinary16Converter implements Converter<UUID, byte[]> {

    INSTANCE,
    ;

    @Override
    public byte[] convert(UUID source) {
      return UUIDBinaryUtil.toBinary(source);
    }
  }

  @ReadingConverter
  enum Binary16ToUUIDConverter implements Converter<byte[], UUID> {

    INSTANCE,
    ;

    @Override
    public UUID convert(byte[] source) {
      return UUIDBinaryUtil.toUUID(source);
    }
  }
}
