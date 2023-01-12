package me.hjhng.springdatajdbcpractice;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import me.hjhng.springdatajdbcpractice.memo.Memo;
import me.hjhng.springdatajdbcpractice.memo.MemoRepository;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class MemoRepositoryTest {

  @Autowired
  private MemoRepository memoRepository;

  Memo saved;

  @BeforeEach
  void init() {
    Memo memo = Memo.builder()
        .title("title")
        .content("content")
        .createdAt(LocalDateTime.now())
        .build();

    saved = memoRepository.save(memo);
  }

  @Test
  void 메모_저장() {
    Memo memo = Memo.builder()
        .title("title")
        .content("content")
        .createdAt(LocalDateTime.now())
        .build();

    Memo saved = memoRepository.save(memo);

    System.out.println("saved = " + saved);

    assertThat(saved.getMemoId()).isNotNull();
    assertThat(saved.getTitle()).isEqualTo("title");
    assertThat(saved.getContent()).isEqualTo("content");
  }

  @Test
  void 메모_조회() {
    memoRepository.findById(saved.getMemoId())
        .ifPresent((saved) -> {
          System.out.println("saved = " + saved);
          assertThat(saved.getTitle()).isEqualTo("title");
          assertThat(saved.getContent()).isEqualTo("content");
        });
  }
}