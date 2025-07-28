package com.zenika.hibernate.application.model;

import org.springframework.data.history.RevisionMetadata.RevisionType;

import java.time.Instant;

public record RevisionDto(
        Integer revisionNumber,
        Instant revisionDate,
        RevisionType revisionType,
        BookDto book
) {
}
