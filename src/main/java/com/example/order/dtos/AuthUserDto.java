package com.example.order.dtos;

public record AuthUserDto(
        String name,
        String role,
        String accessToken
) {}
