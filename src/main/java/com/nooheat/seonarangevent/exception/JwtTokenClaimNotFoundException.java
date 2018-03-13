package com.nooheat.seonarangevent.exception;

public class JwtTokenClaimNotFoundException extends RuntimeException {
    public JwtTokenClaimNotFoundException(String claimName) {
        super("Your token doesn't contain '" + claimName + "' value");
    }
}
