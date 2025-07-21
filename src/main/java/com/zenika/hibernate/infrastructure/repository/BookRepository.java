package com.zenika.hibernate.infrastructure.repository;

import com.zenika.hibernate.infrastructure.repository.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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

}
