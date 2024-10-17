CREATE TABLE payment_event
(
    id                  BIGINT                              AUTO_INCREMENT PRIMARY KEY,
    customer_id         BIGINT                              NOT NULL,
    order_id            VARCHAR(255) UNIQUE,
    order_name          VARCHAR(255)                        NOT NULL,
    coupon_id           VARCHAR(255) UNIQUE,
    discount_amount     DECIMAL(20, 2),
    method              ENUM ('CARD','EASY_PAY','TRANSFER') NOT NULL,
    payment_key         VARCHAR(255) UNIQUE,
    is_completed        BOOLEAN                             NOT NULL DEFAULT FALSE,
    approved_at         DATETIME,
    fail_count          INT                                 NOT NULL DEFAULT 0
) engine = InnoDB;

CREATE TABLE payment_order
(
    id                      BIGINT                                                                 AUTO_INCREMENT PRIMARY KEY,
    payment_event_id        BIGINT                                                                 NOT NULL,
    customer_id             BIGINT                                                                 NOT NULL,
    order_id                VARCHAR(255)                                                           NOT NULL,
    concert_id              VARCHAR(255)                                                           NOT NULL,
    seat_id                 VARCHAR(255)                                                           NOT NULL,
    amount                  DECIMAL(20, 2)                                                         NOT NULL,
    status                  ENUM ('PENDING', 'PROCESSING', 'SUCCESS', 'FAILED', 'UNKNOWN_APPROVE') NOT NULL DEFAULT 'PENDING',
    is_ledger_completed     BOOLEAN                                                                NOT NULL DEFAULT FALSE,
    is_settlement_completed BOOLEAN                                                                NOT NULL DEFAULT FALSE,
    FOREIGN KEY (payment_event_id) REFERENCES payment_event (id)
) engine = InnoDB;

CREATE TABLE payment_order_history
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    payment_order_id BIGINT NOT NULL,
    previous_status  ENUM ('PENDING', 'PROCESSING', 'SUCCESS', 'FAILED', 'UNKNOWN_APPROVE'),
    new_status       ENUM ('PENDING', 'PROCESSING', 'SUCCESS', 'FAILED', 'UNKNOWN_APPROVE'),
    reason           VARCHAR(255),
    FOREIGN KEY (payment_order_id) REFERENCES payment_order (id)
)
