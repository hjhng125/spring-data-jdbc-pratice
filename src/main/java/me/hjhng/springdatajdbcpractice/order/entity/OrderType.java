package me.hjhng.springdatajdbcpractice.order.entity;

/**
 * @Description
 * @Since 2021-03-11
 * @Version 1.0
 * @COPYRIGHT © TOGLE ALL RIGHTS RESERVED.
 */
public enum OrderType {

  NORMAL, // 일반 주문

  PRESENT, // 선물하기 주문

  VOUCHER, // 상품권,이용권,e쿠폰 등 주문 ( 일반주문과 다르게 처리해야하는 주문 )
  ;

}
