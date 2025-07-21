package com.zenika.hibernate.application;

import com.zenika.hibernate.application.model.AuthorDto;
import com.zenika.hibernate.application.model.BookDto;
import com.zenika.hibernate.application.model.BookWithAuthorDto;
import com.zenika.hibernate.domain.LibraryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("bookWithAuthor/{id}")
    BookWithAuthorDto getBookWithAuthor(@PathVariable Long id) {
        log.info("getBookWithAuthor {}", id);
        return libraryService.getBookWithAuthorUsingQuery(id);
    }

    @GetMapping("/all")
    Page<BookWithAuthorDto> findAll(@ParameterObject @PageableDefault(page = 0, size = 1) Pageable pageable) {
        log.info("findAll, pageable {}", pageable);
        return libraryService.all(pageable);
    }
}
