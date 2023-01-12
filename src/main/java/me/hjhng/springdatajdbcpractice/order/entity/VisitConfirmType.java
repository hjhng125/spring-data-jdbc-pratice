package me.hjhng.springdatajdbcpractice.order.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * @Description
 * @Since 2019-07-31
 * @Version 1.0
 * @COPYRIGHT © TOGLE ALL RIGHTS RESERVED.
 */
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum VisitConfirmType {
  NOT_PERMITTED("허용안됨"), //
  ORDER_NUMBER_CODE("주문번호 단위로 확인코드 처리"), //
  ORDER_ID_CODE("주문단위로 확인코드 처리"), //
  ORDER_NUMBER("합포장단위로 확인코드 없이 처리"), //
  ORDER_ID("주문단위로 확인코드 없이 처리"); //

  final String desc;
}
