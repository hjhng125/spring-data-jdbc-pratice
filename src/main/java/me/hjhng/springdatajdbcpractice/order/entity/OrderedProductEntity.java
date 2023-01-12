package me.hjhng.springdatajdbcpractice.order.entity;

import java.util.UUID;

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
@EqualsAndHashCode(doNotUseGetters = true)
public class OrderedProductEntity implements Cloneable {

  UUID productId;

  String productNumber;

  String name;

  String optionNumber;

  String option;

  // 추가구성품 번호
  String extraComponentNumber;

  // 추가구성품명
  String extraComponent;

  //추가구성품 포함 여부 (추가 구성품(extraComponent)가 있거나, 단독추가구성품(11번가 처럼 독립적인 추가구성품이 orderId를 갖는 경우) 인 경우)true
  boolean extraComponentContains;

  String gift;

  String sellerProductCode;

  String sellerProductCodeExtra1;

  String sellerProductCodeExtra2;

  String sellerProductCodeExtra3;

  String sellerProductCodeExtra4;

  String productAdditionalMessage;

  String buyerAdditionalMessage;

  Integer quantity;

}
