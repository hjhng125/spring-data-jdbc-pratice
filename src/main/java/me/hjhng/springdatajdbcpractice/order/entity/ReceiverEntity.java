package me.hjhng.springdatajdbcpractice.order.entity;

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
public class ReceiverEntity implements Cloneable {

  public static final int ZIP_CODE_MIN = 5;
  public static final int ZIP_CODE_MAX = 6;
  public static final int ADDRESS_MIN = 10;
  public static final int ADDRESS_MAX = 80;

  String name;

  String phoneNumber1;

  String phoneNumber2;

  String zipCode;

  String address1;

  String address2;

}
