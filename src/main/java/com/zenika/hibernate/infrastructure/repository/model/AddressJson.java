package com.zenika.hibernate.infrastructure.repository.model;

import lombok.Data;

@Data
public class AddressJson {
    private String street;
    private String city;
    private String zipCode;
}
