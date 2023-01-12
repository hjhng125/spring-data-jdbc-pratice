package me.hjhng.springdatajdbcpractice;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import me.hjhng.springdatajdbcpractice.order.Calculation;
import me.hjhng.springdatajdbcpractice.order.DeliveryInfo;
import me.hjhng.springdatajdbcpractice.order.Order;
import me.hjhng.springdatajdbcpractice.order.OrderNumber;
import me.hjhng.springdatajdbcpractice.order.OrderRepository;
import me.hjhng.springdatajdbcpractice.order.OrderedProduct;
import me.hjhng.springdatajdbcpractice.order.Orderer;
import me.hjhng.springdatajdbcpractice.order.Payment;
import me.hjhng.springdatajdbcpractice.order.Receiver;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class OrderRepositoryTest {

  @Autowired
  private OrderRepository repository;

  private Order order;

  @BeforeEach
  void init() {
    for (int i = 1; i <= 1; ++i) {
      order = Order.builder()
          .id(UUID.randomUUID())
          .syncId(UUID.randomUUID())
          .creationSyncId(UUID.randomUUID())
          .collectId(UUID.randomUUID())
          .packingUnitId(UUID.randomUUID())
          .mallAccountId(UUID.randomUUID())
          .corporationId(UUID.randomUUID())
          .mallType("mallType")
          .status("status")
          .rawData("rawStatus")
          .orderType("orderType")
          .orderNumber(OrderNumber.builder()
              .assistOrderNumber("assist")
              .orderNumber("orderNumber")
              .build())
          .orderSequence("orderSequence")
          .cartNumber("cartNumber")
          .deliveryInfo(DeliveryInfo.builder()
              .deliveryMethod("deliveryMethod")
              .invoiceNumber("invoiceNumber")
              .parcelCode("parcelCode")
              .build())
          .deliveryMessage("deliveryMessage")
          .bundleCandidate("bundle")
          .orderer(Orderer.builder()
              .mallId("mallId")
              .name("name")
              .phoneNumber1("phoneNumber1")
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
              .basicDeliveryCharge((long) i)
              .credit((long) i)
              .deliveryChargeType("deliveryChargeType")
              .paidAt(LocalDateTime.now())
              .point((long) i)
              .build())
              .calculation(Calculation.builder()
                  .calculatedAmount((long) i)
                  .fee((long) i)
                  .build())
              .customsClearanceCode("clearanceCode")
              .orderedAt(LocalDateTime.now())
              .collectedAt(LocalDateTime.now())
              .syncBaseDate(LocalDateTime.now())
          .build();
    }
  }

  @Test
  void save() {
    Order save = repository.save(order);
    System.out.println("save = " + save);

    Order find = repository.findById(Objects.requireNonNull(save.getId()))
        .orElse(null);

    System.out.println("find = " + find);
  }

}
