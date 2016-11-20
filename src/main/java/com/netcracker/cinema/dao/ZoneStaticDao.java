package com.netcracker.cinema.dao;

public interface ZoneStaticDao {
    static final String FIND_ALL_SQL =
            "SELECT ZONE.OBJECT_ID id, ZONE.NAME name\n" +
                    "FROM OBJECTS ZONE\n" +
                    "WHERE ZONE.OBJECT_TYPE_ID = 1\n";

    static final String FIND_ZONE_BY_ID =
            "SELECT ZONE.OBJECT_ID id, ZONE.NAME name\n" +
                    "FROM OBJECTS ZONE\n" +
                    "WHERE ZONE.OBJECT_ID = ?\n";

    static final String INSERT_ZONE =
            "INTO OBJECTS(OBJECT_ID, PARENT_ID, OBJECT_TYPE_ID, NAME, DESCRIPTION) VALUES (GET_OBJ_ID.nextval, NULL, 1, ?, NULL)\n";

    static final String UPDATE_ZONE_OBJECTS =
            "UPDATE OBJECTS \n" +
                    "SET NAME = ? \n" +
                    "WHERE OBJECT_ID = ?";

    static final String DELETE_ZONE =
            "DELETE FROM OBJECTS \n" +
                    "WHERE OBJECT_ID = ?";
}

