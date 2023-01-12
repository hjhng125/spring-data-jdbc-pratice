package me.hjhng.springdatajdbcpractice.memo;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Memo {

  @Id
  private final Long memoId;

  private final String title;

  private final String content;

  private final LocalDateTime createdAt;

  private final LocalDateTime updatedAt;
}
