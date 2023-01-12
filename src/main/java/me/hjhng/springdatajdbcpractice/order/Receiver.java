package me.hjhng.springdatajdbcpractice.order;

import org.springframework.data.relational.core.mapping.Column;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Receiver {

  @Column("receiverName")
  private final String name;

  @Column("receiverPhoneNumber1")
  private final String phoneNumber1;

  @Column("receiverPhoneNumber2")
  private final String phoneNumber2;

  @Column("receiverZipCode")
  private final String zipCode;

  @Column("receiverAddress1")
  private final String address1;

  @Column("receiverAddress2")
  private final String address2;

  @Builder
  public Receiver(String name, String phoneNumber1, String phoneNumber2, String zipCode, String address1, String address2) {
    this.name = name;
    this.phoneNumber1 = phoneNumber1;
    this.phoneNumber2 = phoneNumber2;
    this.zipCode = zipCode;
    this.address1 = address1;
    this.address2 = address2;
  }
}
