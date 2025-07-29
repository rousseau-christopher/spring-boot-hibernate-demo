package com.zenika.hibernate.infrastructure.repository;

import com.zenika.hibernate.AbstractSpringBootTest;
import com.zenika.hibernate.application.model.RevisionDto;
import com.zenika.hibernate.domain.LibraryService;
import com.zenika.hibernate.infrastructure.repository.model.BookEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.history.RevisionMetadata;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

@Slf4j
class BookRepositoryTest extends AbstractSpringBootTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestService testService;

    @Autowired
    private LibraryService libraryService;

    @Test
    void shouldPageAndSort() {
        // GIVEN
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        log.info("findAll, pageable {}", pageable);

        // WHEN
        Page<BookEntity> all = bookRepository.findAll(pageable);

        // THEN
        assertThat(all).isNotNull();
    }

    @Test
    void shouldAuditBook() {
        // CREATION
        long bookId = testService.createBookAndAuthor();
        libraryService.updateNote(bookId, 8.5F);
        libraryService.deleteBook(bookId);

        List<RevisionDto> revisions = libraryService.listBookRevision(bookId);
        log.info("Revisions: {}", revisions);
        assertThat(revisions).hasSize(3);
        RevisionDto revisionDto = revisions.getFirst();
        assertThat(revisionDto.revisionType()).isEqualTo(RevisionMetadata.RevisionType.INSERT);
        assertThat(revisionDto.revisionDate()).isCloseTo(Instant.now(), within(Duration.ofSeconds(1)));

        assertThat(revisionDto.item().auditMetaData().createdDate()).isCloseTo(Instant.now(), within(Duration.ofSeconds(1)));
        assertThat(revisionDto.item().auditMetaData().modifiedDate()).isCloseTo(Instant.now(), within(Duration.ofSeconds(1)));
        assertThat(revisionDto.item().auditMetaData().lastModifiedBy()).isEqualTo("TODO: Current username");
    }
}
