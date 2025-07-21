package com.zenika.hibernate.application.model;

public record BookWithAuthorDto(
        Long id,
        String label,
        String isbn,
        String summary,
        AuthorDto author
) {}