package com.zenika.hibernate.application.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

/**
 * This annotation will auto convert all Page from Spring Data to a PageModel for RestApi
 */
@EnableSpringDataWebSupport(
        pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO
)
@Configuration
public class PageConfiguration {
}
