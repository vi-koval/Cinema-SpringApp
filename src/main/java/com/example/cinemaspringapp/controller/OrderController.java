package com.example.cinemaspringapp.controller;

import com.example.cinemaspringapp.model.Order;
import com.example.cinemaspringapp.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("/order-failure")
    public String orderFailurePage() {
        return "/order/order-failure";
    }

    @PostMapping("/success")
    public String createOrder(HttpSession session) {
        Order order = (Order) session.getAttribute("confirmationOrder");

        if (order == null) {
            return "redirect:/error";
        }

        try {
            orderService.createOrder(order);
        } catch (Exception e) {
            return "redirect:/order-failure";
        }

        session.removeAttribute("confirmationOrder");

        return "redirect:/final-success-page";
    }


}
