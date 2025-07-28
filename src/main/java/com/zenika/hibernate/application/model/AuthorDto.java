package com.zenika.hibernate.application.model;

public record AuthorDto(
        Long id,
        String firstname,
        String lastname,
        AuditMetaDataDto auditMetaData
) {}
