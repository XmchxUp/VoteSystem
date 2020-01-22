package com.xmchx.vote.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/7 20:28
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException  extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
