package me.hjhng.springdatajdbcpractice;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StopWatch;

import me.hjhng.springdatajdbcpractice.config.DataJdbcConfiguration;
import me.hjhng.springdatajdbcpractice.order.Calculation;
import me.hjhng.springdatajdbcpractice.order.DeliveryInfo;
import me.hjhng.springdatajdbcpractice.order.Order;
import me.hjhng.springdatajdbcpractice.order.OrderNumber;
import me.hjhng.springdatajdbcpractice.order.OrderedProduct;
import me.hjhng.springdatajdbcpractice.order.Orderer;
import me.hjhng.springdatajdbcpractice.order.Payment;
import me.hjhng.springdatajdbcpractice.order.Receiver;

@DataJdbcTest
@Import(DataJdbcConfiguration.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class OrderBulkRepositoryTest {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private final List<Order> orders = new ArrayList<>();

  private final String sql =
      "INSERT INTO Orders (`id`, `syncId`, `collectId`, `packingUnitId`, `mallType`, `mallAccountId`, `corporationId`, "
          + "`status`, `rawStatus`, `orderType`, `inInitial`, `prepared`, `inProgress`, `finished`, `canceled`, `orderNumber`, "
          + "`assistOrderNumber`, `orderSequence`, `cartNumber`, `presentReceived`, `deliveryMethod`, `parcelCode`, `invoiceNumber`,"
          + "`deliveryMessage`, `bundleCandidate`, `ordererName`, `ordererMallId`, `ordererPhoneNumber1`, `ordererPhoneNumber2`, "
          + "`productId`, `productNumber`, `productName`, `optionNumber`, `optionName`, `extraComponentNumber`, `extraComponent`,"
          + "`extraComponentContains`, `gift`, `sellerProductCode`, `sellerProductCodeExtra1`, `sellerProductCodeExtra2`, "
          + "`sellerProductCodeExtra3`, `sellerProductCodeExtra4`, `productAdditionalMessage`, `buyerAdditionalMessage`, `quantity`, "
          + "`receiverName`, `receiverPhoneNumber1`, `receiverPhoneNumber2`, `receiverZipCode`, `receiverAddress1`, `receiverAddress2`, "
          + "`productCharge`, `productDiscountCharge`, `orderDiscountCharge`, `orderPointCharge`, `orderCreditCharge`, "
          + "`deliveryChargeType`, `basicDeliveryCharge`, `distanceDeliveryCharge`, `paymentAmount`, `paidAt`, `calculatedAmount`, "
          + "`fee`, `feeNumericType`, `customsClearanceCode`, `customsClearancePhoneNumber`, `orderedAt`, `expectSendDate`, "
          + "`collectedAt`, `syncBaseDate`, `sentAt`, `preparable`, `delayable`, `deliverable`, `visitable`, `deliveryChangeable`, "
          + "`visitConfirmType`, `rawData`, `creationSyncId`, `createdAt`, `deliveryCompletable`) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

  @BeforeEach
  void init() {
    for (int i = 1; i <= 100000; ++i) {
      orders.add(Order.builder()
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
              .deliveryChargeType("chargeType")
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
          .build());
    }
  }

  @Test
  void bulk_insert_test() {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        ps.setBytes(1, UUIDBinaryUtil.toBinary(Objects.requireNonNull(orders.get(i)
                .getId())));
        ps.setBytes(2, UUIDBinaryUtil.toBinary(orders.get(i).getSyncId()));
        ps.setBytes(3, UUIDBinaryUtil.toBinary(orders.get(i).getCollectId()));
        ps.setBytes(4, UUIDBinaryUtil.toBinary(orders.get(i).getPackingUnitId()));
        ps.setObject(5, orders.get(i).getMallType());
        ps.setBytes(6, UUIDBinaryUtil.toBinary(orders.get(i).getMallAccountId()));
        ps.setBytes(7, UUIDBinaryUtil.toBinary(orders.get(i).getCorporationId()));
        ps.setObject(8, orders.get(i).getStatus());
        ps.setObject(9, orders.get(i).getRawStatus());
        ps.setString(10, orders.get(i).getOrderType());
        ps.setBoolean(11, orders.get(i).isInInitial());
        ps.setBoolean(12, orders.get(i).isPrepared());
        ps.setBoolean(13, orders.get(i).isInProgress());
        ps.setBoolean(14, orders.get(i).isFinished());
        ps.setBoolean(15, orders.get(i).isCanceled());
        ps.setString(16, orders.get(i).getOrderNumber().getOrderNumber());
        ps.setString(17, orders.get(i).getOrderNumber().getAssistOrderNumber());
        ps.setObject(18, orders.get(i).getOrderSequence());
        ps.setObject(19, orders.get(i).getCartNumber());
        ps.setObject(20, orders.get(i).isPresentReceived());
        ps.setObject(21, orders.get(i).getDeliveryInfo().getDeliveryMethod());
        ps.setObject(22, orders.get(i).getDeliveryInfo().getParcelCode());
        ps.setObject(23, orders.get(i).getDeliveryInfo().getInvoiceNumber());
        ps.setObject(24, orders.get(i).getDeliveryMessage());
        ps.setObject(25, orders.get(i).getBundleCandidate());
        ps.setObject(26, orders.get(i).getOrderer().getName());
        ps.setObject(27, orders.get(i).getOrderer().getMallId());
        ps.setObject(28, orders.get(i).getOrderer().getPhoneNumber1());
        ps.setObject(29, orders.get(i).getOrderer().getPhoneNumber2());
        ps.setBytes(30, UUIDBinaryUtil.toBinary(orders.get(i).getProduct().getProductId()));
        ps.setObject(31, orders.get(i).getProduct().getProductNumber());
        ps.setObject(32, orders.get(i).getProduct().getName());
        ps.setObject(33, orders.get(i).getProduct().getOptionNumber());
        ps.setObject(34, orders.get(i).getProduct().getOption());
        ps.setObject(35, orders.get(i).getProduct().getExtraComponentNumber());
        ps.setObject(36, orders.get(i).getProduct().getExtraComponent());
        ps.setObject(37, orders.get(i).getProduct().isExtraComponentContains());
        ps.setObject(38, orders.get(i).getProduct().getGift());
        ps.setObject(39, orders.get(i).getProduct().getSellerProductCode());
        ps.setObject(40, orders.get(i).getProduct().getSellerProductCodeExtra1());
        ps.setObject(41, orders.get(i).getProduct().getSellerProductCodeExtra2());
        ps.setObject(42, orders.get(i).getProduct().getSellerProductCodeExtra3());
        ps.setObject(43, orders.get(i).getProduct().getSellerProductCodeExtra4());
        ps.setObject(44, orders.get(i).getProduct().getProductAdditionalMessage());
        ps.setObject(45, orders.get(i).getProduct().getBuyerAdditionalMessage());
        ps.setObject(46, orders.get(i).getProduct().getQuantity());
        ps.setObject(47, orders.get(i).getReceiver().getName());
        ps.setObject(48, orders.get(i).getReceiver().getPhoneNumber1());
        ps.setObject(49, orders.get(i).getReceiver().getPhoneNumber2());
        ps.setObject(50, orders.get(i).getReceiver().getZipCode());
        ps.setObject(51, orders.get(i).getReceiver().getAddress1());
        ps.setObject(52, orders.get(i).getReceiver().getAddress2());
        ps.setObject(53, orders.get(i).getPayment().getProductCharge());
        ps.setObject(54, orders.get(i).getPayment().getProductDiscountCharge());
        ps.setObject(55, orders.get(i).getPayment().getOrderDiscountCharge());
        ps.setObject(56, orders.get(i).getPayment().getPoint());
        ps.setObject(57, orders.get(i).getPayment().getCredit());
        ps.setObject(58, orders.get(i).getPayment().getDeliveryChargeType());
        ps.setObject(59, orders.get(i).getPayment().getBasicDeliveryCharge());
        ps.setObject(60, orders.get(i).getPayment().getDistanceDeliveryCharge());
        ps.setObject(61, orders.get(i).getPayment().getPaymentAmount());
        ps.setObject(62, orders.get(i).getPayment().getPaidAt());
        ps.setObject(63, orders.get(i).getCalculation().getCalculatedAmount());
        ps.setObject(64, orders.get(i).getCalculation().getFee());
        ps.setObject(65, orders.get(i).getCalculation().getFeeNumericType());
        ps.setObject(66, orders.get(i).getCustomsClearanceCode());
        ps.setObject(67, orders.get(i).getCustomsClearancePhoneNumber());
        ps.setObject(68, orders.get(i).getOrderedAt());
        ps.setObject(69, orders.get(i).getExpectSendDate());
        ps.setObject(70, orders.get(i).getCollectedAt());
        ps.setObject(71, orders.get(i).getSyncBaseDate());
        ps.setObject(72, orders.get(i).getSentAt());
        ps.setObject(73, orders.get(i).isPreparable());
        ps.setObject(74, orders.get(i).isDelayable());
        ps.setObject(75, orders.get(i).isDeliverable());
        ps.setObject(76, orders.get(i).isVisitable());
        ps.setObject(77, orders.get(i).isDeliveryChangeable());
        ps.setObject(78, orders.get(i).getVisitConfirmType());
        ps.setObject(79, orders.get(i).getRawData());
        ps.setBytes(80, UUIDBinaryUtil.toBinary(orders.get(i).getCreationSyncId()));
        ps.setObject(81, orders.get(i).getCreatedAt());
        ps.setObject(82, orders.get(i).isDeliveryCompletable());
      }


      @Override
      public int getBatchSize() {
        return orders.size();
      }
    });
    stopWatch.stop();

    System.out.println("bulk_insert_test -> Total time in seconds: " + stopWatch.getTotalTimeSeconds());
  }

  @Test
  void bulk_insert_async_test() throws ExecutionException, InterruptedException {
    AtomicInteger atomicInteger = new AtomicInteger();
    StopWatch stopWatch = new StopWatch();
    CompletableFuture[] completableFutures = orders.stream()
        .collect(Collectors.groupingBy(t -> atomicInteger.getAndIncrement() / 1000))
        .values()
        .stream()
        .map(order -> CompletableFuture.runAsync(() -> {
          jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
              ps.setBytes(1, UUIDBinaryUtil.toBinary(Objects.requireNonNull(order.get(i)
                  .getId())));
              ps.setBytes(2, UUIDBinaryUtil.toBinary(order.get(i).getSyncId()));
              ps.setBytes(3, UUIDBinaryUtil.toBinary(order.get(i).getCollectId()));
              ps.setBytes(4, UUIDBinaryUtil.toBinary(order.get(i).getPackingUnitId()));
              ps.setObject(5, order.get(i).getMallType());
              ps.setBytes(6, UUIDBinaryUtil.toBinary(order.get(i).getMallAccountId()));
              ps.setBytes(7, UUIDBinaryUtil.toBinary(order.get(i).getCorporationId()));
              ps.setObject(8, order.get(i).getStatus());
              ps.setObject(9, order.get(i).getRawStatus());
              ps.setString(10, order.get(i).getOrderType());
              ps.setBoolean(11, order.get(i).isInInitial());
              ps.setBoolean(12, order.get(i).isPrepared());
              ps.setBoolean(13, order.get(i).isInProgress());
              ps.setBoolean(14, order.get(i).isFinished());
              ps.setBoolean(15, order.get(i).isCanceled());
              ps.setString(16, order.get(i).getOrderNumber().getOrderNumber());
              ps.setString(17, order.get(i).getOrderNumber().getAssistOrderNumber());
              ps.setObject(18, order.get(i).getOrderSequence());
              ps.setObject(19, order.get(i).getCartNumber());
              ps.setObject(20, order.get(i).isPresentReceived());
              ps.setObject(21, order.get(i).getDeliveryInfo().getDeliveryMethod());
              ps.setObject(22, order.get(i).getDeliveryInfo().getParcelCode());
              ps.setObject(23, order.get(i).getDeliveryInfo().getInvoiceNumber());
              ps.setObject(24, order.get(i).getDeliveryMessage());
              ps.setObject(25, order.get(i).getBundleCandidate());
              ps.setObject(26, order.get(i).getOrderer().getName());
              ps.setObject(27, order.get(i).getOrderer().getMallId());
              ps.setObject(28, order.get(i).getOrderer().getPhoneNumber1());
              ps.setObject(29, order.get(i).getOrderer().getPhoneNumber2());
              ps.setBytes(30, UUIDBinaryUtil.toBinary(order.get(i).getProduct().getProductId()));
              ps.setObject(31, order.get(i).getProduct().getProductNumber());
              ps.setObject(32, order.get(i).getProduct().getName());
              ps.setObject(33, order.get(i).getProduct().getOptionNumber());
              ps.setObject(34, order.get(i).getProduct().getOption());
              ps.setObject(35, order.get(i).getProduct().getExtraComponentNumber());
              ps.setObject(36, order.get(i).getProduct().getExtraComponent());
              ps.setObject(37, order.get(i).getProduct().isExtraComponentContains());
              ps.setObject(38, order.get(i).getProduct().getGift());
              ps.setObject(39, order.get(i).getProduct().getSellerProductCode());
              ps.setObject(40, order.get(i).getProduct().getSellerProductCodeExtra1());
              ps.setObject(41, order.get(i).getProduct().getSellerProductCodeExtra2());
              ps.setObject(42, order.get(i).getProduct().getSellerProductCodeExtra3());
              ps.setObject(43, order.get(i).getProduct().getSellerProductCodeExtra4());
              ps.setObject(44, order.get(i).getProduct().getProductAdditionalMessage());
              ps.setObject(45, order.get(i).getProduct().getBuyerAdditionalMessage());
              ps.setObject(46, order.get(i).getProduct().getQuantity());
              ps.setObject(47, order.get(i).getReceiver().getName());
              ps.setObject(48, order.get(i).getReceiver().getPhoneNumber1());
              ps.setObject(49, order.get(i).getReceiver().getPhoneNumber2());
              ps.setObject(50, order.get(i).getReceiver().getZipCode());
              ps.setObject(51, order.get(i).getReceiver().getAddress1());
              ps.setObject(52, order.get(i).getReceiver().getAddress2());
              ps.setObject(53, order.get(i).getPayment().getProductCharge());
              ps.setObject(54, order.get(i).getPayment().getProductDiscountCharge());
              ps.setObject(55, order.get(i).getPayment().getOrderDiscountCharge());
              ps.setObject(56, order.get(i).getPayment().getPoint());
              ps.setObject(57, order.get(i).getPayment().getCredit());
              ps.setObject(58, order.get(i).getPayment().getDeliveryChargeType());
              ps.setObject(59, order.get(i).getPayment().getBasicDeliveryCharge());
              ps.setObject(60, order.get(i).getPayment().getDistanceDeliveryCharge());
              ps.setObject(61, order.get(i).getPayment().getPaymentAmount());
              ps.setObject(62, order.get(i).getPayment().getPaidAt());
              ps.setObject(63, order.get(i).getCalculation().getCalculatedAmount());
              ps.setObject(64, order.get(i).getCalculation().getFee());
              ps.setObject(65, order.get(i).getCalculation().getFeeNumericType());
              ps.setObject(66, order.get(i).getCustomsClearanceCode());
              ps.setObject(67, order.get(i).getCustomsClearancePhoneNumber());
              ps.setObject(68, order.get(i).getOrderedAt());
              ps.setObject(69, order.get(i).getExpectSendDate());
              ps.setObject(70, order.get(i).getCollectedAt());
              ps.setObject(71, order.get(i).getSyncBaseDate());
              ps.setObject(72, order.get(i).getSentAt());
              ps.setObject(73, order.get(i).isPreparable());
              ps.setObject(74, order.get(i).isDelayable());
              ps.setObject(75, order.get(i).isDeliverable());
              ps.setObject(76, order.get(i).isVisitable());
              ps.setObject(77, order.get(i).isDeliveryChangeable());
              ps.setObject(78, order.get(i).getVisitConfirmType());
              ps.setObject(79, order.get(i).getRawData());
              ps.setBytes(80, UUIDBinaryUtil.toBinary(order.get(i).getCreationSyncId()));
              ps.setObject(81, order.get(i).getCreatedAt());
              ps.setObject(82, order.get(i).isDeliveryCompletable());
            }

            @Override
            public int getBatchSize() {
              return order.size();
            }
          });
        }))
        .toArray(CompletableFuture[]::new);

    CompletableFuture<Void> run = CompletableFuture.allOf(completableFutures);

    stopWatch.start();
    run.get();
    stopWatch.stop();

    System.out.println("bulk_insert_async_test -> Total time in seconds: " + stopWatch.getTotalTimeSeconds());
  }

}
