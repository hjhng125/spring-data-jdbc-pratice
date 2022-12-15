package me.hjhng.springdatajdbcpractice;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class MemoBulkRepositoryTest {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  List<Memo> memos;

  @BeforeEach
  void init() {
    for (var i = 1; i <= 10000; ++i) {
      memos.add(Memo.builder()
              .title("title" + i)
              .content("content" + i)
          .build());
    }
  }

  @Test
  void bulk_update_test() {
    String sql = "INSERT INTO Memo (`title`, `content`) values ( ?, ?)";
    jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        ps.setString(1, memos.get(i).getTitle());
        ps.setString(2, memos.get(i).getContent());
      }

      @Override
      public int getBatchSize() {
        return memos.size();
      }
    });
  }

}
