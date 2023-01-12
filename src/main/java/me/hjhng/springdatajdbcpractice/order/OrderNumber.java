package me.hjhng.springdatajdbcpractice.order;

import org.springframework.data.relational.core.mapping.Column;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderNumber {

  @Column("orderNumber")
  private final String orderNumber;

  @Column("assistOrderNumber")
  private final String assistOrderNumber;

  @Builder
  public OrderNumber(String orderNumber, String assistOrderNumber) {
    this.orderNumber = orderNumber;
    this.assistOrderNumber = assistOrderNumber;
  }
}