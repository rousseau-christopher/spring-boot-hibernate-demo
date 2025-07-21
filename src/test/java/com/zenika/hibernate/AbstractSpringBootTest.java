package com.zenika.hibernate;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@Slf4j
@ActiveProfiles("it")
@Import(TestcontainersConfiguration.class)
@SpringBootTest
@Sql(scripts = "/data/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@AutoConfigureMockMvc
public abstract class AbstractSpringBootTest {
    protected static final Long TOLKIEN_ID = 1L;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    protected MockMvcTester mockMvcTester;

    @BeforeEach
    void setUp() {
        sessionFactory.getStatistics().clear();
    }

    @AfterEach
    void tearDown() {
        Statistics statistics = sessionFactory.getStatistics();
        log.info("""
                Query stats:
                Nb Query {}
                prepareStatement {}
                Stats: {}
                """,
                statistics.getQueryExecutionCount(),
                statistics.getPrepareStatementCount(),
                statistics
        );
    }

    protected long getQueryCount() {
        return sessionFactory.getStatistics().getPrepareStatementCount();
    }

}
