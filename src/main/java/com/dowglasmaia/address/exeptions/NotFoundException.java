package com.dowglasmaia.address.exeptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends RuntimeException {

    private static final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
    private static final long serialVersionUID = 1L;

    public NotFoundException(String msg) {
        super(msg);
    }

}
