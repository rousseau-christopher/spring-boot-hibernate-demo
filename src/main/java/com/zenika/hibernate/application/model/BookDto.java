package com.zenika.hibernate.application.model;

import java.time.Instant;

public record BookDto(
        Long id,
        String label,
        String isbn,
        String summary,
        Float note,
        Instant createdDate,
        Instant modifiedDate,
        String lastModifiedBy
) {}