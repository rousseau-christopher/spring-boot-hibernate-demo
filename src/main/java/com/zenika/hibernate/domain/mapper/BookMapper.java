package com.zenika.hibernate.domain.mapper;

import com.zenika.hibernate.application.model.BookDto;
import com.zenika.hibernate.application.model.BookWithAuthorDto;
import com.zenika.hibernate.infrastructure.repository.model.BookEntity;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BookMapper {
    BookDto bookEntityToDto(BookEntity book);

    BookWithAuthorDto bookEntityToBookWithAuthor(BookEntity book);
}
