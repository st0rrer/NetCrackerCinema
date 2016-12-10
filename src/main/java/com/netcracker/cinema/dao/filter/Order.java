package com.netcracker.cinema.dao.filter;

public class Order {
    private String sql;

    private Order(String sql) {
        this.sql = sql;
    }

    public static Order asc(String column) {
        return new Order(column);
    }

    public static Order desc(String column) {
        return new Order(column + " DESC");
    }

    @Override
    public String toString() {
        return sql;
    }
}
