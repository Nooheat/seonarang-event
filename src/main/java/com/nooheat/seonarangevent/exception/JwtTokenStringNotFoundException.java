package com.nooheat.seonarangevent.exception;

public class JwtTokenStringNotFoundException extends RuntimeException {
    public JwtTokenStringNotFoundException() {
        super("'twitch-event-access-token' required");
    }
}
