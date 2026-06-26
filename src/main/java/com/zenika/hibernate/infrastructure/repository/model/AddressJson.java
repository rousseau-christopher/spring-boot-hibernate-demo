package com.zenika.hibernate.infrastructure.repository.model;


public record AddressJson (
    String street,
    String city,
    String zipCode,
    Country country
) {
    public record Country(
            String code,
            String name
    ) {}
}
