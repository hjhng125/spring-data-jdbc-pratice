package me.hjhng.springdatajdbcpractice.order.entity;

import java.time.OffsetDateTime;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * @Description
 * @Since 2019-03-08
 * @Version 1.0
 * @COPYRIGHT © TOGLE ALL RIGHTS RESERVED.
 */
@Getter
@Setter
@Builder
@ToString
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class PaymentEntity implements Cloneable {

  // 주문금액 ( 상품단가+옵션단가 * 상품개수 )
  Long productCharge;

  // 상품 1개당 할인금액의 총합
  Long productDiscountCharge;

  // 주문에 적용된 할인금액
  Long orderDiscountCharge;

  Long point;

  Long credit;

  String deliveryChargeType;

  // 기본 배송비
  Long basicDeliveryCharge;

  // 도서산간 배송비
  Long distanceDeliveryCharge;

  // 최종 결제 금액 ( (productCharge - productDiscountCharge - orderDiscountCharge) + ( basicDeliveryCharge + distanceDeliveryCharge )
  Long paymentAmount;

  OffsetDateTime paidAt;


}
