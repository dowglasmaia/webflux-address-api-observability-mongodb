package com.dowglasmaia.address.controllers;


import br.com.dowglasmaia.openapi.api.AddressesApi;
import br.com.dowglasmaia.openapi.model.AddressResponse;
import com.dowglasmaia.address.service.AddressService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
public class AddressController implements BaseController, AddressesApi {
    @Autowired
    private AddressService service;

    @Override
    public Mono<ResponseEntity<AddressResponse>> findByZipCode(String zipCode, ServerWebExchange exchange) {
        return service.findByZipCode(zipCode)
                .doFirst(() -> log.info("Start endpoint findByZipCode"))
                .flatMap(addressResponse -> Mono.just(ResponseEntity
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(addressResponse)));
    }
}
