package com.zenika.hibernate.infrastructure.repository;

import com.zenika.hibernate.infrastructure.repository.model.BookEntity;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.HibernateHints;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    @Query("""
            SELECT book
              FROM BookEntity book
                JOIN FETCH book.author
              WHERE book.id = :bookId
            """
)
    Optional<BookEntity> retrieveABookWithHisAuthor(Long bookId);

    @Modifying
    @Query("UPDATE BookEntity book SET book.note = :value WHERE book.id = :id")
    int updateNote(Long id, Float value);


    @QueryHints(
            @QueryHint(name = HibernateHints.HINT_FETCH_SIZE, value = "2")
    )
    @EntityGraph(attributePaths = "author")
    Stream<BookEntity> findAllByAuthorId(long authorId);

}
