package me.hjhng.springdatajdbcpractice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import me.hjhng.springdatajdbcpractice.order.entity.MallType;
import me.hjhng.springdatajdbcpractice.order.entity.OrderEntity;
import me.hjhng.springdatajdbcpractice.order.entity.OrderEntityMapper;
import me.hjhng.springdatajdbcpractice.order.entity.OrderStatus;
import me.hjhng.springdatajdbcpractice.order.entity.OrderType;

@SpringBootTest
@Import(DataJdbcConfiguration.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class OrderEntityBulkNamedRepositoryTest {

  @Autowired
  private NamedParameterJdbcOperations jdbcTemplate;

  @Autowired
  private OrderEntityMapper mapper;

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
  void entity_bulk_insert_test() {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    List<MapSqlParameterSource> params = new ArrayList<>();
    for (OrderEntity order : orderEntities) {
      MapSqlParameterSource source = new MapSqlParameterSource();
      source.addValue("id", UUIDBinaryUtil.toBinary(Objects.requireNonNull(order.getId())));
      source.addValue("syncId", UUIDBinaryUtil.toBinary(order.getSyncId()));
      source.addValue("collectId", UUIDBinaryUtil.toBinary(order.getCollectId()));
      source.addValue("packingUnitId", UUIDBinaryUtil.toBinary(order.getPackingUnitId()));
      source.addValue("mallType", EnumUtil.toString(order.getMallType()));
      source.addValue("mallAccountId", UUIDBinaryUtil.toBinary(order.getMallAccountId()));
      source.addValue("corporationId", UUIDBinaryUtil.toBinary(order.getCorporationId()));
      source.addValue("status", EnumUtil.toString(order.getStatus()));
      source.addValue("rawStatus", order.getRawStatus());
      source.addValue("orderType", EnumUtil.toString(order.getOrderType()));
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
      source.addValue("visitConfirmType", EnumUtil.toString(order.getVisitConfirmType()));
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
}
