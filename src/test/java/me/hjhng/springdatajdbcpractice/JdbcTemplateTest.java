package me.hjhng.springdatajdbcpractice;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import me.hjhng.springdatajdbcpractice.config.DataJdbcConfiguration;

@SpringBootTest(classes = DataJdbcConfiguration.class)
@AutoConfigureTestDatabase(replace = NONE)
@ExtendWith(SpringExtension.class)
class JdbcTemplateTest {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Test
  void test() {
    jdbcTemplate.execute("SELECT * FROM Orders");
  }
}
