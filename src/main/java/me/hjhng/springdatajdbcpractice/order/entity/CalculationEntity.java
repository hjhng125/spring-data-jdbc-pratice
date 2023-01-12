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
public class CalculationEntity implements Cloneable {

  /** 정산예정금액 */
  Long calculatedAmount;

  /** 쇼핑몰 수수료 */
  Long fee;

  /** 쇼핑몰 수수료 음수(-1), 양수(1) 구분 */
  Integer feeNumericType;

}
