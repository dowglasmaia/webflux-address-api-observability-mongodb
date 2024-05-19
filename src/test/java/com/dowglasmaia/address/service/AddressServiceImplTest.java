package com.dowglasmaia.address.service;

import com.dowglasmaia.address.exeptions.BusinessException;
import com.dowglasmaia.address.integration.ViaCepApiIntegration;
import com.dowglasmaia.address.repository.AddressRepository;
import com.dowglasmaia.address.service.impl.AddressServiceImpl;
import com.dowglasmaia.address.service.mapper.AddressMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.dowglasmaia.address.mockbuilder.MockBuilder.*;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
public class AddressServiceImplTest {


    @InjectMocks
    private AddressServiceImpl service;

    @MockBean
    private AddressRepository repository;

    @MockBean
    private AddressMapper mapper;

    @MockBean
    private ViaCepApiIntegration viaCepApiIntegration;

    @Test
    void souldFindByZipCodeSuccessfully(){

        var responseExpect = buildAddressResponse();

        var zipCode = "55024-780";

        when(repository.findByZip(anyString()))
              .thenReturn(Mono.just(buildAddressDocument()));

        when(viaCepApiIntegration.getAddressByZipCode(anyString()))
              .thenReturn(Mono.just(buildViaCepModel()));

        when(mapper.toAddressResponse(buildAddressDocument()))
              .thenReturn( buildAddressResponse() );


        var result = service.findByZipCode(zipCode);


        StepVerifier.create(result)
              .expectNext(responseExpect)
              .verifyComplete();
    }

    @Test
    void souldFindByZipCodeBusinessException(){
        var zipCode = "55024780";

        var result = service.findByZipCode(zipCode);

        StepVerifier.create(result)
              .expectError(BusinessException.class);

    }


}
