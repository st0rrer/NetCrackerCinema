package com.netcracker.cinema.dao.impl;

interface ZoneDaoQuery {
    static final String FIND_ALL_SQL =
            "SELECT " +
                    "ZONE.OBJECT_ID id" +
                    ", ZONE.NAME name \n" +
            "FROM " +
                    "OBJECTS ZONE\n" +
            "WHERE " +
                    "ZONE.OBJECT_TYPE_ID = 3\n";

    static final String FIND_ZONE_BY_ID =
            "SELECT " +
                    "ZONE.OBJECT_ID id" +
                    ", ZONE.NAME name\n" +
            "FROM " +
                    "OBJECTS ZONE\n" +
            "WHERE " +
                    "ZONE.OBJECT_ID = ? " +
                    "AND ZONE.OBJECT_TYPE_ID = 3";

    static final String INSERT_ZONE =
            "INSERT INTO OBJECTS(OBJECT_ID, PARENT_ID, OBJECT_TYPE_ID, NAME, DESCRIPTION) " +
            "VALUES (GET_OBJ_ID.nextval, NULL, 3, ?, NULL)\n";

    static final String UPDATE_ZONE_OBJECTS =
            "UPDATE OBJECTS \n" +
                "SET NAME = ? \n" +
                    "WHERE " +
                        "OBJECT_ID = ? " +
                        "AND OBJECT_TYPE_ID = 3";

    static final String DELETE_ZONE =
            "DELETE FROM OBJECTS \n" +
                "WHERE " +
                    "OBJECT_ID = ? \n" +
                    "AND OBJECT_TYPE_ID = 3";
}

