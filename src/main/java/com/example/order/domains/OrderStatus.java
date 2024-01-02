package com.example.order.domains;

import com.example.order.exceptions.OrderStatusNotMatch;

import java.util.Arrays;

public enum OrderStatus {

    READY("READY"),
    DELIVERY("DELIVERY"),
    COMPLETE("COMPLETE"),
    CANCELED("CANCELED");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public static OrderStatus isInStatus(String status){
        return Arrays.stream(OrderStatus.values())
                .filter(v -> v.value.equals(status))
                .findFirst()
                .orElseThrow(OrderStatusNotMatch::new);
    }
}
