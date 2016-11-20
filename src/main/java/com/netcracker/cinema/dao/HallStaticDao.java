package com.netcracker.cinema.dao;

public interface HallStaticDao {
    static final String FIND_ALL_SQL =
            "SELECT HALL.OBJECT_ID id, HALL.NAME name\n" +
                    "FROM OBJECTS HALL\n" +
                    "WHERE HALL.OBJECT_TYPE_ID = 1\n";

    static final String FIND_HALL_BY_ID =
            "SELECT HALL.OBJECT_ID id, HALL.NAME name\n" +
                    "FROM OBJECTS HALL\n" +
                    "WHERE HALL.OBJECT_ID = ?\n";

    static final String INSERT_HALL =
            "INTO OBJECTS(OBJECT_ID, PARENT_ID, OBJECT_TYPE_ID, NAME, DESCRIPTION) VALUES (GET_OBJ_ID.nextval, NULL, 1, ?, NULL)\n";

    static final String UPDATE_HALL_OBJECTS =
            "UPDATE OBJECTS \n" +
                    "SET NAME = ? \n" +
                    "WHERE OBJECT_ID = ?";

    static final String DELETE_HALL =
            "DELETE FROM OBJECTS \n" +
                    "WHERE OBJECT_ID = ?";
}
