package com.dowglasmaia.address.integration.model;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViaCepModel {
    @Getter
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;
    private boolean erro;

}
