package com.zenika.hibernate.application.model;

public record BookDto (
        long id,
        String label,
        String isbn,
        String summary,
        Float note,
        AuditMetaDataDto auditMetaData
) implements AuditMetaDataItem {}