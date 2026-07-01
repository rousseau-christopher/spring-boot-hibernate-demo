package com.zenika.hibernate.infrastructure.repository.specification;

import com.zenika.hibernate.infrastructure.repository.model.AuthorEntity;
import com.zenika.hibernate.infrastructure.repository.model.BookEntity;
import jakarta.persistence.criteria.Join;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import static com.zenika.hibernate.infrastructure.repository.configuration.CustomPostgreSqlDialect.JSON_PROPERTY_LIKE;
import static com.zenika.hibernate.infrastructure.repository.configuration.CustomPostgreSqlDialect.TRIGRAM_SIMILARITY;

@UtilityClass
public class BookSpecifications {

    public static Specification<BookEntity> isbnSearch(@Nullable String isbn) {
        return (book, query, criteriaBuild) -> {
            if (StringUtils.isBlank(isbn)) {
                return null;
            }

            return criteriaBuild.equal(book.get("isbn"), isbn);
        };
    }

    public static Specification<BookEntity> labelSearch(@Nullable String label) {
        return (book, query, criteriaBuild) -> {
            if (StringUtils.isBlank(label)) {
                return null;
            }
            return criteriaBuild.isTrue(
                    criteriaBuild.function(
                            TRIGRAM_SIMILARITY,
                            Boolean.class,
                            criteriaBuild.literal(label),
                            book.get("label")

                    )
            );
        };
    }

    public static Specification<BookEntity> authorSearch(@Nullable String authorName) {
        return (book, query, criteriaBuild) -> {
            if (StringUtils.isBlank(authorName)) {
                return null;
            }

            Join<BookEntity, AuthorEntity> author = book.join("author");

            return criteriaBuild.isTrue(
                    criteriaBuild.function(
                            TRIGRAM_SIMILARITY,
                            Boolean.class,
                            criteriaBuild.literal(authorName),
                            author.get("lastname")

                    )
            );
        };
    }

    public static Specification<BookEntity> authorCitySearch(@Nullable String city) {
        return (book, query, criteriaBuild) -> {
            if (StringUtils.isBlank(city)) {
                return null;
            }

            Join<BookEntity, AuthorEntity> author = book.join("author");

            return criteriaBuild.isTrue(
                    criteriaBuild.function(
                            JSON_PROPERTY_LIKE,
                            Boolean.class,
                            author.get("address"),
                            criteriaBuild.literal("city"),
                            criteriaBuild.literal(city + "%")

                    )
            );
        };
    }

}
