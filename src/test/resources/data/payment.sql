insert into payment
(approved_at, customer_id, fail_count, is_benefit_applied, is_completed, method, order_id, order_name, payment_key)
values (NULL, 1, 0, true, false, 'EASY_PAY', '9c7bf46c-3b77-4a56-9c17-e944d0b8de41',
        '[1ea051ea-f3dd-4bdd-b080-709cadd7828f:R]', NULL);

insert into payment_order
(amount, concert_id, is_ledger_completed, is_settlement_completed, order_id, payment_id, seat_id, status)
values (120000, 'test-concertId', false, false, '9c7bf46c-3b77-4a56-9c17-e944d0b8de41', 1,
        '1ea051ea-f3dd-4bdd-b080-709cadd7828f', 'PENDING');

insert into benefit
    (coupon_id, discount_amount, discount_policy, payment_id)
values ('test-coupon-Id', 20000, 'PERCENTAGE', 1);
