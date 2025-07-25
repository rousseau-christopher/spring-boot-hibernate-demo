package com.zenika.hibernate.application.model;

public record NewBookDto(
        String label,
        String isbn,
        String summary,
        Float note
) {}