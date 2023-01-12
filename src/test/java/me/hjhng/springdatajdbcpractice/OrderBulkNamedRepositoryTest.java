package me.hjhng.springdatajdbcpractice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
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
import me.hjhng.springdatajdbcpractice.order.entity.OrderEntity;

@SpringBootTest
@Import(DataJdbcConfiguration.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class OrderBulkNamedRepositoryTest {

  @Autowired
  private NamedParameterJdbcOperations jdbcTemplate;

  private final List<Order> orders = new ArrayList<>();
  private final List<OrderEntity> orderEntities = new ArrayList<>();

  private final String sql =
      "INSERT INTO Orders VALUES (:id, :syncId, :collectId, :packingUnitId, :mallType, :mallAccountId, :corporationId, "
          + ":status, :rawStatus, :orderType, :inInitial, :prepared, :inProgress, :finished, "
          + ":canceled, :orderNumber, :assistOrderNumber, :orderSequence, :cartNumber, :presentReceived, "
          + ":deliveryMethod, :parcelCode, :invoiceNumber, :deliveryMessage, :bundleCandidate, "
          + ":ordererName, :ordererMallId, :ordererPhoneNumber1, :ordererPhoneNumber2, :productId, "
          + ":productNumber, :productName, :optionNumber, :optionName, :extraComponentNumber, :extraComponent, "
          + ":extraComponentContains, :gift, :sellerProductCode, :sellerProductCodeExtra1, :sellerProductCodeExtra2, "
          + ":sellerProductCodeExtra3, :sellerProductCodeExtra4, :productAdditionalMessage, :buyerAdditionalMessage, "
          + ":quantity, :receiverName, :receiverPhoneNumber1, :receiverPhoneNumber2, :receiverZipCode, "
          + ":receiverAddress1, :receiverAddress2, :productCharge, :productDiscountCharge, :orderDiscountCharge, "
          + ":orderPointCharge, :orderCreditCharge, :deliveryChargeType, :basicDeliveryCharge, :distanceDeliveryCharge, "
          + ":paymentAmount, :paidAt, :calculatedAmount, :fee, :feeNumericType, :customsClearanceCode, "
          + ":customsClearancePhoneNumber, :orderedAt, :expectSendDate, :collectedAt, :syncBaseDate, "
          + ":sentAt, :preparable, :delayable, :deliverable, :visitable, :deliveryChangeable, "
          + ":visitConfirmType, :rawData, :creationSyncId, :createdAt, :deliveryCompletable);";

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

    List<MapSqlParameterSource> params = new ArrayList<>();
    for (Order order : orders) {
      MapSqlParameterSource source = new MapSqlParameterSource();
      source.addValue("id", UUIDBinaryUtil.toBinary(Objects.requireNonNull(order.getId())));
      source.addValue("syncId", UUIDBinaryUtil.toBinary(order.getSyncId()));
      source.addValue("collectId", UUIDBinaryUtil.toBinary(order.getCollectId()));
      source.addValue("packingUnitId", UUIDBinaryUtil.toBinary(order.getPackingUnitId()));
      source.addValue("mallType", order.getMallType());
      source.addValue("mallAccountId", UUIDBinaryUtil.toBinary(order.getMallAccountId()));
      source.addValue("corporationId", UUIDBinaryUtil.toBinary(order.getCorporationId()));
      source.addValue("status", order.getStatus());
      source.addValue("rawStatus", order.getRawStatus());
      source.addValue("orderType", order.getOrderType());
      source.addValue("inInitial", order.isInInitial());
      source.addValue("prepared", order.isPrepared());
      source.addValue("inProgress", order.isInProgress());
      source.addValue("finished", order.isFinished());
      source.addValue("canceled", order.isCanceled());
      source.addValue("orderNumber", order.getOrderNumber().getOrderNumber());
      source.addValue("assistOrderNumber", order.getOrderNumber().getAssistOrderNumber());
      source.addValue("orderSequence", order.getOrderSequence());
      source.addValue("cartNumber", order.getCartNumber());
      source.addValue("presentReceived", order.isPresentReceived());
      source.addValue("deliveryMethod", order.getDeliveryInfo().getDeliveryMethod());
      source.addValue("parcelCode", order.getDeliveryInfo().getParcelCode());
      source.addValue("invoiceNumber", order.getDeliveryInfo().getInvoiceNumber());
      source.addValue("deliveryMessage", order.getDeliveryMessage());
      source.addValue("bundleCandidate", order.getBundleCandidate());
      source.addValue("ordererName", order.getOrderer().getName());
      source.addValue("ordererMallId", order.getOrderer().getMallId());
      source.addValue("ordererPhoneNumber1", order.getOrderer().getPhoneNumber1());
      source.addValue("ordererPhoneNumber2", order.getOrderer().getPhoneNumber2());
      source.addValue("productId", UUIDBinaryUtil.toBinary(order.getProduct().getProductId()));
      source.addValue("productNumber", order.getProduct().getProductNumber());
      source.addValue("productName", order.getProduct().getName());
      source.addValue("optionNumber", order.getProduct().getOptionNumber());
      source.addValue("optionName", order.getProduct().getOption());
      source.addValue("extraComponentNumber", order.getProduct().getExtraComponentNumber());
      source.addValue("extraComponent", order.getProduct().getExtraComponent());
      source.addValue("extraComponentContains", order.getProduct().isExtraComponentContains());
      source.addValue("gift", order.getProduct().getGift());
      source.addValue("sellerProductCode", order.getProduct().getSellerProductCode());
      source.addValue("sellerProductCodeExtra1", order.getProduct().getSellerProductCodeExtra1());
      source.addValue("sellerProductCodeExtra2", order.getProduct().getSellerProductCodeExtra2());
      source.addValue("sellerProductCodeExtra3", order.getProduct().getSellerProductCodeExtra3());
      source.addValue("sellerProductCodeExtra4", order.getProduct().getSellerProductCodeExtra4());
      source.addValue("productAdditionalMessage", order.getProduct().getProductAdditionalMessage());
      source.addValue("buyerAdditionalMessage", order.getProduct().getBuyerAdditionalMessage());
      source.addValue("quantity", order.getProduct().getQuantity());
      source.addValue("receiverName", order.getReceiver().getName());
      source.addValue("receiverPhoneNumber1", order.getReceiver().getPhoneNumber1());
      source.addValue("receiverPhoneNumber2", order.getReceiver().getPhoneNumber2());
      source.addValue("receiverZipCode", order.getReceiver().getZipCode());
      source.addValue("receiverAddress1", order.getReceiver().getAddress1());
      source.addValue("receiverAddress2", order.getReceiver().getAddress2());
      source.addValue("productCharge", order.getPayment().getProductCharge());
      source.addValue("productDiscountCharge", order.getPayment().getProductDiscountCharge());
      source.addValue("orderDiscountCharge", order.getPayment().getOrderDiscountCharge());
      source.addValue("orderPointCharge", order.getPayment().getPoint());
      source.addValue("orderCreditCharge", order.getPayment().getCredit());
      source.addValue("deliveryChargeType", order.getPayment().getDeliveryChargeType());
      source.addValue("basicDeliveryCharge", order.getPayment().getBasicDeliveryCharge());
      source.addValue("distanceDeliveryCharge", order.getPayment().getDistanceDeliveryCharge());
      source.addValue("paymentAmount", order.getPayment().getPaymentAmount());
      source.addValue("paidAt", order.getPayment().getPaidAt());
      source.addValue("calculatedAmount", order.getCalculation().getCalculatedAmount());
      source.addValue("fee", order.getCalculation().getFee());
      source.addValue("feeNumericType", order.getCalculation().getFeeNumericType());
      source.addValue("customsClearanceCode", order.getCustomsClearanceCode());
      source.addValue("customsClearancePhoneNumber", order.getCustomsClearancePhoneNumber());
      source.addValue("orderedAt", order.getOrderedAt());
      source.addValue("expectSendDate", order.getExpectSendDate());
      source.addValue("collectedAt", order.getCollectedAt());
      source.addValue("syncBaseDate", order.getSyncBaseDate());
      source.addValue("sentAt", order.getSentAt());
      source.addValue("preparable", order.isPreparable());
      source.addValue("delayable", order.isDelayable());
      source.addValue("deliverable", order.isDeliverable());
      source.addValue("visitable", order.isVisitable());
      source.addValue("deliveryChangeable", order.isDeliveryChangeable());
      source.addValue("visitConfirmType", order.getVisitConfirmType());
      source.addValue("rawData", order.getRawData());
      source.addValue("creationSyncId", UUIDBinaryUtil.toBinary(order.getCreationSyncId()));
      source.addValue("createdAt", order.getCreatedAt());
      source.addValue("deliveryCompletable", order.isDeliveryCompletable());

      params.add(source);
    }

    jdbcTemplate.batchUpdate(sql, params.toArray(new MapSqlParameterSource[orderEntities.size()]));
    stopWatch.stop();

    System.out.println("bulk_insert_test -> Total time in seconds: " + stopWatch.getTotalTimeSeconds());
  }

//  @Test
//  void bulk_insert_async_test() throws ExecutionException, InterruptedException {
//    AtomicInteger atomicInteger = new AtomicInteger();
//    StopWatch stopWatch = new StopWatch();
//    CompletableFuture[] completableFutures = orders.stream()
//        .collect(Collectors.groupingBy(t -> atomicInteger.getAndIncrement() / 1000))
//        .values()
//        .stream()
//        .map(order -> CompletableFuture.runAsync(() -> {
//          jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
//
//            @Override
//            public void setValues(PreparedStatement ps, int i) throws SQLException {
//              ps.setBytes(1, UUIDBinaryUtil.toBinary(Objects.requireNonNull(order.get(i)
//                  .getId())));
//              ps.setBytes(2, UUIDBinaryUtil.toBinary(order.get(i).getSyncId()));
//              ps.setBytes(3, UUIDBinaryUtil.toBinary(order.get(i).getCollectId()));
//              ps.setBytes(4, UUIDBinaryUtil.toBinary(order.get(i).getPackingUnitId()));
//              ps.setObject(5, order.get(i).getMallType());
//              ps.setBytes(6, UUIDBinaryUtil.toBinary(order.get(i).getMallAccountId()));
//              ps.setBytes(7, UUIDBinaryUtil.toBinary(order.get(i).getCorporationId()));
//              ps.setObject(8, order.get(i).getStatus());
//              ps.setObject(9, order.get(i).getRawStatus());
//              ps.setString(10, order.get(i).getOrderType());
//              ps.setBoolean(11, order.get(i).isInInitial());
//              ps.setBoolean(12, order.get(i).isPrepared());
//              ps.setBoolean(13, order.get(i).isInProgress());
//              ps.setBoolean(14, order.get(i).isFinished());
//              ps.setBoolean(15, order.get(i).isCanceled());
//              ps.setString(16, order.get(i).getOrderNumber().getOrderNumber());
//              ps.setString(17, order.get(i).getOrderNumber().getAssistOrderNumber());
//              ps.setObject(18, order.get(i).getOrderSequence());
//              ps.setObject(19, order.get(i).getCartNumber());
//              ps.setObject(20, order.get(i).isPresentReceived());
//              ps.setObject(21, order.get(i).getDeliveryInfo().getDeliveryMethod());
//              ps.setObject(22, order.get(i).getDeliveryInfo().getParcelCode());
//              ps.setObject(23, order.get(i).getDeliveryInfo().getInvoiceNumber());
//              ps.setObject(24, order.get(i).getDeliveryMessage());
//              ps.setObject(25, order.get(i).getBundleCandidate());
//              ps.setObject(26, order.get(i).getOrderer().getName());
//              ps.setObject(27, order.get(i).getOrderer().getMallId());
//              ps.setObject(28, order.get(i).getOrderer().getPhoneNumber1());
//              ps.setObject(29, order.get(i).getOrderer().getPhoneNumber2());
//              ps.setBytes(30, UUIDBinaryUtil.toBinary(order.get(i).getProduct().getProductId()));
//              ps.setObject(31, order.get(i).getProduct().getProductNumber());
//              ps.setObject(32, order.get(i).getProduct().getName());
//              ps.setObject(33, order.get(i).getProduct().getOptionNumber());
//              ps.setObject(34, order.get(i).getProduct().getOption());
//              ps.setObject(35, order.get(i).getProduct().getExtraComponentNumber());
//              ps.setObject(36, order.get(i).getProduct().getExtraComponent());
//              ps.setObject(37, order.get(i).getProduct().isExtraComponentContains());
//              ps.setObject(38, order.get(i).getProduct().getGift());
//              ps.setObject(39, order.get(i).getProduct().getSellerProductCode());
//              ps.setObject(40, order.get(i).getProduct().getSellerProductCodeExtra1());
//              ps.setObject(41, order.get(i).getProduct().getSellerProductCodeExtra2());
//              ps.setObject(42, order.get(i).getProduct().getSellerProductCodeExtra3());
//              ps.setObject(43, order.get(i).getProduct().getSellerProductCodeExtra4());
//              ps.setObject(44, order.get(i).getProduct().getProductAdditionalMessage());
//              ps.setObject(45, order.get(i).getProduct().getBuyerAdditionalMessage());
//              ps.setObject(46, order.get(i).getProduct().getQuantity());
//              ps.setObject(47, order.get(i).getReceiver().getName());
//              ps.setObject(48, order.get(i).getReceiver().getPhoneNumber1());
//              ps.setObject(49, order.get(i).getReceiver().getPhoneNumber2());
//              ps.setObject(50, order.get(i).getReceiver().getZipCode());
//              ps.setObject(51, order.get(i).getReceiver().getAddress1());
//              ps.setObject(52, order.get(i).getReceiver().getAddress2());
//              ps.setObject(53, order.get(i).getPayment().getProductCharge());
//              ps.setObject(54, order.get(i).getPayment().getProductDiscountCharge());
//              ps.setObject(55, order.get(i).getPayment().getOrderDiscountCharge());
//              ps.setObject(56, order.get(i).getPayment().getPoint());
//              ps.setObject(57, order.get(i).getPayment().getCredit());
//              ps.setObject(58, order.get(i).getPayment().getDeliveryChargeType());
//              ps.setObject(59, order.get(i).getPayment().getBasicDeliveryCharge());
//              ps.setObject(60, order.get(i).getPayment().getDistanceDeliveryCharge());
//              ps.setObject(61, order.get(i).getPayment().getPaymentAmount());
//              ps.setObject(62, order.get(i).getPayment().getPaidAt());
//              ps.setObject(63, order.get(i).getCalculation().getCalculatedAmount());
//              ps.setObject(64, order.get(i).getCalculation().getFee());
//              ps.setObject(65, order.get(i).getCalculation().getFeeNumericType());
//              ps.setObject(66, order.get(i).getCustomsClearanceCode());
//              ps.setObject(67, order.get(i).getCustomsClearancePhoneNumber());
//              ps.setObject(68, order.get(i).getOrderedAt());
//              ps.setObject(69, order.get(i).getExpectSendDate());
//              ps.setObject(70, order.get(i).getCollectedAt());
//              ps.setObject(71, order.get(i).getSyncBaseDate());
//              ps.setObject(72, order.get(i).getSentAt());
//              ps.setObject(73, order.get(i).isPreparable());
//              ps.setObject(74, order.get(i).isDelayable());
//              ps.setObject(75, order.get(i).isDeliverable());
//              ps.setObject(76, order.get(i).isVisitable());
//              ps.setObject(77, order.get(i).isDeliveryChangeable());
//              ps.setObject(78, order.get(i).getVisitConfirmType());
//              ps.setObject(79, order.get(i).getRawData());
//              ps.setBytes(80, UUIDBinaryUtil.toBinary(order.get(i).getCreationSyncId()));
//              ps.setObject(81, order.get(i).getCreatedAt());
//              ps.setObject(82, order.get(i).isDeliveryCompletable());
//            }
//
//            @Override
//            public int getBatchSize() {
//              return order.size();
//            }
//          });
//        }))
//        .toArray(CompletableFuture[]::new);
//
//    CompletableFuture<Void> run = CompletableFuture.allOf(completableFutures);
//
//    stopWatch.start();
//    run.get();
//    stopWatch.stop();
//
//    System.out.println("bulk_insert_async_test -> Total time in seconds: " + stopWatch.getTotalTimeSeconds());
//  }

}
