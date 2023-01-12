package me.hjhng.springdatajdbcpractice.order;


import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Embedded.OnEmpty;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Table("Orders")
public class Order implements Persistable<UUID> {

  @Id
  private final UUID id;

  @Column("syncId")
  private final UUID syncId;

  @Column("creationSyncId")
  private final UUID creationSyncId;

  @Column("collectId")
  private final UUID collectId;

  @Column("packingUnitId")
  private final UUID packingUnitId;

  @Column("mallType")
  private final String mallType;

  @Column("mallAccountId")
  private final UUID mallAccountId;

  @Column("corporationId")
  private final UUID corporationId;

  private final String status;

  @Column("rawStatus")
  private final String rawStatus;

  @Column("orderType")
  private final String orderType;

  @Column("inInitial")
  private final boolean inInitial;

  private final boolean prepared;

  @Column("inProgress")
  private final boolean inProgress;

  private final boolean finished;

  private final boolean canceled;

  @Embedded(onEmpty = OnEmpty.USE_EMPTY)
  private final OrderNumber orderNumber;

  @Column("orderSequence")
  private final String orderSequence;

  @Column("cartNumber")
  private final String cartNumber;

  @Column("presentReceived")
  private final boolean presentReceived;

  @Embedded(onEmpty = OnEmpty.USE_EMPTY)
  private final DeliveryInfo deliveryInfo;

  @Column("deliveryMessage")
  private final String deliveryMessage;

  @Column("bundleCandidate")
  private final String bundleCandidate;

  @Embedded(onEmpty = OnEmpty.USE_EMPTY)
  private final Orderer orderer;

  @Embedded(onEmpty = OnEmpty.USE_EMPTY)
  private final OrderedProduct product;

  @Embedded(onEmpty = OnEmpty.USE_EMPTY)
  private final Receiver receiver;

  @Embedded(onEmpty = OnEmpty.USE_EMPTY)
  private final Payment payment;

  @Embedded(onEmpty = OnEmpty.USE_EMPTY)
  private final Calculation calculation;

  @Column("customsClearanceCode")
  private final String customsClearanceCode;

  @Column("customsClearancePhoneNumber")
  private final String customsClearancePhoneNumber;

  @Column("orderedAt")
  private final LocalDateTime orderedAt;

  @Column("expectSendDate")
  private final LocalDateTime expectSendDate;

  @Column("collectedAt")
  private final LocalDateTime collectedAt;

  @Column("createdAt")
  private final LocalDateTime createdAt;

  @Column("syncBaseDate")
  private final LocalDateTime syncBaseDate;

  @Column("sentAt")
  private final LocalDateTime sentAt;

  private final boolean preparable;

  private final boolean delayable;

  private final boolean deliverable;

  private final boolean visitable;

  @Column("deliveryChangeable")
  private final boolean deliveryChangeable;

  @Column("deliveryCompletable")
  private final boolean deliveryCompletable;

  @Column("visitConfirmType")
  private final String  visitConfirmType;

  @Column("rawData")
  private final String rawData;

  @Builder
  public Order(UUID id, UUID syncId, UUID creationSyncId, UUID collectId, UUID packingUnitId, String mallType, UUID mallAccountId, UUID corporationId, String status, String rawStatus,
      String orderType, boolean inInitial, boolean prepared, boolean inProgress, boolean finished, boolean canceled, OrderNumber orderNumber, String orderSequence, String cartNumber,
      boolean presentReceived, DeliveryInfo deliveryInfo, String deliveryMessage, String bundleCandidate, Orderer orderer, OrderedProduct product, Receiver receiver, Payment payment,
      Calculation calculation, String customsClearanceCode, String customsClearancePhoneNumber, LocalDateTime orderedAt, LocalDateTime expectSendDate, LocalDateTime collectedAt,
      LocalDateTime createdAt, LocalDateTime syncBaseDate, LocalDateTime sentAt, boolean preparable, boolean delayable, boolean deliverable, boolean visitable, boolean deliveryChangeable,
      boolean deliveryCompletable, String visitConfirmType, String rawData) {
    this.id = id;
    this.syncId = syncId;
    this.creationSyncId = creationSyncId;
    this.collectId = collectId;
    this.packingUnitId = packingUnitId;
    this.mallType = mallType;
    this.mallAccountId = mallAccountId;
    this.corporationId = corporationId;
    this.status = status;
    this.rawStatus = rawStatus;
    this.orderType = orderType;
    this.inInitial = inInitial;
    this.prepared = prepared;
    this.inProgress = inProgress;
    this.finished = finished;
    this.canceled = canceled;
    this.orderNumber = orderNumber;
    this.orderSequence = orderSequence;
    this.cartNumber = cartNumber;
    this.presentReceived = presentReceived;
    this.deliveryInfo = deliveryInfo;
    this.deliveryMessage = deliveryMessage;
    this.bundleCandidate = bundleCandidate;
    this.orderer = orderer;
    this.product = product;
    this.receiver = receiver;
    this.payment = payment;
    this.calculation = calculation;
    this.customsClearanceCode = customsClearanceCode;
    this.customsClearancePhoneNumber = customsClearancePhoneNumber;
    this.orderedAt = orderedAt;
    this.expectSendDate = expectSendDate;
    this.collectedAt = collectedAt;
    this.createdAt = createdAt;
    this.syncBaseDate = syncBaseDate;
    this.sentAt = sentAt;
    this.preparable = preparable;
    this.delayable = delayable;
    this.deliverable = deliverable;
    this.visitable = visitable;
    this.deliveryChangeable = deliveryChangeable;
    this.deliveryCompletable = deliveryCompletable;
    this.visitConfirmType = visitConfirmType;
    this.rawData = rawData;
  }

  @Override
  public boolean isNew() {
    return true;
  }
}
