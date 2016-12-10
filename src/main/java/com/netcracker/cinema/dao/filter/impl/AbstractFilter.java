package com.netcracker.cinema.dao.filter.impl;

import com.netcracker.cinema.dao.filter.Filter;
import com.netcracker.cinema.dao.filter.Order;
import com.netcracker.cinema.dao.filter.Where;

import java.util.ArrayList;

abstract class AbstractFilter implements Filter {
    protected ArrayList<Where> wheres;
    protected ArrayList<Order> orders;

    public AbstractFilter() {
        wheres = new ArrayList<>(3);
        orders = new ArrayList<>(3);
    }

    abstract protected String getQuery();
    abstract protected String getDefaultOrderByColumn();

    @Override
    public String buildQuery() {
        StringBuilder orderClause = new StringBuilder();

        if(!orders.isEmpty()) {
            for (Order order : orders) {
                orderClause.append(order);
                orderClause.append(",");
            }
            orderClause.deleteCharAt(orderClause.length() - 1);
        } else {
            orderClause.append(getDefaultOrderByColumn());
        }

        StringBuilder whereClause = new StringBuilder();

        for(Where where: wheres) {
            whereClause.append(" AND ");
            whereClause.append(where);
        }

        //mb better use templater
        return String.format(getQuery(), orderClause.toString(), whereClause.toString());
    }
}
