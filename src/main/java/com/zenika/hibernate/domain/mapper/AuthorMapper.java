package com.zenika.hibernate.domain.mapper;

import com.zenika.hibernate.application.model.AuthorDto;
import com.zenika.hibernate.infrastructure.repository.model.AuthorEntity;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AuthorMapper {
    AuthorDto authorEntityToDto(AuthorEntity authorEntity);
}
