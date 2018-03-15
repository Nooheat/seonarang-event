package com.nooheat.seonarangevent.exception;

public class UserNotPermittedException extends RuntimeException {
    public UserNotPermittedException(String twitchId) {
        super(twitchId + "님에겐 이벤트를 열 권한이 없습니다.");
    }
}
