package com.netcracker.cinema.dao.impl;

import com.netcracker.cinema.dao.Paginator;
import com.netcracker.cinema.dao.filter.Filter;
import com.netcracker.cinema.exception.CinemaException;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

import static com.netcracker.cinema.dao.Paginator.PaginatorQuery.QUERY;
import static com.netcracker.cinema.dao.Paginator.PaginatorQuery.SELECT_AVAILABLE_PAGES;

abstract class AbstractPaginator<T> implements Paginator<T> {
    private static final Logger LOGGER = Logger.getLogger(AbstractPaginator.class);
    private JdbcTemplate jdbcTemplate;
    private Filter filter;
    private RowMapper<T> rowMapper;
    private int pageSize;

    public AbstractPaginator(Filter filter, RowMapper<T> rowMapper, JdbcTemplate jdbcTemplate, int pageSize) {
        if(pageSize < 0) {
            LOGGER.error("Attempt to create paginator with negative pageSize, pageSize " + pageSize);
            throw new IllegalArgumentException("pageSize must be >= 0, but was " + pageSize);
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
            LOGGER.error("Attempt to get 0 or negative page, page " + page);
            throw new IllegalArgumentException("Page number must be > 0, but was " + page);
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
            LOGGER.error("Attempt to set negative page size, pageSize " + pageSize);
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
        try {
            long count = jdbcTemplate.queryForObject(String.format(SELECT_AVAILABLE_PAGES, filter.buildQuery()), Long.class);
            if (count % pageSize == 0) {
                return count / pageSize;
            } else {
                return count / pageSize + 1;
            }
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaException(e.getMessage(), e);
        }
    }

    private List<T> findSelectedRows(int start, int end) {
        try {
            List<T> query = jdbcTemplate.query(String.format(QUERY, filter.buildQuery()), new Object[]{start, end}, rowMapper);
            LOGGER.info("paginator returned " + query.size() + " rows");
            return query;
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaException(e.getMessage(), e);
        }
    }
}
