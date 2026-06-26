package com.zenika.hibernate.application.model;

import org.jspecify.annotations.Nullable;

public record BookSearchRequestDto (
        @Nullable
        String bookName,
        @Nullable
        String authorName,
        @Nullable
        String city,
        @Nullable
        String isbn
){
}
