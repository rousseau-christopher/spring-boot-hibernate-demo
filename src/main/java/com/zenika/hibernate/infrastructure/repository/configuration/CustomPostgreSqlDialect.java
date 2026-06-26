package com.zenika.hibernate.infrastructure.repository.configuration;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.query.sqm.function.SqmFunctionRegistry;

@SuppressWarnings("unused")
public class CustomPostgreSqlDialect extends PostgreSQLDialect {
    public static final String TRIGRAM_SIMILARITY = "trigram_similarity";
    public static final String JSON_PROPERTY_LIKE = "json_property_like";

    public CustomPostgreSqlDialect() {
        super();
    }

    @Override
    public void initializeFunctionRegistry(FunctionContributions functionContributions) {
        super.initializeFunctionRegistry(functionContributions);

        SqmFunctionRegistry functionRegistry = functionContributions.getFunctionRegistry();

        functionRegistry.registerPattern(
                TRIGRAM_SIMILARITY,
                "(?1 <% ?2)"
        );

        functionRegistry.registerPattern(
                JSON_PROPERTY_LIKE,
                "?1->>?2 like ?3"
        );
    }
}
