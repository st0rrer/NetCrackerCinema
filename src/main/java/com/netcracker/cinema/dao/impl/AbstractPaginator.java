package com.netcracker.cinema.dao.impl;

import com.netcracker.cinema.dao.Paginator;
import com.netcracker.cinema.dao.filter.Filter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import static com.netcracker.cinema.dao.Paginator.PaginatorQuery.*;

import java.util.List;

abstract class AbstractPaginator<T> implements Paginator<T> {
    private JdbcTemplate jdbcTemplate;
    private Filter filter;
    private RowMapper<T> rowMapper;
    private int pageSize;

    public AbstractPaginator(Filter filter, RowMapper<T> rowMapper, JdbcTemplate jdbcTemplate, int pageSize) {
        if(pageSize < 0) {
            throw new IllegalArgumentException("pageSize must be >= 0");
        }

        if(pageSize == 0) {
            this.pageSize = 10;
        } else {
            this.pageSize = pageSize;
        }
        this.filter = filter;
        this.rowMapper = rowMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<T> getPage(int page) {
        if(page <= 0) {
            throw new IllegalArgumentException("Page number must be > 0");
        }
        return findSelectedRows(pageSize * page - pageSize + 1, pageSize * page);
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public void setPageSize(int pageSize) {
        if(pageSize < 0){
            throw new IllegalArgumentException("pageSize must be >= 0");
        }

        if(pageSize == 0) {
            this.pageSize = 10;
        } else {
            this.pageSize = pageSize;
        }
    }

    @Override
    public long availablePages() {
        long count = jdbcTemplate.queryForObject(String.format(SELECT_AVAILABLE_PAGES, filter.buildQuery()), Long.class);
        if (count % pageSize == 0) {
            return count / pageSize;
        } else {
            return count / pageSize + 1;
        }
    }

    protected List<T> findSelectedRows(int start, int end) {
        return jdbcTemplate.query(String.format(QUERY, filter.buildQuery()), new Object[] {start, end}, rowMapper);
    }
}
