package com.zenika.hibernate.infrastructure.repository;

import com.zenika.hibernate.infrastructure.repository.model.BookEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookEagerRepository extends JpaRepository<BookEntity, Long> {

    @EntityGraph(attributePaths = "author")
    @NonNull Page<BookEntity> findAll(@NonNull Pageable pageable);
}
