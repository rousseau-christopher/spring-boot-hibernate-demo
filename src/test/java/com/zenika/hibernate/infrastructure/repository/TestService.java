package com.zenika.hibernate.infrastructure.repository;

import com.zenika.hibernate.infrastructure.repository.model.AuthorEntity;
import com.zenika.hibernate.infrastructure.repository.model.BookEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Transactional
    public long createBookAndAuthor() {
        // CREATION
        AuthorEntity author = createAuthor();
        authorRepository.save(author);

        BookEntity book = createBook(author);
        bookRepository.save(book);

        return book.getId();
    }

    @Transactional
    public void updateNoteForBook(long bookId, float note) {
        BookEntity book = bookRepository.findById(bookId).orElseThrow();
        book.setNote(note);
        bookRepository.save(book);
    }

    @Transactional
    public void updateNoteForBookWithQuery(long bookId, float note) {
        bookRepository.updateNote(bookId, note);
    }


    private AuthorEntity createAuthor() {
        AuthorEntity author = new AuthorEntity();
        author.setFirstname("Bob");
        author.setLastname("Dilan");
        return author;
    }

    private BookEntity createBook(AuthorEntity author) {
        BookEntity book = new BookEntity("isbn");
        book.setLabel("label");
        book.setSummary("summary");
        book.setAuthor(author);
        return book;
    }
}
