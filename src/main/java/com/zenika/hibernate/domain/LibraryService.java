package com.zenika.hibernate.domain;

import com.zenika.hibernate.application.model.AuthorDto;
import com.zenika.hibernate.application.model.BookDto;
import com.zenika.hibernate.application.model.BookIds;
import com.zenika.hibernate.application.model.BookWithAuthorDto;
import com.zenika.hibernate.domain.exception.NotFoundException;
import com.zenika.hibernate.domain.mapper.AuthorMapper;
import com.zenika.hibernate.domain.mapper.BookMapper;
import com.zenika.hibernate.infrastructure.repository.AuthorRepository;
import com.zenika.hibernate.infrastructure.repository.BookEagerRepository;
import com.zenika.hibernate.infrastructure.repository.BookRepository;
import com.zenika.hibernate.infrastructure.repository.model.BookEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@Transactional(readOnly = true) // by default we will use a read only transaction
@RequiredArgsConstructor
public class LibraryService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookEagerRepository bookEagerRepository;

    public AuthorDto getAuthor(Long id) {
        return authorRepository.findById(id)
                .map(authorMapper::authorEntityToDto)
                .orElseThrow(() -> new NotFoundException("Author [" + id + "] not found"));
    }

    public List<AuthorDto> getAuthors() {
        return authorRepository.findAll().stream()
                .map(authorMapper::authorEntityToDto)
                .toList();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }


    public BookDto getBook(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::bookEntityToDto)
                .orElseThrow(() -> createBookNotFoundException(id));
    }

    public BookWithAuthorDto getBookWithAuthor(Long id) {
        return bookEagerRepository.findById(id)
                .map(bookMapper::bookEntityToBookWithAuthor)
                .orElseThrow(() -> createBookNotFoundException(id));
    }

    public BookWithAuthorDto getBookWithAuthorUsingQuery(Long id) {
        return bookRepository.retrieveABookWithHisAuthor(id)
                .map(bookMapper::bookEntityToBookWithAuthor)
                .orElseThrow(() -> createBookNotFoundException(id));
    }

    public Page<BookWithAuthorDto> all(Pageable pageable) {
        return bookEagerRepository
                .findAll(pageable)
                .map(bookMapper::bookEntityToBookWithAuthor);
    }

    private static NotFoundException createBookNotFoundException(Long id) {
        return new NotFoundException("Book [" + id + "] not found");
    }

    /*
     * This method creates 2 queries :
     *
     * First query to get the book
     * Second query to update the note
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateNote(Long id, Float value) {
        BookEntity bookEntity = bookRepository
                .findById(id)
                .orElseThrow(() -> createBookNotFoundException(id));

        bookEntity.setNote(value);
        bookRepository.save(bookEntity);
    }

    /*
     This method uses only one query to update the note
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateNoteUsingQuery(Long id, Float value) {
        bookRepository.updateNote(id, value);
    }

    private final Random random = new Random();
    /**
     * This method will do a batch update of the books
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateNorFor(BookIds bookIds) {
        List<BookEntity> books = bookRepository
                .findAllById(bookIds.ids());
        books.forEach(bookEntity -> bookEntity.setNote(random.nextFloat(10)));


        bookRepository.saveAll(books);
    }
}
