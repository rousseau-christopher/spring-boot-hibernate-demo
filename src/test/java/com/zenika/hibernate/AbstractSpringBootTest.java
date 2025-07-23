package com.zenika.hibernate;

import lombok.extern.slf4j.Slf4j;
import net.ttddyy.dsproxy.QueryCount;
import net.ttddyy.dsproxy.QueryCountHolder;
import org.hibernate.SessionFactory;
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

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ActiveProfiles("it")
@Import(TestcontainersConfiguration.class)
@SpringBootTest
@Sql(scripts = "/data/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@AutoConfigureMockMvc
public abstract class AbstractSpringBootTest {
    protected static final Long TOLKIEN_ID = 1L;
    protected static final Long CLEAN_CODE_ID = 4L;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    protected MockMvcTester mockMvcTester;

    @BeforeEach
    void setUp() {
        sessionFactory.getStatistics().clear();
        QueryCountHolder.clear();
    }

    @AfterEach
    void tearDown() {
        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        log.info("""
                Query stats:
                Nb Insert {}
                Nb Update {}
                Nb Delete {}
                Stats: {}
                """,
                queryCount.getSelect(),
                queryCount.getUpdate(),
                queryCount.getDelete(),
                queryCount
        );
    }

    protected void assertSelectQueryCount(long expected) {
        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        assertThat(queryCount.getSelect()).withFailMessage("Must execute only %s select query. But found %s", expected, queryCount.getSelect()).isEqualTo(expected);
    }

    protected void assertUpdateQueryCount(long expected) {
        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        assertThat(queryCount.getUpdate()).withFailMessage("Must execute only %s update query. But found %s", expected, queryCount.getUpdate()).isEqualTo(expected);
    }

}
