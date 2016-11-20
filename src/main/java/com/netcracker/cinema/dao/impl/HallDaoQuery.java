package com.netcracker.cinema.dao.impl;

public interface HallDaoQuery {
    static final String FIND_ALL_SQL =
            "SELECT " +
                    "HALL.OBJECT_ID id" +
                    ", HALL.NAME name \n" +
            "FROM " +
                    "OBJECTS HALL\n" +
            "WHERE " +
                    "HALL.OBJECT_TYPE_ID = 2";

    static final String FIND_HALL_BY_ID =
            "SELECT " +
                    "HALL.OBJECT_ID id" +
                    ", HALL.NAME name\n" +
            "FROM " +
                    "OBJECTS HALL\n" +
            "WHERE " +
                    "HALL.OBJECT_ID = ?\n" +
                    "AND HALL.OBJECT_TYPE_ID = 2";

    static final String INSERT_HALL =
            "INSERT INTO OBJECTS(OBJECT_ID, PARENT_ID, OBJECT_TYPE_ID, NAME, DESCRIPTION) \n" +
            "VALUES (GET_OBJ_ID.nextval, NULL, 2, ?, NULL)";

    static final String UPDATE_HALL_OBJECTS =
            "UPDATE OBJECTS \n" +
                "SET NAME = ? \n" +
                    "WHERE \n" +
                        "OBJECT_ID = ? \n" +
                        "AND OBJECT_TYPE_ID = 2";

    static final String DELETE_HALL =
            "DELETE FROM OBJECTS \n" +
                "WHERE \n" +
                    "OBJECT_ID = ? \n" +
                    "AND OBJECT_TYPE_ID = 2";
}
