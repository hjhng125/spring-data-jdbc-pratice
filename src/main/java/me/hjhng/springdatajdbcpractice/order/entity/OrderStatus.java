package me.hjhng.springdatajdbcpractice.order.entity;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

/**
 * @Description
 * @Since 2019-01-04
 * @Version 1.0
 * @COPYRIGHT © TOGLE ALL RIGHTS RESERVED.
 */
@Getter
public enum OrderStatus {
  INITIAL("신규주문", 1),
  DELAYED("발송지연", 2),
  PREPARED("발송준비", 3),
  DISPATCHED("출고중", 4),
  SENT("배송중", 5),
  DELIVERY_COMPLETED("배송완료", 6),
  PURCHASED("구매확정", 7),
  CANCELED("취소됨", 8),
  ;

  private final Integer step;
  private final String name;

  OrderStatus(String name, Integer step) {
    this.name = name;
    this.step = step;
  }

}
