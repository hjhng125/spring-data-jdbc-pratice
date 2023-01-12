package me.hjhng.springdatajdbcpractice.order.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import me.hjhng.springdatajdbcpractice.order.Calculation;
import me.hjhng.springdatajdbcpractice.order.DeliveryInfo;
import me.hjhng.springdatajdbcpractice.order.Order;
import me.hjhng.springdatajdbcpractice.order.OrderNumber;
import me.hjhng.springdatajdbcpractice.order.OrderedProduct;
import me.hjhng.springdatajdbcpractice.order.Orderer;
import me.hjhng.springdatajdbcpractice.order.Payment;
import me.hjhng.springdatajdbcpractice.order.Receiver;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = OrderEntityMapperImpl.class)
class OrderEntityMapperTest {

  @Autowired
  OrderEntityMapperImpl mapper;

  @Test
  void mapperTest() {
    Order build = Order.builder()
        .id(UUID.randomUUID())
        .syncId(UUID.randomUUID())
        .creationSyncId(UUID.randomUUID())
        .collectId(UUID.randomUUID())
        .packingUnitId(UUID.randomUUID())
        .mallAccountId(UUID.randomUUID())
        .corporationId(UUID.randomUUID())
        .mallType(MallType.AUCTION.name())
        .status(OrderStatus.INITIAL.name())
        .rawData("rawStatus")
        .orderType(OrderType.NORMAL.name())
        .orderNumber(OrderNumber.builder()
            .assistOrderNumber("assist")
            .orderNumber("orderNumber")
            .build())
        .orderSequence("orderSequence")
        .cartNumber("cartNumber")
        .deliveryInfo(DeliveryInfo.builder()
            .deliveryMethod("PARCEL")
            .invoiceNumber("invoiceNumber")
            .parcelCode("DHL_DE")
            .build())
        .deliveryMessage("deliveryMessage")
        .bundleCandidate("bundle")
        .orderer(Orderer.builder()
            .mallId("mallId" )
            .name("name" )
            .phoneNumber1("phoneNumber1" )
            .build())
        .receiver(Receiver.builder()
            .address1("address1")
            .name("name")
            .zipCode("zip")
            .build())
        .product(OrderedProduct.builder()
            .productId(UUID.randomUUID())
            .name("productName")
            .build())
        .payment(Payment.builder()
            .basicDeliveryCharge(0L)
            .credit(0L)
            .deliveryChargeType("PAY_LATER")
            .paidAt(LocalDateTime.now())
            .point(0L)
            .build())
        .calculation(Calculation.builder()
            .calculatedAmount(0L)
            .fee(0L)
            .build())
        .customsClearanceCode("clearanceCode")
        .orderedAt(LocalDateTime.now())
        .collectedAt(LocalDateTime.now())
        .syncBaseDate(LocalDateTime.now())
        .build();

    OrderEntity orderEntity = mapper.toEntity(build);
    assertThat(orderEntity).isNotNull();
  }
}