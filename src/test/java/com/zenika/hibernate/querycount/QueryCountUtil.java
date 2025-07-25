package com.zenika.hibernate.querycount;

import lombok.extern.slf4j.Slf4j;
import net.ttddyy.dsproxy.QueryCount;
import net.ttddyy.dsproxy.QueryCountHolder;

@Slf4j
public class QueryCountUtil {
    private QueryCountUtil() {}

    public static void resetCounter() {
        QueryCountHolder.clear();
    }

    public static QueryCount getQueryCount() {
        return QueryCountHolder.get("MyDS");
    }

    public static void logQueryCount() {
        QueryCount queryCount = getQueryCount();
        log.info("""
                Query stats:
                Nb Insert {}
                Nb Update {}
                Nb Delete {}
                """,
                queryCount.getSelect(),
                queryCount.getUpdate(),
                queryCount.getDelete()
        );
    }
}
