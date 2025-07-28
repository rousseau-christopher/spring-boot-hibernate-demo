package com.zenika.hibernate.infrastructure.repository.configuration;

import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Can be implemented to get the current connected user using Spring Security
 */
@Service
public class CustomAuditorAware implements AuditorAware<String> {


    @Override
    public @NonNull Optional<String> getCurrentAuditor() {
        return Optional.of("TODO: Current username");
    }
}
