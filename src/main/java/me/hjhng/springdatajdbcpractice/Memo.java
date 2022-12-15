package me.hjhng.springdatajdbcpractice;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@Table(name = "Memo")
public class Memo {

  @Id
  @Column("memoId")
  private final Long memoId;

  private final String title;

  private final String content;
}
