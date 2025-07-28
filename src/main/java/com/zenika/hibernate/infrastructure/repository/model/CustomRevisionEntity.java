package com.zenika.hibernate.infrastructure.repository.model;

import com.zenika.hibernate.infrastructure.repository.configuration.CustomRevisionEntityListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import java.util.Date;

@Getter
@Setter
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
    private Date timestamp;

    private String username;
}
