package me.hjhng.springdatajdbcpractice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import me.hjhng.springdatajdbcpractice.order.Calculation;
import me.hjhng.springdatajdbcpractice.order.DeliveryInfo;
import me.hjhng.springdatajdbcpractice.order.Order;
import me.hjhng.springdatajdbcpractice.order.OrderNumber;
import me.hjhng.springdatajdbcpractice.order.OrderedProduct;
import me.hjhng.springdatajdbcpractice.order.Orderer;
import me.hjhng.springdatajdbcpractice.order.Payment;
import me.hjhng.springdatajdbcpractice.order.Receiver;
import me.hjhng.springdatajdbcpractice.order.entity.MallType;
import me.hjhng.springdatajdbcpractice.order.entity.OrderEntity;
import me.hjhng.springdatajdbcpractice.order.entity.OrderEntityMapper;
import me.hjhng.springdatajdbcpractice.order.entity.OrderJpaRepository;
import me.hjhng.springdatajdbcpractice.order.entity.OrderStatus;
import me.hjhng.springdatajdbcpractice.order.entity.OrderType;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class OrderJpaRepositoryTest {

  @Autowired
  private OrderJpaRepository repository;

  @Autowired
  private OrderEntityMapper mapper;

  private final List<OrderEntity> orderEntities = new ArrayList<>();

  @BeforeEach
  void init() {
    for (int i = 1; i <= 1000; ++i) {
      Order build = Order.builder()
          .id(UUID.randomUUID())
          .syncId(UUID.randomUUID())
          .creationSyncId(UUID.randomUUID())
          .collectId(UUID.randomUUID())
          .packingUnitId(UUID.randomUUID())
          .mallAccountId(UUID.randomUUID())
          .corporationId(UUID.randomUUID())
          .mallType(MallType.LOTTE_ON.name())
          .status(OrderStatus.DISPATCHED.name())
          .rawData("rawStatus")
          .orderType(OrderType.VOUCHER.name())
          .orderNumber(OrderNumber.builder()
              .assistOrderNumber("assist")
              .orderNumber("orderNumber")
              .build())
          .orderSequence("orderSequence")
          .cartNumber("cartNumber")
          .deliveryInfo(DeliveryInfo.builder()
              .deliveryMethod("PARCEL")
              .invoiceNumber("invoiceNumber")
              .parcelCode("EZUSA")
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
              .deliveryChargeType("PREPAID")
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

      OrderEntity orderEntity = mapper.toEntity(build);
      orderEntities.add(orderEntity);
    }
  }

  @Test
  void bulk_insert_test() {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    repository.saveAll(orderEntities);

    stopWatch.stop();

    System.out.println("bulk_insert_test -> Total time in seconds: " + stopWatch.getTotalTimeSeconds());
  }

}
