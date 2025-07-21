package com.zenika.hibernate.infrastructure.repository;

import com.zenika.hibernate.AbstractSpringBootTest;
import com.zenika.hibernate.infrastructure.repository.model.BookEntity;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Slf4j
class BookRepositoryTest extends AbstractSpringBootTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void shouldPageAndSort() {
        // GIVEN
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        log.info("findAll, pageable {}", pageable);

        // WHEN
        Page<BookEntity> all = bookRepository.findAll(pageable);

        // THEN
        Assertions.assertThat(all).isNotNull();
    }
}
