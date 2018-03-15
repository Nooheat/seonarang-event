package com.nooheat.seonarangevent.exception;

public class UidNotValidException extends RuntimeException {
    public UidNotValidException() {
        super("존재하지 않는 계정을 통해 액세스 시도하셨습니다.");
    }
}
