package me.hjhng.springdatajdbcpractice.order;

import org.springframework.data.relational.core.mapping.Column;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DeliveryInfo {

  @Column("deliveryMethod")
  private final String deliveryMethod;

  @Column("parcelCode")
  private final String parcelCode;

  @Column("invoiceNumber")
  private final String invoiceNumber;

  @Builder
  public DeliveryInfo(String deliveryMethod, String parcelCode, String invoiceNumber) {
    this.deliveryMethod = deliveryMethod;
    this.parcelCode = parcelCode;
    this.invoiceNumber = invoiceNumber;
  }
}
