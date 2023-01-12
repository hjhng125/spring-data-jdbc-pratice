package me.hjhng.springdatajdbcpractice.order;

import java.time.LocalDateTime;

import org.springframework.data.relational.core.mapping.Column;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Payment {

  @Column("productCharge")
  private final Long productCharge;

  @Column("productDiscountCharge")
  private final Long productDiscountCharge;

  @Column("orderDiscountCharge")
  private final Long orderDiscountCharge;

  @Column("orderPointCharge")
  private final Long point;

  @Column("orderCreditCharge")
  private final Long credit;

  @Column("deliveryChargeType")
  private final String deliveryChargeType;

  @Column("basicDeliveryCharge")
  private final Long basicDeliveryCharge;

  @Column("distanceDeliveryCharge")
  private final Long distanceDeliveryCharge;

  @Column("paymentAmount")
  private final Long paymentAmount;

  @Column("paidAt")
  private final LocalDateTime paidAt;

  @Builder
  public Payment(Long productCharge, Long productDiscountCharge, Long orderDiscountCharge, Long point, Long credit, String deliveryChargeType, Long basicDeliveryCharge, Long distanceDeliveryCharge,
      Long paymentAmount, LocalDateTime paidAt) {
    this.productCharge = productCharge;
    this.productDiscountCharge = productDiscountCharge;
    this.orderDiscountCharge = orderDiscountCharge;
    this.point = point;
    this.credit = credit;
    this.deliveryChargeType = deliveryChargeType;
    this.basicDeliveryCharge = basicDeliveryCharge;
    this.distanceDeliveryCharge = distanceDeliveryCharge;
    this.paymentAmount = paymentAmount;
    this.paidAt = paidAt;
  }
}
