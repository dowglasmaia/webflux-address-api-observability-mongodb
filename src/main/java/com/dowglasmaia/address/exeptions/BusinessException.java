package com.dowglasmaia.address.exeptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private static final HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
    private static final long serialVersionUID = 1L;

    public BusinessException(String msg) {
        super(msg);
    }

}
