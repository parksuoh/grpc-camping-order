package com.example.order.exceptions;

public class ProductNotExist extends RuntimeException {

    public ProductNotExist() {
        super("상품이 존재하지 않습니다.");
    }
}
