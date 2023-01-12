CREATE TABLE IF NOT EXISTS Memo
(
    memoId  bigint auto_increment primary key,
    title   varchar(255)  not null,
    content varchar(2000) null,
    createdAt DATETIME not null,
    updatedAt DATETIME null
);

CREATE TABLE IF NOT EXISTS Orders
(
    id                          binary(16)           not null comment '주문 ID'
        primary key,
    syncId                      binary(16)           not null comment 'syncId',
    collectId                   binary(16)           not null comment 'collectId',
    packingUnitId               binary(16)           not null comment 'packingUnitId',
    mallType                    varchar(20)          null comment '쇼핑몰타입',
    mallAccountId               binary(16)           not null comment 'mallAccountId',
    corporationId               binary(16)           not null comment 'mallAccountId',
    status                      varchar(45)          null comment '주문상태',
    rawStatus                   varchar(45)          null comment '쇼핑몰의 주문 상태 코드',
    orderType                   varchar(25)          not null comment '주문종류',
    inInitial                   tinyint(1)           not null comment '신규주문 여부',
    prepared                    tinyint(1)           not null comment '발주확인 주문 여부',
    inProgress                  tinyint(1)           not null comment '처리중인 주문 여부',
    finished                    tinyint(1)           not null comment '주문 완료 여부',
    canceled                    tinyint(1)           not null comment '주문 취소 여부',
    orderNumber                 varchar(45)          not null comment '주문 번호',
    assistOrderNumber           text                 not null comment '보조 주문 번호',
    orderSequence               text                 null comment '주문 순서',
    cartNumber                  text                 null comment '카트 번호',
    presentReceived             tinyint(1)           null comment '선물 수락 여부',
    deliveryMethod              varchar(25)          null comment '배송방법',
    parcelCode                  varchar(30)          null comment '택배사코드',
    invoiceNumber               varchar(30)          null comment '송장번호',
    deliveryMessage             text                 null comment '배송메시지',
    bundleCandidate             text                 null comment '합포장 번호',
    ordererName                 text                 null comment '주문자 명',
    ordererMallId               text                 null comment '주문자 ID',
    ordererPhoneNumber1         text                 null comment '주문자 연락처1',
    ordererPhoneNumber2         text                 null comment '주문자 연락처2',
    productId                   binary(16)           null comment 'productId',
    productNumber               text                 null comment '상품 번호',
    productName                 text                 null comment '상품명',
    optionNumber                text                 null comment '옵션 번호',
    optionName                  text                 null comment '옵션명',
    extraComponentNumber        text                 null comment '추가 구성품 번호',
    extraComponent              text                 null comment '추가 구성품',
    extraComponentContains      bit                  null comment '해당 주문이 추가 구성품을 포함하고 있는지, 혹은 추가구성품인지 여부',
    gift                        text                 null comment '사은품',
    sellerProductCode           text                 null comment '판매자 상품 코드',
    sellerProductCodeExtra1     text                 null comment '판매자 상품 코드1',
    sellerProductCodeExtra2     text                 null comment '판매자 상품 코드2',
    sellerProductCodeExtra3     text                 null comment '판매자 상품 코드3',
    sellerProductCodeExtra4     text                 null comment '판매자 상품 코드4',
    productAdditionalMessage    text                 null comment '상품 추가 메시지',
    buyerAdditionalMessage      text                 null comment '구매자 추가 메시지',
    quantity                    int                  null comment '수량',
    receiverName                text                 null comment '수취인 이름',
    receiverPhoneNumber1        text                 null comment '수취인 전화번호',
    receiverPhoneNumber2        text                 null comment '수취인 전화번호2',
    receiverZipCode             varchar(10)          null comment '수취인 우편번호',
    receiverAddress1            text                 null comment '수취인 주소',
    receiverAddress2            text                 null comment '수취인 상세 주소',
    productCharge               varchar(50)          null comment '상품 금액',
    productDiscountCharge       varchar(50)          null comment '상품 할인 금액',
    orderDiscountCharge         varchar(50)          null comment '주문 할인 금액',
    orderPointCharge            varchar(50)          null comment '포인트 결제 금액',
    orderCreditCharge           varchar(50)          null comment '신용카드 결제 금액',
    deliveryChargeType          varchar(20)          null comment '배송비 결제 타입',
    basicDeliveryCharge         varchar(45)          null comment '배송비 결제 금액',
    distanceDeliveryCharge      varchar(45)          null comment '배송비 도서산간 결제 금액',
    paymentAmount               varchar(45)          null comment '결제 금액',
    paidAt                      datetime             null comment '결제 일시',
    calculatedAmount            int                  null comment '정산금액',
    fee                         int                  null comment '수수료',
    feeNumericType              tinyint(1)           null comment '쇼핑몰 수수료 음수(-1), 양수(1) 구분',
    customsClearanceCode        text                 null comment '고유 통관 번호',
    customsClearancePhoneNumber text                 null comment '고유 통관 번호',
    orderedAt                   datetime             null comment '주문 일시',
    expectSendDate              datetime             null comment '발송 예정 일시',
    collectedAt                 datetime             null comment '수집 일시',
    syncBaseDate                datetime             null comment '수집 기준 일시',
    sentAt                      datetime             null comment '발송 일시',
    preparable                  tinyint(1)           null comment '발주 확인 가능 여부',
    delayable                   tinyint(1)           null comment '발송 지연 가능 여부',
    deliverable                 tinyint(1)           null comment '송장번호 등록 가능 (발송처리 가능) 여부',
    visitable                   tinyint(1)           null comment '방문수령 가능 여부',
    deliveryChangeable          tinyint(1)           null comment '발송정보 변경 가능 여부',
    visitConfirmType            varchar(45)          null comment '방문 수령 처리 타입',
    rawData                     text                 null comment 'rawData',
    creationSyncId              binary(16)           not null comment '최초 SyncId',
    createdAt                   datetime(6)          null comment '생성일시',
    deliveryCompletable         tinyint(1) default 0 null comment '배송완료 처리 가능여부'
);