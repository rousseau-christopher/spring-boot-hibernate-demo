package com.zenika.hibernate.infrastructure.repository.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Getter
@Setter
@Embeddable
public class AuditMetaData {
    @CreatedDate
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false, updatable = false)
    private Instant createdDate;

    @LastModifiedDate
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
    private Instant modifiedDate;

    @LastModifiedBy
    private String lastModifiedBy;
}
