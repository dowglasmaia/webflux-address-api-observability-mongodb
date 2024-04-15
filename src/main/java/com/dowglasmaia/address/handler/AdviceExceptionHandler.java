package com.dowglasmaia.address.handler;

import com.dowglasmaia.address.exeptions.BusinessException;
import com.dowglasmaia.address.exeptions.NotFoundException;
import com.dowglasmaia.address.exeptions.StandardError;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@Log4j2
@RestControllerAdvice
public class AdviceExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public Mono<ResponseEntity<StandardError>> handleNotFoundException(NotFoundException ex) {
        StandardError error = StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .error("Not Found")
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(error));
    }

    @ExceptionHandler(BusinessException.class)
    public Mono<ResponseEntity<StandardError>> handleBusinessException(BusinessException ex) {
        StandardError error = StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .error("Not Found")
                .message(ex.getMessage())
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .build();
        return Mono.just(ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error));
    }
}
