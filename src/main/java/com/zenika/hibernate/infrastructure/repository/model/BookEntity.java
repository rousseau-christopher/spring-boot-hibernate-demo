package com.zenika.hibernate.infrastructure.repository.model;

import com.zenika.hibernate.domain.mapper.Default;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.envers.Audited;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "book")
@ToString(exclude = "author")
@Audited
public class BookEntity extends AbstractAuditEntity{
    private static final String BOOK_SEQUENCE = "book_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = BOOK_SEQUENCE)
    @SequenceGenerator(name = BOOK_SEQUENCE, sequenceName = BOOK_SEQUENCE, allocationSize = 1)
    private Long id;

    @NaturalId
    @Setter(AccessLevel.NONE)
    @NonNull
    @Column(length = 15, nullable = false, unique = true)
    private String isbn;

    private String label;
    private Float note;
    @Column(columnDefinition = "TEXT")
    private String summary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false, foreignKey = @ForeignKey(name = "author_foreign_key"))
    private AuthorEntity author;

    public BookEntity() {
    }

    @Default
    public BookEntity(@NonNull String isbn) {
        this.isbn = isbn;
    }

    /*
       We have a business key : this key is unique
       We can use it for equal and hash code
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookEntity that = (BookEntity) o;
        return Objects.equals(isbn, that.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(isbn);
    }
}