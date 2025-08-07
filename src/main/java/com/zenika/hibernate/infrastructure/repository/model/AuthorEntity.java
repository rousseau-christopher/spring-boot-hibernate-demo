package com.zenika.hibernate.infrastructure.repository.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.type.SqlTypes;

import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Table(name = "author")
@ToString(exclude = "book")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED) // we don't want the book audited when we update an author
public class AuthorEntity extends AbstractAuditEntity{
        private static final String AUTHOR_SEQUENCE = "author_seq";

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = AUTHOR_SEQUENCE)
        @SequenceGenerator(name = AUTHOR_SEQUENCE, sequenceName = AUTHOR_SEQUENCE, allocationSize = 1)
        private Long id;

        @Version
        private int version;

        private String firstname;
        private String lastname;

        @JdbcTypeCode(SqlTypes.JSON)
        @Column(columnDefinition = "jsonb")
        private AddressJson address;

        @OneToMany(mappedBy = "author", fetch = LAZY, cascade = CascadeType.REMOVE)
        @NotAudited
        private List<BookEntity> book;

        /*
         * id is auto generated AFTER save. It can be null
         * In this case we use it for equal (null are not equal)
         * but we use the hash of the class for hashCode.
         */

        @Override
        public boolean equals(Object o) {
                if (o == null || getClass() != o.getClass()) return false;
                AuthorEntity that = (AuthorEntity) o;
                return id !=null && id.equals(that.id);
        }

        @Override
        public int hashCode() {
                return getClass().hashCode();
        }
}
