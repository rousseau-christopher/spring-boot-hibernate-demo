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
        if (queryCount != null) {
            log.info("""
                Query stats:
                Nb Select {}
                Nb Insert {}
                Nb Update {}
                Nb Delete {}
                """,
                    queryCount.getSelect(),
                    queryCount.getInsert(),
                    queryCount.getUpdate(),
                    queryCount.getDelete()
            );
        } else {
            log.info("No query was executed");
        }
    }
}
