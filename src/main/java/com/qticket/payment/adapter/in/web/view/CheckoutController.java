package com.qticket.payment.adapter.in.web.view;

import com.qticket.payment.adapter.in.web.view.request.CheckoutRequest;
import com.qticket.payment.application.port.in.CheckoutUseCase;
import com.qticket.payment.domain.CheckoutResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutUseCase checkoutUseCase;

    @GetMapping
    String checkoutPage(CheckoutRequest request, Model model) {
        CheckoutResult result = checkoutUseCase.checkout(request.toCommand());

        model.addAttribute("orderId", result.orderId());
        model.addAttribute("orderName", result.orderName());
        model.addAttribute("amount", result.amount());

        return "widget/checkout";
    }

}
