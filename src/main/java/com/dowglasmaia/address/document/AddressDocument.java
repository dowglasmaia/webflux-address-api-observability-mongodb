package com.dowglasmaia.address.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "address")
public class AddressDocument {

    @Id
    private String id;
    private String street;
    private String number;
    private String city;
    private String state;
    private String neighborhood;

    @Indexed(unique = true)
    private String zip;

}
