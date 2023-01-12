package me.hjhng.springdatajdbcpractice;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StopWatch;

import me.hjhng.springdatajdbcpractice.memo.Memo;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class MemoBulkRepositoryTest {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private final List<Memo> memos = new ArrayList<>();

  private final String sql = "INSERT INTO Memo (`title`, `content`, `createdAt`) values ( ?, ?, ? )";

  @BeforeEach
  void init() {
    for (int i = 1; i <= 50000; ++i) {
      memos.add(Memo.builder()
              .title("title")
              .content("content")
              .createdAt(LocalDateTime.now())
          .build());
    }
  }

  @Test
  void bulk_insert_test() {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        ps.setString(1, memos.get(i).getTitle());
        ps.setString(2, memos.get(i).getContent());
        ps.setString(3, memos.get(i).getCreatedAt().toString());
      }

      @Override
      public int getBatchSize() {
        return memos.size();
      }
    });
    stopWatch.stop();

    System.out.println("bulk_insert_test -> Total time in seconds: " + stopWatch.getTotalTimeSeconds());
  }

  @Test
  void bulk_insert_async_test() throws ExecutionException, InterruptedException {
    AtomicInteger atomicInteger = new AtomicInteger();
    StopWatch stopWatch = new StopWatch();
    CompletableFuture[] completableFutures = memos.stream()
        .collect(Collectors.groupingBy(t -> atomicInteger.getAndIncrement() / 1000))
        .values()
        .stream()
        .map(memo -> CompletableFuture.runAsync(() -> {
          jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
              ps.setString(1, memo.get(i)
                  .getTitle());
              ps.setString(2, memo.get(i)
                  .getContent());
              ps.setString(3, memo.get(i).getCreatedAt().toString());
            }

            @Override
            public int getBatchSize() {
              return memo.size();
            }
          });
        }))
        .toArray(CompletableFuture[]::new);

    CompletableFuture<Void> run = CompletableFuture.allOf(completableFutures);

    stopWatch.start();
    run.get();
    stopWatch.stop();

    System.out.println("bulk_insert_async_test -> Total time in seconds: " + stopWatch.getTotalTimeSeconds());
  }

}
