package com.zenika.hibernate.application;

import com.zenika.hibernate.application.model.*;
import com.zenika.hibernate.domain.LibraryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
public class LibraryController {
    private final LibraryService libraryService;

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
    long addBook(@PathVariable long authorId,@RequestBody NewBookDto bookDto) {
        log.info("addBook {}", bookDto);
        return libraryService.addBook(authorId, bookDto);
    }

    /**
     * Impossible to make it like this : the stream is close before being read and convert to json!!!!
     * @param id author id
     */
    @GetMapping("author/{id}/book")
    @Transactional(readOnly = true)
    Stream<BookWithAuthorDto> getBooksForAuthor(@PathVariable Long id) {
        log.info("get All Books using Stream");
        return libraryService.getBooks(id);
    }

    @PutMapping("book/{id}/note")
    void updateBookNote(@PathVariable Long id, @RequestBody NoteDto note) {
        log.info("Update book {} with note {}", id, note);
        libraryService.updateNote(id, note.value());
    }

    @GetMapping("bookWithAuthor/{id}")
    BookWithAuthorDto getBookWithAuthor(@PathVariable Long id) {
        log.info("getBookWithAuthor {}", id);
        return libraryService.getBookWithAuthorUsingQuery(id);
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

    @GetMapping("/book/{bookId}/audit")
    List<RevisionDto> auditBook(@PathVariable Long bookId) {
        log.info("Auditing Book {}", bookId);

        return libraryService.listBookRevision(bookId);
    }
}
