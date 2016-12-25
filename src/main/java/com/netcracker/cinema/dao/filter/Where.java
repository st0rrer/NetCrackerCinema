package com.netcracker.cinema.dao.filter;

public class Where {
    private String sql;

    private Where(String sql) {
        this.sql = sql;
    }

    public static Where eq(String column, Object value) {
        return new Where(column + " = " + value);
    }

    public static Where notEq(String column, Object value) {
        return new Where(column + " != " + value);
    }

    public static Where greater(String column, Object value) {
        return new Where(column + " > " + value);
    }

    public static Where like(String column, Object value) {
        return new Where(column + " LIKE '%" + value + "%'");
    }

    public static Where greaterOrEq(String column, Object value) {
        return new Where(column + " >= " + value);
    }

    public static Where lesser(String column, Object value) {
        return new Where(column + " < " + value);
    }

    public static Where lesserOrEq(String column, Object value) {
        return new Where(column + " <= " + value);
    }

    public static Where between(String column, Object value1, Object value2) {
        return new Where(column + " BETWEEN " + value1 + " AND " + value2);
    }

    @Override
    public String toString() {
        return sql;
    }
}
