package me.hjhng.springdatajdbcpractice.order.entity;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converts;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Since 2021/03/26
 * @Version 1.0
 * @COPYRIGHT © TOGLE ALL RIGHTS RESERVED.
 */
@Slf4j
@Entity
@Table(name = "Orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class OrderEntity {

  @Id
  @AttributeOverride(name = "id", column = @Column(name = "id", columnDefinition = "BINARY(16)"))
  UUID id;

  @AttributeOverride(name = "id", column = @Column(name = "syncId", columnDefinition = "BINARY(16)"))
  UUID syncId;

  @AttributeOverride(name = "id", column = @Column(name = "creationSyncId", columnDefinition = "BINARY(16)"))
  UUID creationSyncId;

  @AttributeOverride(name = "id", column = @Column(name = "collectId", columnDefinition = "BINARY(16)"))
  UUID collectId;

  @AttributeOverride(name = "id", column = @Column(name = "packingUnitId", columnDefinition = "BINARY(16)"))
  UUID packingUnitId;

  @Enumerated(EnumType.STRING)
  MallType mallType;

  @AttributeOverride(name = "id", column = @Column(name = "mallAccountId", columnDefinition = "BINARY(16)"))
  UUID mallAccountId;

  @AttributeOverride(name = "id", column = @Column(name = "corporationId", columnDefinition = "BINARY(16)"))
  UUID corporationId;

  @Enumerated(EnumType.STRING)
  OrderStatus status;

  String rawStatus;

  @Enumerated(EnumType.STRING)
  OrderType orderType;

  boolean inInitial;

  boolean prepared;

  boolean inProgress;

  boolean finished;

  boolean canceled;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "orderNumber", column = @Column(name = "orderNumber")),
    @AttributeOverride(name = "assistOrderNumber", column = @Column(name = "assistOrderNumber")),
  })
  OrderNumberEntity orderNumber;

  String orderSequence;

  String cartNumber;

  boolean presentReceived;

  @Embedded
  @AttributeOverrides({ @AttributeOverride(name = "deliveryMethod", column = @Column(name = "deliveryMethod")),
    @AttributeOverride(name = "parcelCode", column = @Column(name = "parcelCode")),
    @AttributeOverride(name = "invoiceNumber", column = @Column(name = "invoiceNumber")), })
  DeliveryInfoEntity deliveryInfo;

  String deliveryMessage;

  String bundleCandidate;

  @Embedded
  @AttributeOverrides({ //
    @AttributeOverride(name = "name", column = @Column(name = "ordererName")), //
    @AttributeOverride(name = "mallId", column = @Column(name = "ordererMallId")), //
    @AttributeOverride(name = "phoneNumber1", column = @Column(name = "ordererPhoneNumber1")), //
    @AttributeOverride(name = "phoneNumber2", column = @Column(name = "ordererPhoneNumber2")), //
  })
  OrdererEntity orderer;

  @Embedded
  @AttributeOverrides({ //
    @AttributeOverride(name = "productId.id", column = @Column(name = "productId")), //
    @AttributeOverride(name = "productNumber", column = @Column(name = "productNumber")), //
    @AttributeOverride(name = "name", column = @Column(name = "productName")), //
    @AttributeOverride(name = "optionNumber", column = @Column(name = "optionNumber")), //
    @AttributeOverride(name = "option", column = @Column(name = "optionName")), //
    @AttributeOverride(name = "extraComponentNumber", column = @Column(name = "extraComponentNumber")), //
    @AttributeOverride(name = "extraComponent", column = @Column(name = "extraComponent")), //
    @AttributeOverride(name = "extraComponentContains", column = @Column(name = "extraComponentContains")), //
    @AttributeOverride(name = "gift", column = @Column(name = "gift")), //
    @AttributeOverride(name = "sellerProductCode", column = @Column(name = "sellerProductCode")), //
    @AttributeOverride(name = "sellerProductCodeExtra1", column = @Column(name = "sellerProductCodeExtra1")), //
    @AttributeOverride(name = "sellerProductCodeExtra2", column = @Column(name = "sellerProductCodeExtra2")), //
    @AttributeOverride(name = "sellerProductCodeExtra3", column = @Column(name = "sellerProductCodeExtra3")), //
    @AttributeOverride(name = "sellerProductCodeExtra4", column = @Column(name = "sellerProductCodeExtra4")), //
    @AttributeOverride(name = "productAdditionalMessage", column = @Column(name = "productAdditionalMessage")), //
    @AttributeOverride(name = "buyerAdditionalMessage", column = @Column(name = "buyerAdditionalMessage")), //
    @AttributeOverride(name = "quantity", column = @Column(name = "quantity")), //
  })
  OrderedProductEntity product;

  @Embedded
  @AttributeOverrides({ //
    @AttributeOverride(name = "name", column = @Column(name = "receiverName")), //
    @AttributeOverride(name = "phoneNumber1", column = @Column(name = "receiverPhoneNumber1")), //
    @AttributeOverride(name = "phoneNumber2", column = @Column(name = "receiverPhoneNumber2")), //
    @AttributeOverride(name = "zipCode", column = @Column(name = "receiverZipCode")), //
    @AttributeOverride(name = "address1", column = @Column(name = "receiverAddress1")), //
    @AttributeOverride(name = "address2", column = @Column(name = "receiverAddress2")), //
  })
  ReceiverEntity receiver;

  @Embedded
  @AttributeOverrides({ @AttributeOverride(name = "productCharge", column = @Column(name = "productCharge")),
    @AttributeOverride(name = "productDiscountCharge", column = @Column(name = "productDiscountCharge")),
    @AttributeOverride(name = "orderDiscountCharge", column = @Column(name = "orderDiscountCharge")),
    @AttributeOverride(name = "point", column = @Column(name = "orderPointCharge")),
    @AttributeOverride(name = "credit", column = @Column(name = "orderCreditCharge")),
    @AttributeOverride(name = "deliveryChargeType", column = @Column(name = "deliveryChargeType")),
    @AttributeOverride(name = "basicDeliveryCharge", column = @Column(name = "basicDeliveryCharge")),
    @AttributeOverride(name = "distanceDeliveryCharge", column = @Column(name = "distanceDeliveryCharge")),
    @AttributeOverride(name = "paymentAmount", column = @Column(name = "paymentAmount")),
    @AttributeOverride(name = "paidAt", column = @Column(name = "paidAt")), })
  PaymentEntity payment;

  @Embedded
  @AttributeOverrides({ @AttributeOverride(name = "calculatedAmount", column = @Column(name = "calculatedAmount")),
    @AttributeOverride(name = "fee", column = @Column(name = "fee")),
    @AttributeOverride(name = "feeNumericType", column = @Column(name = "feeNumericType")), })
  CalculationEntity calculation;

  String customsClearanceCode;

  String customsClearancePhoneNumber;

  OffsetDateTime orderedAt;

  OffsetDateTime expectSendDate;

  OffsetDateTime collectedAt;

  OffsetDateTime createdAt;

  OffsetDateTime syncBaseDate;

  OffsetDateTime sentAt;

  boolean preparable;

  boolean delayable;

  boolean deliverable;

  boolean visitable;

  boolean deliveryChangeable;

  boolean deliveryCompletable;

  @Enumerated(EnumType.STRING)
  VisitConfirmType visitConfirmType;

  String rawData;

  @PrePersist
  public void onPersist() {
    if (createdAt == null) {
      createdAt = OffsetDateTime.now();
    }
  }

}
