package com.qticket.payment.adapter.in.web.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentWidgetController {

    @GetMapping("widget/success")
    String successPage() {
        return "widget/success";
    }

    @GetMapping("fail")
    String failPage() {
        return "fail";
    }

}
