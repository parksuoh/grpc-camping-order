package com.example.order.dtos;

public record GetOrderItemDto(
        Long orderItemId,
        String productName,
        Long productPrice,
        String productFirstOptionName,
        Long productFirstOptionPrice,
        String productSecondOptionName,
        Long productSecondOptionPrice,
        Long unitPrice,
        Integer quantity,
        Long totalPrice
) {}

