package com.zenika.hibernate.application.model;

import java.time.Instant;

public record AuditMetaDataDto (
        Instant createdDate,
        Instant modifiedDate,
        String lastModifiedBy
) {}
