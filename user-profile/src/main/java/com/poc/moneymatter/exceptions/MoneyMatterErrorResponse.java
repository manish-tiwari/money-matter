package com.poc.moneymatter.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MoneyMatterErrorResponse {

    private String exception;
    private String cause;
    private String message;
    private Integer status;

}
