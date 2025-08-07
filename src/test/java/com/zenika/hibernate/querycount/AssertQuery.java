package com.zenika.hibernate.querycount;

import net.ttddyy.dsproxy.QueryCount;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertQuery {

    private AssertQuery() {}

    public static void assertSelectQueryCount(long expected) {
        QueryCount queryCount = QueryCountUtil.getQueryCount();
        assertThat(queryCount.getSelect()).withFailMessage("Must execute only %s select query. But found %s", expected, queryCount.getSelect()).isEqualTo(expected);
    }

    public static void assertUpdateQueryCount(long expected) {
        QueryCount queryCount = QueryCountUtil.getQueryCount();
        assertThat(queryCount.getUpdate()).withFailMessage("Must execute only %s update query. But found %s", expected, queryCount.getUpdate()).isEqualTo(expected);
    }

    public static void assertInsertQueryCount(long expected) {
        QueryCount queryCount = QueryCountUtil.getQueryCount();
        assertThat(queryCount.getInsert()).withFailMessage("Must execute only %s insert query. But found %s", expected, queryCount.getInsert()).isEqualTo(expected);
    }
}
