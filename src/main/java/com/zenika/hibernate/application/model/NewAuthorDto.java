package com.zenika.hibernate.application.model;

import com.zenika.hibernate.infrastructure.repository.model.AddressJson;

public record NewAuthorDto(
        String firstname,
        String lastname,
        AddressJson address
) {}
