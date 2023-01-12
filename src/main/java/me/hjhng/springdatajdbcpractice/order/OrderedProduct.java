package me.hjhng.springdatajdbcpractice.order;

import java.util.UUID;

import org.springframework.data.relational.core.mapping.Column;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderedProduct {

  @Column("productId")
  private final UUID productId;

  @Column("productNumber")
  private final String productNumber;

  @Column("productName")
  private final String name;

  @Column("optionNumber")
  private final String optionNumber;

  @Column("optionName")
  private final String option;

  @Column("extraComponentNumber")
  private final String extraComponentNumber;

  @Column("extraComponent")
  private final String extraComponent;

  @Column("extraComponentContains")
  private final boolean extraComponentContains;

  private final String gift;

  @Column("sellerProductCode")
  private final String sellerProductCode;

  @Column("sellerProductCodeExtra1")
  private final String sellerProductCodeExtra1;

  @Column("sellerProductCodeExtra2")
  private final String sellerProductCodeExtra2;

  @Column("sellerProductCodeExtra3")
  private final String sellerProductCodeExtra3;

  @Column("sellerProductCodeExtra4")
  private final String sellerProductCodeExtra4;

  @Column("productAdditionalMessage")
  private final String productAdditionalMessage;

  @Column("buyerAdditionalMessage")
  private final String buyerAdditionalMessage;

  private final Integer quantity;

  @Builder
  public OrderedProduct(UUID productId, String productNumber, String name, String optionNumber, String option, String extraComponentNumber, String extraComponent, boolean extraComponentContains,
      String gift, String sellerProductCode, String sellerProductCodeExtra1, String sellerProductCodeExtra2, String sellerProductCodeExtra3, String sellerProductCodeExtra4,
      String productAdditionalMessage, String buyerAdditionalMessage, Integer quantity) {
    this.productId = productId;
    this.productNumber = productNumber;
    this.name = name;
    this.optionNumber = optionNumber;
    this.option = option;
    this.extraComponentNumber = extraComponentNumber;
    this.extraComponent = extraComponent;
    this.extraComponentContains = extraComponentContains;
    this.gift = gift;
    this.sellerProductCode = sellerProductCode;
    this.sellerProductCodeExtra1 = sellerProductCodeExtra1;
    this.sellerProductCodeExtra2 = sellerProductCodeExtra2;
    this.sellerProductCodeExtra3 = sellerProductCodeExtra3;
    this.sellerProductCodeExtra4 = sellerProductCodeExtra4;
    this.productAdditionalMessage = productAdditionalMessage;
    this.buyerAdditionalMessage = buyerAdditionalMessage;
    this.quantity = quantity;
  }
}
