package com.nooheat.seonarangevent.exception;

public class UnexpectedSortException extends RuntimeException {
    public UnexpectedSortException(String sort) {
        super("Unexpected sort option '" + sort + "'");
    }
}
