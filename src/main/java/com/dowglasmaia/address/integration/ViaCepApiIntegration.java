package com.dowglasmaia.address.integration;


import com.dowglasmaia.address.integration.model.ViaCepModel;
import reactor.core.publisher.Mono;

public interface ViaCepApiIntegration {
    Mono<ViaCepModel> getAddressByZipCode(String zipCode);
}
