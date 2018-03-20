package com.nooheat.seonarangevent.advice;

import com.google.gson.JsonObject;
import com.nooheat.seonarangevent.exception.JwtTokenClaimNotFoundException;
import com.nooheat.seonarangevent.exception.JwtTokenStringNotFoundException;
import com.nooheat.seonarangevent.exception.UidNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(value = {JwtTokenClaimNotFoundException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    protected ErrorResponse claimNotFoundExceptionHandle(HttpServletRequest request, Exception exception) {
        return new ErrorResponse(exception.getMessage(), request.getRequestURL().toString());
    }

    @ExceptionHandler(value = {JwtTokenStringNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ErrorResponse tokenStringNotFoundExceptionHandle(HttpServletRequest request, Exception exception) {
        return new ErrorResponse(exception.getMessage(), request.getRequestURL().toString());
    }

    @ExceptionHandler(UidNotValidException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    protected ErrorResponse uidNotFoundException(HttpServletRequest request, Exception exception) {
        return new ErrorResponse(exception.getMessage(), request.getRequestURL().toString());
    }

//    UnexpectedSortException은 발생할 일이 없어 주석처리.
//    @ExceptionHandler(UnexpectedSortException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    protected ErrorResponse unexpectedSortExceptionHandle(HttpServletRequest request, Exception exception) {
//        return new ErrorResponse(LocalDateTime.now(), exception.getMessage(), request.getRequestURL().toString());
//    }
}
