package com.example.order.exceptions;

public class DeleteCartItemFail extends RuntimeException {

    public DeleteCartItemFail() {
        super("카트아이템 삭제에 실패하였습니다.");
    }
}
