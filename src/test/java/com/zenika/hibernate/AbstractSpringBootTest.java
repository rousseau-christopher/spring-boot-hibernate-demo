package com.zenika.hibernate;

import com.zenika.hibernate.querycount.QueryCountUtil;
import lombok.extern.slf4j.Slf4j;
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
@Sql(scripts = "/data/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@AutoConfigureMockMvc
public abstract class AbstractSpringBootTest {
    protected static final Long TOLKIEN_ID = 1L;
    protected static final Long CLEAN_CODE_ID = 4L;

    @Autowired
    protected MockMvcTester mockMvcTester;

    @BeforeEach
    void setUp() {
        QueryCountUtil.resetCounter();
    }

    @AfterEach
    void tearDown() {
        QueryCountUtil.logQueryCount();
    }

}
