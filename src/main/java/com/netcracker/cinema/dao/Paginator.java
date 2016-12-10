package com.netcracker.cinema.dao;

import java.util.List;

public interface Paginator<T> {

    List<T> getPage(int page);

    int getPageSize();

    void setPageSize(int pageSize);

    long availablePages();

    public interface PaginatorQuery {
        String SELECT_AVAILABLE_PAGES =
                "SELECT COUNT(*) FROM (%s)";

        String QUERY = "SELECT * FROM (%s) WHERE rn BETWEEN ? AND ? " +
                " ORDER BY rn";
    }
}
