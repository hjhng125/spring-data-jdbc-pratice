package me.hjhng.springdatajdbcpractice.order;

import org.springframework.data.relational.core.mapping.Column;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Orderer {

  @Column("name")
  private final String name;

  @Column("mallId")
  private final String mallId;

  @Column("phoneNumber1")
  private final String phoneNumber1;

  @Column("phoneNumber2")
  private final String phoneNumber2;

  @Builder
  public Orderer(String name, String mallId, String phoneNumber1, String phoneNumber2) {
    this.name = name;
    this.mallId = mallId;
    this.phoneNumber1 = phoneNumber1;
    this.phoneNumber2 = phoneNumber2;
  }
}
