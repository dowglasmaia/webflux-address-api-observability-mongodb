package com.dowglasmaia.address.service.mapper;

import br.com.dowglasmaia.openapi.model.AddressResponse;
import com.dowglasmaia.address.document.AddressDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressResponse toAddressResponse(AddressDocument document);

    AddressDocument toAddressDocument(AddressResponse addressResponse);

}
