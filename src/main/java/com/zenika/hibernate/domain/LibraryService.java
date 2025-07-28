package com.zenika.hibernate.domain;

import com.zenika.hibernate.application.model.*;
import com.zenika.hibernate.domain.exception.NotFoundException;
import com.zenika.hibernate.domain.mapper.AuthorMapper;
import com.zenika.hibernate.domain.mapper.BookMapper;
import com.zenika.hibernate.infrastructure.repository.AuthorRepository;
import com.zenika.hibernate.infrastructure.repository.BookEagerRepository;
import com.zenika.hibernate.infrastructure.repository.BookRepository;
import com.zenika.hibernate.infrastructure.repository.model.AuthorEntity;
import com.zenika.hibernate.infrastructure.repository.model.BookEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Slf4j
@Service
@Transactional(readOnly = true) // by default, we will use a read only transaction
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
                .orElseThrow(authorNotFoundException(id));
    }



    public List<AuthorDto> getAuthors() {
        return authorRepository.findAll().stream()
                .map(authorMapper::authorEntityToDto)
                .toList();
    }

    @Transactional
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }


    public BookDto getBook(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::bookEntityToDto)
                .orElseThrow(bookNotFoundException(id));
    }

    public BookWithAuthorDto getBookWithAuthor(Long id) {
        return bookEagerRepository.findById(id)
                .map(bookMapper::bookEntityToBookWithAuthor)
                .orElseThrow(bookNotFoundException(id));
    }

    public BookWithAuthorDto getBookWithAuthorUsingQuery(Long id) {
        return bookRepository.retrieveABookWithHisAuthor(id)
                .map(bookMapper::bookEntityToBookWithAuthor)
                .orElseThrow(bookNotFoundException(id));
    }

    public Page<BookWithAuthorDto> all(Pageable pageable) {
        return bookEagerRepository
                .findAll(pageable)
                .map(bookMapper::bookEntityToBookWithAuthor);
    }



    /*
     * This method creates 2 queries :
     *
     * First query to get the book
     * Second query to update the note
     */
    @Transactional
    public void updateNote(Long id, Float value) {
        BookEntity bookEntity = bookRepository
                .findById(id)
                .orElseThrow(bookNotFoundException(id));

        bookEntity.setNote(value);
        bookRepository.save(bookEntity);
    }

    /*
     This method uses only one query to update the note
     */
    @Transactional
    public void updateNoteUsingQuery(Long id, Float value) {
        bookRepository.updateNote(id, value);
    }

    private final Random random = new Random();
    /**
     * This method will do a batch update of the books
     */
    @Transactional
    public void updateNotesFor(BookIds bookIds) {
        List<BookEntity> books = bookRepository
                .findAllById(bookIds.ids());
        books.forEach(bookEntity -> bookEntity.setNote(random.nextFloat(10)));

        bookRepository.saveAll(books);
    }

    @Transactional
    public void deleteBook(long bookId) {
        bookRepository.deleteById(bookId);
    }

    public Stream<BookWithAuthorDto> getBooks(Long authorId) {
        return bookRepository.findAllByAuthorId(authorId)
                .map(bookMapper::bookEntityToBookWithAuthor);
    }

    @Transactional
    public long addBook(long authorId, NewBookDto bookDto) {
        AuthorEntity authorEntity = authorRepository.findById(authorId)
                .orElseThrow(authorNotFoundException(authorId));

        BookEntity bookEntity = bookMapper.bookDtoToEntity(bookDto);
        bookEntity.setAuthor(authorEntity);
        bookRepository.save(bookEntity);

        return bookEntity.getId();
    }

    private static Supplier<NotFoundException> bookNotFoundException(Long id) {
        return () -> new NotFoundException("Book [" + id + "] not found");
    }

    private static Supplier<NotFoundException> authorNotFoundException(Long id) {
        return () -> new NotFoundException("Author [" + id + "] not found");
    }

    public List<RevisionDto> listBookRevision(Long bookId) {
        return bookRepository.findRevisions(bookId).stream()
                .map(revision ->
                        new RevisionDto(
                                revision.getMetadata().getRequiredRevisionNumber(),
                                revision.getMetadata().getRequiredRevisionInstant(),
                                revision.getMetadata().getRevisionType(),
                                bookMapper.bookEntityToDto(revision.getEntity())
                        )
                )
                .toList();
    }
}
