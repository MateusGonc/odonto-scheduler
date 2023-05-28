package com.br.odontoscheduler.model;

import lombok.Data;

@Data
public class Address {
    private String street;
    private String number;
    private String zipcode;
    private String city;
    private String state;
}
