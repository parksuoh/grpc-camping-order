package com.example.order.exceptions;

public class OrderNotExist extends RuntimeException {

    public OrderNotExist() {
        super("주문이 존재하지 않습니다.");
    }
}
