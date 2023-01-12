package me.hjhng.springdatajdbcpractice.order;

import org.springframework.data.relational.core.mapping.Column;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Calculation {

  @Column("calculatedAmount")
  private final Long calculatedAmount;

  private final Long fee;

  @Column("feeNumericType")
  private final Integer feeNumericType;

  @Builder
  public Calculation(Long calculatedAmount, Long fee, Integer feeNumericType) {
    this.calculatedAmount = calculatedAmount;
    this.fee = fee;
    this.feeNumericType = feeNumericType;
  }
}
