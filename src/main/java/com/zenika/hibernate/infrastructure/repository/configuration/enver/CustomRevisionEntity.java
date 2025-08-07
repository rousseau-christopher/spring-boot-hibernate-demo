package com.zenika.hibernate.infrastructure.repository.configuration.enver;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import java.time.Instant;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "revision_info")
@RevisionEntity(CustomRevisionEntityListener.class)
public class CustomRevisionEntity {

    private static final String ID_SEQUENCE = "revision_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_SEQUENCE)
    @SequenceGenerator(name = ID_SEQUENCE, sequenceName = ID_SEQUENCE, allocationSize = 1)
    @RevisionNumber
    private long id;

    @RevisionTimestamp
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false, updatable = false)
    private Instant timestamp;

    private String username;
}
