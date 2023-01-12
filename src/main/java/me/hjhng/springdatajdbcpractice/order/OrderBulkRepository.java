package me.hjhng.springdatajdbcpractice.order;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import me.hjhng.springdatajdbcpractice.UUIDBinaryUtil;

@Repository
public class OrderBulkRepository {

  private final JdbcTemplate jdbcTemplate;

  public OrderBulkRepository(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public void execute(List<Order> orders) {
    final String sql =
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

    jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        ps.setBytes(1, UUIDBinaryUtil.toBinary(Objects.requireNonNull(orders.get(i)
            .getId())));
        ps.setBytes(2, UUIDBinaryUtil.toBinary(orders.get(i)
            .getSyncId()));
        ps.setBytes(3, UUIDBinaryUtil.toBinary(orders.get(i)
            .getCollectId()));
        ps.setBytes(4, UUIDBinaryUtil.toBinary(orders.get(i)
            .getPackingUnitId()));
        ps.setObject(5, orders.get(i)
            .getMallType());
        ps.setBytes(6, UUIDBinaryUtil.toBinary(orders.get(i)
            .getMallAccountId()));
        ps.setBytes(7, UUIDBinaryUtil.toBinary(orders.get(i)
            .getCorporationId()));
        ps.setObject(8, orders.get(i)
            .getStatus());
        ps.setObject(9, orders.get(i)
            .getRawStatus());
        ps.setString(10, orders.get(i)
            .getOrderType());
        ps.setBoolean(11, orders.get(i)
            .isInInitial());
        ps.setBoolean(12, orders.get(i)
            .isPrepared());
        ps.setBoolean(13, orders.get(i)
            .isInProgress());
        ps.setBoolean(14, orders.get(i)
            .isFinished());
        ps.setBoolean(15, orders.get(i)
            .isCanceled());
        ps.setString(16, orders.get(i)
            .getOrderNumber()
            .getOrderNumber());
        ps.setString(17, orders.get(i)
            .getOrderNumber()
            .getAssistOrderNumber());
        ps.setObject(18, orders.get(i)
            .getOrderSequence());
        ps.setObject(19, orders.get(i)
            .getCartNumber());
        ps.setObject(20, orders.get(i)
            .isPresentReceived());
        ps.setObject(21, orders.get(i)
            .getDeliveryInfo()
            .getDeliveryMethod());
        ps.setObject(22, orders.get(i)
            .getDeliveryInfo()
            .getParcelCode());
        ps.setObject(23, orders.get(i)
            .getDeliveryInfo()
            .getInvoiceNumber());
        ps.setObject(24, orders.get(i)
            .getDeliveryMessage());
        ps.setObject(25, orders.get(i)
            .getBundleCandidate());
        ps.setObject(26, orders.get(i)
            .getOrderer()
            .getName());
        ps.setObject(27, orders.get(i)
            .getOrderer()
            .getMallId());
        ps.setObject(28, orders.get(i)
            .getOrderer()
            .getPhoneNumber1());
        ps.setObject(29, orders.get(i)
            .getOrderer()
            .getPhoneNumber2());
        ps.setBytes(30, UUIDBinaryUtil.toBinary(orders.get(i)
            .getProduct()
            .getProductId()));
        ps.setObject(31, orders.get(i)
            .getProduct()
            .getProductNumber());
        ps.setObject(32, orders.get(i)
            .getProduct()
            .getName());
        ps.setObject(33, orders.get(i)
            .getProduct()
            .getOptionNumber());
        ps.setObject(34, orders.get(i)
            .getProduct()
            .getOption());
        ps.setObject(35, orders.get(i)
            .getProduct()
            .getExtraComponentNumber());
        ps.setObject(36, orders.get(i)
            .getProduct()
            .getExtraComponent());
        ps.setObject(37, orders.get(i)
            .getProduct()
            .isExtraComponentContains());
        ps.setObject(38, orders.get(i)
            .getProduct()
            .getGift());
        ps.setObject(39, orders.get(i)
            .getProduct()
            .getSellerProductCode());
        ps.setObject(40, orders.get(i)
            .getProduct()
            .getSellerProductCodeExtra1());
        ps.setObject(41, orders.get(i)
            .getProduct()
            .getSellerProductCodeExtra2());
        ps.setObject(42, orders.get(i)
            .getProduct()
            .getSellerProductCodeExtra3());
        ps.setObject(43, orders.get(i)
            .getProduct()
            .getSellerProductCodeExtra4());
        ps.setObject(44, orders.get(i)
            .getProduct()
            .getProductAdditionalMessage());
        ps.setObject(45, orders.get(i)
            .getProduct()
            .getBuyerAdditionalMessage());
        ps.setObject(46, orders.get(i)
            .getProduct()
            .getQuantity());
        ps.setObject(47, orders.get(i)
            .getReceiver()
            .getName());
        ps.setObject(48, orders.get(i)
            .getReceiver()
            .getPhoneNumber1());
        ps.setObject(49, orders.get(i)
            .getReceiver()
            .getPhoneNumber2());
        ps.setObject(50, orders.get(i)
            .getReceiver()
            .getZipCode());
        ps.setObject(51, orders.get(i)
            .getReceiver()
            .getAddress1());
        ps.setObject(52, orders.get(i)
            .getReceiver()
            .getAddress2());
        ps.setObject(53, orders.get(i)
            .getPayment()
            .getProductCharge());
        ps.setObject(54, orders.get(i)
            .getPayment()
            .getProductDiscountCharge());
        ps.setObject(55, orders.get(i)
            .getPayment()
            .getOrderDiscountCharge());
        ps.setObject(56, orders.get(i)
            .getPayment()
            .getPoint());
        ps.setObject(57, orders.get(i)
            .getPayment()
            .getCredit());
        ps.setObject(58, orders.get(i)
            .getPayment()
            .getDeliveryChargeType());
        ps.setObject(59, orders.get(i)
            .getPayment()
            .getBasicDeliveryCharge());
        ps.setObject(60, orders.get(i)
            .getPayment()
            .getDistanceDeliveryCharge());
        ps.setObject(61, orders.get(i)
            .getPayment()
            .getPaymentAmount());
        ps.setObject(62, orders.get(i)
            .getPayment()
            .getPaidAt());
        ps.setObject(63, orders.get(i)
            .getCalculation()
            .getCalculatedAmount());
        ps.setObject(64, orders.get(i)
            .getCalculation()
            .getFee());
        ps.setObject(65, orders.get(i)
            .getCalculation()
            .getFeeNumericType());
        ps.setObject(66, orders.get(i)
            .getCustomsClearanceCode());
        ps.setObject(67, orders.get(i)
            .getCustomsClearancePhoneNumber());
        ps.setObject(68, orders.get(i)
            .getOrderedAt());
        ps.setObject(69, orders.get(i)
            .getExpectSendDate());
        ps.setObject(70, orders.get(i)
            .getCollectedAt());
        ps.setObject(71, orders.get(i)
            .getSyncBaseDate());
        ps.setObject(72, orders.get(i)
            .getSentAt());
        ps.setObject(73, orders.get(i)
            .isPreparable());
        ps.setObject(74, orders.get(i)
            .isDelayable());
        ps.setObject(75, orders.get(i)
            .isDeliverable());
        ps.setObject(76, orders.get(i)
            .isVisitable());
        ps.setObject(77, orders.get(i)
            .isDeliveryChangeable());
        ps.setObject(78, orders.get(i)
            .getVisitConfirmType());
        ps.setObject(79, orders.get(i)
            .getRawData());
        ps.setBytes(80, UUIDBinaryUtil.toBinary(orders.get(i)
            .getCreationSyncId()));
        ps.setObject(81, orders.get(i)
            .getCreatedAt());
        ps.setObject(82, orders.get(i)
            .isDeliveryCompletable());
      }

      @Override
      public int getBatchSize() {
        return orders.size();
      }
    });
  }
}
