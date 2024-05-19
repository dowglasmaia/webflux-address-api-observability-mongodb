package com.dowglasmaia.address.service.impl;


import br.com.dowglasmaia.openapi.model.AddressResponse;
import com.dowglasmaia.address.document.AddressDocument;
import com.dowglasmaia.address.exeptions.BusinessException;
import com.dowglasmaia.address.exeptions.NotFoundException;
import com.dowglasmaia.address.integration.ViaCepApiIntegration;
import com.dowglasmaia.address.repository.AddressRepository;
import com.dowglasmaia.address.service.AddressService;
import com.dowglasmaia.address.service.mapper.AddressMapper;
import com.dowglasmaia.address.service.mapper.AddressModelIntegrationMapper;
import com.dowglasmaia.address.util.ZipCodeValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Log4j2
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository repository;
    @Autowired
    private AddressMapper mapper;

    @Autowired
    private ViaCepApiIntegration viaCepApiIntegration;


    @Override
    public Mono<AddressResponse> findByZipCode(String zipCode) {
        if (ZipCodeValidator.isValidCEP(zipCode)) {
            return repository.findByZip(zipCode)
                    .doFirst(() -> log.info("Start Method findByZipCode with zipCode: {}", zipCode))
                    .flatMap(document -> Mono.just(mapper.toAddressResponse(document)))
                    .switchIfEmpty(
                            this.getAddressByViaCepApi(zipCode)
                                    .doFirst(() -> log.info("Address not exist in DataBase with zip Code: {}", zipCode))
                                    .doFirst(() -> log.info("Start Method getAddressByViaCepApi with zipCode: {}", zipCode))
                                    .flatMap( addressResponse -> insert(addressResponse) )

                    )
                    .onErrorResume(error -> Mono.error(new RuntimeException("Unexpected error", error)))
                    .doOnSuccess(response -> log.info("Address found with Sucecessfully"))
                    .doOnError(error -> log.error("Find Address failed: {}", error.getMessage()));
        }

        return Mono.error(new BusinessException("Zip Code is Invalid"));
    }


    private Mono<AddressResponse> getAddressByViaCepApi(String zipCode) {
        return viaCepApiIntegration.getAddressByZipCode(zipCode)
                .flatMap(address -> {
                    if (address == null || address.isErro()) {
                        log.error("Address Not Found for zip code " + zipCode);
                        return Mono.error(new NotFoundException("Address Not Found"));
                    } else {
                        var addressResponse = AddressModelIntegrationMapper.toAddressResponse(address);
                        return Mono.just(addressResponse);
                    }
                });
    }

    private Mono<AddressResponse> insert(AddressResponse addressResponse) {
        AddressDocument document = mapper.toAddressDocument(addressResponse);
        return repository.save(document)
                .map(savedDocument -> mapper.toAddressResponse(savedDocument))
                .doOnSuccess(response -> log.info("Sucecessfully saved address"))
                .doOnError(error -> log.error("Insert failed"))
                .onErrorResume(error -> Mono.error(new RuntimeException("Insert failed")));
    }
}
