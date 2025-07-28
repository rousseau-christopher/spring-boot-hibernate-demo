package com.zenika.hibernate.infrastructure.repository.model;


import jakarta.persistence.Embedded;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Audited // mandatory or the fields defined here with not be audited by enver
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // Needed for the annotation in AuditMetaData
public abstract class AbstractAuditEntity {
    @Embedded
    private AuditMetaData auditMetaData = new AuditMetaData();
}
