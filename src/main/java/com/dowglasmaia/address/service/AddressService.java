package com.dowglasmaia.address.service;

import br.com.dowglasmaia.openapi.model.AddressResponse;
import reactor.core.publisher.Mono;

public interface AddressService {
    Mono<AddressResponse> findByZipCode(String zipCode);
}
