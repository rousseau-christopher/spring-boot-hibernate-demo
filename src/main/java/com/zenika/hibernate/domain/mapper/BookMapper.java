package com.zenika.hibernate.domain.mapper;

import com.zenika.hibernate.application.model.BookDto;
import com.zenika.hibernate.application.model.BookWithAuthorDto;
import com.zenika.hibernate.application.model.NewBookDto;
import com.zenika.hibernate.infrastructure.repository.model.BookEntity;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BookMapper {
    BookDto bookEntityToDto(BookEntity book);
    List<BookDto> booksToDto(List<BookEntity> books);

    BookEntity bookDtoToEntity(NewBookDto bookDto);

    BookWithAuthorDto bookEntityToBookWithAuthor(BookEntity book);
}
