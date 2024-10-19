CREATE TABLE payment
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '결제 ID',
    customer_id        BIGINT       NOT NULL COMMENT '결제 사용자 ID',
    order_id           VARCHAR(255) UNIQUE COMMENT '예약 정보와 UUID를 기반으로 Application에서 생성된 주문 ID',
    order_name         VARCHAR(255) NOT NULL COMMENT '예약 정보를 기반으로 생성된 주문 명',
    is_benefit_applied BOOLEAN      NOT NULL DEFAULT FALSE COMMENT '할인 적용 여부',
    method             ENUM ('CARD','EASY_PAY','TRANSFER') NOT NULL COMMENT '결제 수단',
    is_completed       BOOLEAN      NOT NULL DEFAULT FALSE COMMENT '결제 승인 완료 여부',
    payment_key        VARCHAR(255) UNIQUE COMMENT 'PG 승인 후 반환되는 KEY',
    fail_count         INT          NOT NULL DEFAULT 0 COMMENT '결제 실패 횟수',
    approved_at        DATETIME COMMENT 'PG 승인 일시'
) engine = InnoDB;

CREATE TABLE payment_order
(
    id                      BIGINT AUTO_INCREMENT PRIMARY KEY,
    payment_id              BIGINT         NOT NULL COMMENT '결제 ID',
    order_id                VARCHAR(255)   NOT NULL COMMENT '주문 ID',
    concert_id              VARCHAR(255)   NOT NULL COMMENT '공연 ID',
    seat_id                 VARCHAR(255)   NOT NULL COMMENT '좌석 ID',
    amount                  DECIMAL(20, 2) NOT NULL COMMENT '좌석 금액',
    status                  ENUM ('PENDING', 'PROCESSING', 'SUCCESS', 'FAILED', 'UNKNOWN_APPROVE') NOT NULL DEFAULT 'PENDING' COMMENT '결제 상태',
    is_ledger_completed     BOOLEAN        NOT NULL DEFAULT FALSE COMMENT '원장 생성 완료 여부',
    is_settlement_completed BOOLEAN        NOT NULL DEFAULT FALSE COMMENT '정산 완료 여부',
    FOREIGN KEY (payment_id) REFERENCES payment (id)
) engine = InnoDB;

CREATE TABLE payment_order_history
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    payment_order_id BIGINT NOT NULL COMMENT '결제 항목 ID',
    previous_status  ENUM ('PENDING', 'PROCESSING', 'SUCCESS', 'FAILED', 'UNKNOWN_APPROVE') COMMENT '이전 결제 상태',
    new_status       ENUM ('PENDING', 'PROCESSING', 'SUCCESS', 'FAILED', 'UNKNOWN_APPROVE') COMMENT '신규 결제 상태',
    reason           VARCHAR(255) COMMENT '결제 사유',
    FOREIGN KEY (payment_order_id) REFERENCES payment_order (id)
) engine = InnoDB;

CREATE TABLE benefit
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    payment_id      BIGINT         NOT NULL COMMENT '결제 ID',
    coupon_id       VARCHAR(255) UNIQUE COMMENT '쿠폰 ID',
    discount_amount DECIMAL(20, 2) NOT NULL COMMENT '할인 적용 금액',
    discount_policy VARCHAR(255)   NOT NULL COMMENT '할인 정책',
    FOREIGN KEY (payment_id) REFERENCES payment (id)
) engine = InnoDB;
