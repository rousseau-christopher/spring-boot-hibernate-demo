package com.zenika.hibernate.infrastructure.repository;

import com.zenika.hibernate.infrastructure.repository.model.BookEntity;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.HibernateHints;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface BookRepository extends
        JpaRepository<BookEntity, Long>,
        JpaSpecificationExecutor<BookEntity>,
        RevisionRepository<BookEntity, Long, Long> {

    @NonNull Page<BookEntity> findAll(@NonNull Pageable pageable);

    /**
     * we can add arbitrary text between find and By to describe the query
     */
    @EntityGraph(attributePaths = "author")
    @NonNull
    Optional<BookEntity> findWithAuthorById(@NonNull Long id);

    @Query("""
            SELECT book
              FROM BookEntity book
                JOIN FETCH book.author
              WHERE book.id = :bookId
            """
    )
    Optional<BookEntity> retrieveABookWithHisAuthor(Long bookId);

    /*
        This way of updating will bypass the audit mechanism !!! and the optimistic locking version !!!!
     */
    @Modifying
    @Query("UPDATE BookEntity book SET book.note = :value WHERE book.id = :id")
    int updateNote(Long id, Float value);


    @QueryHints(
            @QueryHint(name = HibernateHints.HINT_FETCH_SIZE, value = "2")
    )
    @EntityGraph(attributePaths = "author")
    Stream<BookEntity> findAllByAuthorId(long authorId);

    /*
        Pessimistic lock
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<BookEntity> findLockedById(Long id);

    @NonNull
    @EntityGraph(attributePaths = "author")
    Page<BookEntity> findAll(@Nullable Specification<BookEntity> spec, @NonNull Pageable pageable);

    @NonNull
    @Query("""
            SELECT book
              FROM BookEntity book
                JOIN FETCH book.author
              WHERE book.isbn Like :isbn
              AND book.label like :label
            """
    )
    Page<BookEntity> searchBook(String isbn, String label,  @NonNull Pageable pageable);
}
