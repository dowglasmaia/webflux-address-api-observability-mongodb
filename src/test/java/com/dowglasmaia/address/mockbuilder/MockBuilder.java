package com.dowglasmaia.address.mockbuilder;

import br.com.dowglasmaia.openapi.model.AddressResponse;

public class MockBuilder {

    public static AddressResponse buildAddressResponse() {
        var response = new AddressResponse();
        response.setId("id-012");
        response.setStreet("Rua Test01");
        response.setNeighborhood("Centro");
        response.setNumber("320");
        response.setCity("Floripa");
        response.setState("SC");
        response.setZip("01233566");

        return response;
    }
}
