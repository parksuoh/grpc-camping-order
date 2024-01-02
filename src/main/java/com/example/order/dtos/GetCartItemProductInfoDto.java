package com.example.order.dtos;

public record GetCartItemProductInfoDto(
        Long productId,
        Long firstOptionId,
        Long secondOptionId,
        Integer quantity
) {
}
