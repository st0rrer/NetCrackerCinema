package com.netcracker.cinema.dao.filter.impl;

import com.netcracker.cinema.dao.filter.Order;
import com.netcracker.cinema.dao.filter.Where;

import java.text.SimpleDateFormat;
import java.util.Date;
import static com.netcracker.cinema.dao.filter.impl.SeanceFilterQuery.*;

public class SeanceFilter extends AbstractFilter {

    @Override
    protected String getQuery() {
        return QUERY;
    }

    @Override
    protected String getDefaultOrderByColumn() {
        return DEFAULT_ORDER_BY_COLUMN;
    }

    public SeanceFilter forId(long id) {
        wheres.add(Where.eq("SEANCE.OBJECT_ID", id));
        return this;
    }

    public SeanceFilter forMovieId(long id) {
        wheres.add(Where.eq("SEANCE_MOVIE_REF.OBJECT_ID", id));
        return this;
    }

    public SeanceFilter forHallId(long id) {
        wheres.add(Where.eq("SEANCE_HALL_REF.OBJECT_ID", id));
        return this;
    }

    public SeanceFilter forMovieName(String movieName) {
        wheres.add(Where.like("MOVIE.NAME", movieName));
        return this;
    }

    public SeanceFilter actual() {
        wheres.add(Where.greater("DATE_ATTR.DATE_VALUE", "SYSDATE"));
        return this;
    }

    public SeanceFilter forDateRange(Date startDate, Date endDate) {
        if(startDate == null || endDate == null) {
            throw new IllegalArgumentException("startDate or endDate can't be null");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY HH:mm:ss");
        wheres.add(Where.between("DATE_ATTR.DATE_VALUE", "TO_DATE('" + dateFormat.format(startDate) + "', 'DD-MM-YYYY HH24:MI:SS')",
                "TO_DATE('" + dateFormat.format(endDate) + "', 'DD-MM-YYYY HH24:MI:SS')"));
        return this;
    }

    public SeanceFilter orderByIdAsc() {
        orders.add(Order.asc("SEANCE.OBJECT_ID"));
        return this;
    }

    public SeanceFilter orderByIdDesc() {
        orders.add(Order.desc("SEANCE.OBJECT_ID"));
        return this;
    }

    public SeanceFilter orderByStartDateAsc() {
        orders.add(Order.asc("DATE_ATTR.DATE_VALUE"));
        return this;
    }

    public SeanceFilter orderByStartDateDesc() {
        orders.add(Order.desc("DATE_ATTR.DATE_VALUE"));
        return this;
    }
}
