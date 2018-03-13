package com.nooheat.seonarangevent.advice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ErrorResponse {
    @JsonFormat(pattern = "yyy-MM-dd kk:mm:ss.SSS")
    private LocalDateTime timestamp;
    private String message;
    private String path;

}

//TODO: failed to invoke ~
//TODO: converter not found