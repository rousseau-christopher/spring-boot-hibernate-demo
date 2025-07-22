package com.zenika.hibernate.application.model;

public record BookDto(
        Long id,
        String label,
        String isbn,
        String summary,
        Float note
) {}