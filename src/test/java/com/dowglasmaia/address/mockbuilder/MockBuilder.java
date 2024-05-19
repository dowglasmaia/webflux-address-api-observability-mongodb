package com.dowglasmaia.address.mockbuilder;

import br.com.dowglasmaia.openapi.model.AddressResponse;
import com.dowglasmaia.address.document.AddressDocument;
import com.dowglasmaia.address.integration.model.ViaCepModel;

public class MockBuilder {

    public static AddressResponse buildAddressResponse(){
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


    public static AddressDocument buildAddressDocument(){
        var response = new AddressDocument();
        response.setId("id-019");
        response.setStreet("Rua Test08");
        response.setNeighborhood("Centro");
        response.setNumber("300");
        response.setCity("Floripa");
        response.setState("SC");
        response.setZip("01233566");
        return response;
    }

    public static ViaCepModel buildViaCepModel(){
        return ViaCepModel.builder()
              .cep("55024-780")
              .logradouro("Rua Test08")
              .complemento("")
              .bairro("Centro")
              .localidade("Floripa")
              .uf("SC")
              .ibge("0123")
              .gia("")
              .ddd("48")
              .siafi("012")
              .erro(false)
              .build();
    }


}
