package com.poc.moneymatter.exceptions.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class MoneyMatterErrorResponse {

    private String exception;
    private String cause;
    private String message;
    private Integer status;
    private Date timestamp;

}
