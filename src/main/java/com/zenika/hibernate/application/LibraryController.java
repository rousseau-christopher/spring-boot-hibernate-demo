package com.zenika.hibernate.application;

import com.zenika.hibernate.application.model.*;
import com.zenika.hibernate.domain.LibraryService;
import com.zenika.hibernate.infrastructure.repository.BookRepository;
import com.zenika.hibernate.infrastructure.repository.model.BookEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
public class LibraryController {
    private final LibraryService libraryService;
    private final BookRepository bookRepository;

    @PostMapping("author")
    void addAuthor(@RequestBody NewAuthorDto authorDto) {
        log.info("Adding Author: {}", authorDto);
        libraryService.addAuthor(authorDto);
    }

    @GetMapping("author")
    List<AuthorDto> getAuthors() {
        log.info("getAuthors");
        return libraryService.getAuthors();
    }

    @GetMapping("author/{id}")
    AuthorDto getAuthor(@PathVariable Long id) {
        log.info("getAuthor {}", id);
        return libraryService.getAuthor(id);
    }

    @DeleteMapping("author/{id}")
    void deleteAuthor(@PathVariable Long id) {
        log.info("deleteAuthor {}", id);
        libraryService.deleteAuthor(id);
    }

    @GetMapping("book/{id}")
    BookDto getBook(@PathVariable Long id) {
        log.info("getBook {}", id);
        return libraryService.getBook(id);
    }

    @PostMapping("author/{authorId}/book")
    long addBook(@PathVariable long authorId, @RequestBody NewBookDto bookDto) {
        log.info("addBook {}", bookDto);
        return libraryService.addBook(authorId, bookDto);
    }

    @PutMapping("book/{id}/note")
    void updateBookNote(@PathVariable Long id, @RequestBody NoteDto note) {
        log.info("Update item {} with note {}", id, note);
        libraryService.updateNote(id, note.value());
    }

    @GetMapping("bookWithAuthor/{id}")
    BookWithAuthorDto getBookWithAuthor(@PathVariable Long id) {
        log.info("getBookWithAuthor {}", id);
        return libraryService.getBookWithAuthor(id);
    }

    @GetMapping("/all")
    Page<BookWithAuthorDto> findAll(@ParameterObject @PageableDefault(size = 1) Pageable pageable) {
        log.info("findAll, pageable {}", pageable);
        return libraryService.all(pageable);
    }

    @PutMapping("/randomNote")
    void setRandomNote(@RequestBody BookIds bookIds) {
        log.info("Set Random Not For: {}", bookIds);
        libraryService.updateNotesFor(bookIds);
    }

    @GetMapping("/search")
    Page<BookWithAuthorDto> searchBooks(
            @ParameterObject
            @SortDefault(sort = "label", direction = Sort.Direction.ASC)
            Pageable pageable,
            @ParameterObject BookSearchRequestDto bookSearchRequest) {
        log.info("searchBooks {}, pageable {}", bookSearchRequest, pageable);
        return libraryService.searchWithSpecification(bookSearchRequest, pageable);
    }

    @GetMapping("/book/{bookId}/audit")
    List<RevisionDto> auditBook(@PathVariable Long bookId) {
        log.info("Auditing Book {}", bookId);

        return libraryService.listBookRevision(bookId);
    }

    @GetMapping("/author/{authorId}/audit")
    List<RevisionDto> auditAuthor(@PathVariable Long authorId) {
        log.info("Auditing Author {}", authorId);

        return libraryService.listAuthorRevision(authorId);
    }

    @GetMapping("/book/{bookId}/bad")
    BookEntity getBookReallyBad(@PathVariable Long bookId) {
        log.info("getBookReallyBad {}", bookId);
        return bookRepository.findById(bookId)
                .orElseThrow(IllegalArgumentException::new);
    }
}
