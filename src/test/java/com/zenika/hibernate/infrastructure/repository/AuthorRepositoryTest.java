package com.zenika.hibernate.infrastructure.repository;

import com.zenika.hibernate.AbstractSpringBootTest;
import com.zenika.hibernate.infrastructure.repository.model.AuthorEntity;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
class AuthorRepositoryTest extends AbstractSpringBootTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @Transactional
    void shouldGetAuthors() {
        List<AuthorEntity> authorEntities = authorRepository.findAll();

        log.info("Authors: {}", authorEntities);
    }

}