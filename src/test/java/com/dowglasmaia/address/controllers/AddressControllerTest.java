package com.dowglasmaia.address.controllers;


import com.dowglasmaia.address.exeptions.NotFoundException;
import com.dowglasmaia.address.mockbuilder.MockBuilder;
import com.dowglasmaia.address.service.AddressService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@ActiveProfiles(value = "test")
@WebFluxTest(AddressController.class)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
public class AddressControllerTest {

    final String URI_PATH = "/api/v1/addresses/find-by-zipcode";

    @MockBean
    private AddressService service;

    @Autowired
    WebTestClient webTestClient;

    @Test
    @DisplayName("Shoul perform GET /addresses/find-by-zipcode")
    void souldFindByZipCodeSuccessfully() {
        // Mock do obj de resposta
        var responseMock = MockBuilder.buildAddressResponse();

        String queryParam = "0123";

        // Mock do service
        Mockito.when(service.findByZipCode(Mockito.anyString()))
                .thenReturn(Mono.just(responseMock));


        //Execute a requisição
        WebTestClient.ResponseSpec response = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path(URI_PATH)
                        .queryParam("zipCode", queryParam)
                        .build())
                .exchange();


        //Verifique se a resposta

        response.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.city").isEqualTo("Floripa")
                .jsonPath("$.street").isEqualTo("Rua Test01");

    }

    @Test
    @DisplayName("Shoul return HTTP 404 when address is not found")
    void souldFindByZipCodeHttp404() {
        Mockito.when(service.findByZipCode(Mockito.anyString()))
                .thenThrow(new NotFoundException("Address Not Found"));

        String queryParam = "0123";

        //Execute a requisição
        WebTestClient.ResponseSpec response = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path(URI_PATH)
                        .queryParam("zipCode", queryParam)
                        .build())
                .exchange();


        //Verifique se a resposta
        response.expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Address Not Found");


    }



}
