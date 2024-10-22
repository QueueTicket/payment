insert into payment(approved_at, customer_id, fail_count, is_benefit_applied,
                    is_completed, method, order_id, order_name, payment_key)
values (NULL, 1, 0, true, false, 'EASY_PAY', '9c7bf46c-3b77-4a56-9c17-e944d0b8de41',
        '[1ea051ea-f3dd-4bdd-b080-709cadd7828f:R]', NULL);

insert into payment_item(payment_id, order_id, concert_id, seat_id,
                         amount, status, is_ledger_completed, is_settlement_completed)
values (1, '9c7bf46c-3b77-4a56-9c17-e944d0b8de41', 'test-concertId', '1ea051ea-f3dd-4bdd-b080-709cadd7828f', 120000,
        'PENDING', false, false),
       (1, '9c7bf46c-3b77-4a56-9c17-e944d0b8de41', 'test-concertId', 'add7828f-f3dd-4bdd-b080-709c1ea051ea', 120000,
        'PENDING', false, false)
;

insert into benefit(payment_id, coupon_id, discount_amount)
values (1, 'test-coupon-Id', 20000);
