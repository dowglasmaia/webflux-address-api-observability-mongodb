package com.dowglasmaia.address.exeptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandardError implements Serializable {

    private Long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

}
