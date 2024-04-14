package com.dowglasmaia.address.service.mapper;

import br.com.dowglasmaia.openapi.model.AddressResponse;
import com.dowglasmaia.address.integration.model.ViaCepModel;

public class AddressModelIntegrationMapper {

    public static AddressResponse toAddressResponse(ViaCepModel model) {
        AddressResponse addressResponse = new AddressResponse();

        addressResponse.setStreet(model.getLogradouro());
        addressResponse.setCity(model.getLocalidade());
        addressResponse.setNeighborhood(model.getBairro());
        addressResponse.setZip(model.getCep());
        addressResponse.setState(model.getUf());

        return addressResponse;
    }
}
