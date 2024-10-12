package com.qticket.payment.adapter.in.web.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class PaymentWidgetController {

    @GetMapping
    String checkoutPage() {
        log.info("Checkout page");
        return "widget/checkout";
    }

    @GetMapping("widget/success")
    String successPage() {
        log.info("success page");
        return "widget/success";
    }

    @GetMapping("fail")
    String failPage() {
        log.info("fail page");
        return "fail";
    }

}
