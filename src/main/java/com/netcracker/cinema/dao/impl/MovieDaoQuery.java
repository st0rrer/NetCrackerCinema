package com.netcracker.cinema.dao.impl;

/**
 * Created by dimka on 18.11.2016.
 */
interface MovieDaoQuery {

    static final String FIND_ALL_SQL =
            "SELECT " +
                    "MOVIE.OBJECT_ID id" +
                    ", MOVIE.NAME title" +
                    ", DESC_ATT.VALUE description" +
                    ", DUR_ATT.VALUE duration" +
                    ", IMDB_ATT.VALUE IMDB" +
                    ", PERIOD_ATT.VALUE periodicity" +
                    ", BASE_ATT.VALUE basePrice" +
                    ", RSTART_ATT.DATE_VALUE startDate" +
                    ", REND_ATT.DATE_VALUE endDate" +
                    ", POSTER_ATT.VALUE poster \n" +
            "FROM " +
                    "OBJECTS MOVIE LEFT JOIN ATTRIBUTES DESC_ATT \n" +
                        "ON MOVIE.OBJECT_ID = DESC_ATT.OBJECT_ID \n" +
                    "LEFT JOIN ATTRIBUTES DUR_ATT \n" +
                        "ON MOVIE.OBJECT_ID = DUR_ATT.OBJECT_ID \n" +
                    "LEFT JOIN ATTRIBUTES IMDB_ATT \n" +
                        "ON MOVIE.OBJECT_ID = IMDB_ATT.OBJECT_ID \n" +
                    "LEFT JOIN ATTRIBUTES PERIOD_ATT \n" +
                        "ON MOVIE.OBJECT_ID = PERIOD_ATT.OBJECT_ID \n" +
                    "LEFT JOIN ATTRIBUTES BASE_ATT \n" +
                        "ON MOVIE.OBJECT_ID = BASE_ATT.OBJECT_ID \n" +
                    "LEFT JOIN ATTRIBUTES RSTART_ATT \n" +
                        "ON MOVIE.OBJECT_ID = RSTART_ATT.OBJECT_ID \n" +
                    "LEFT JOIN ATTRIBUTES REND_ATT \n" +
                        "ON MOVIE.OBJECT_ID = REND_ATT.OBJECT_ID \n" +
                    "LEFT JOIN ATTRIBUTES POSTER_ATT \n" +
                        "ON MOVIE.OBJECT_ID = POSTER_ATT.OBJECT_ID \n" +
            "WHERE " +
                    "MOVIE.OBJECT_TYPE_ID = 1 AND " +
                    "DESC_ATT.ATTR_ID = 1 AND " +
                    "DUR_ATT.ATTR_ID = 2 AND " +
                    "IMDB_ATT.ATTR_ID = 3 AND " +
                    "PERIOD_ATT.ATTR_ID = 4 AND " +
                    "BASE_ATT.ATTR_ID = 5 AND " +
                    "RSTART_ATT.ATTR_ID = 6 AND " +
                    "REND_ATT.ATTR_ID = 7 AND " +
                    "POSTER_ATT.ATTR_ID = 8";

    static final String FIND_MOVIE_BY_ID =
            "SELECT " +
                    "MOVIE.OBJECT_ID id" +
                    ", MOVIE.NAME title" +
                    ", DESC_ATT.VALUE description" +
                    ", DUR_ATT.VALUE duration" +
                    ", IMDB_ATT.VALUE IMDB" +
                    ", PERIOD_ATT.VALUE periodicity" +
                    ", BASE_ATT.VALUE basePrice" +
                    ", RSTART_ATT.DATE_VALUE startDate" +
                    ", REND_ATT.DATE_VALUE endDate" +
                    ", POSTER_ATT.VALUE poster \n" +
            "FROM " +
                    "OBJECTS MOVIE INNER JOIN ATTRIBUTES DESC_ATT \n" +
                        "ON MOVIE.OBJECT_ID = DESC_ATT.OBJECT_ID\n" +
                    "LEFT JOIN ATTRIBUTES DUR_ATT\n" +
                        "ON MOVIE.OBJECT_ID = DUR_ATT.OBJECT_ID\n" +
                    "LEFT JOIN ATTRIBUTES IMDB_ATT\n" +
                        "ON MOVIE.OBJECT_ID = IMDB_ATT.OBJECT_ID\n" +
                    "LEFT JOIN ATTRIBUTES PERIOD_ATT\n" +
                        "ON MOVIE.OBJECT_ID = PERIOD_ATT.OBJECT_ID\n" +
                    "LEFT JOIN ATTRIBUTES BASE_ATT\n" +
                        "ON MOVIE.OBJECT_ID = BASE_ATT.OBJECT_ID\n" +
                    "LEFT JOIN ATTRIBUTES RSTART_ATT\n" +
                        "ON MOVIE.OBJECT_ID = RSTART_ATT.OBJECT_ID\n" +
                    "LEFT JOIN ATTRIBUTES REND_ATT\n" +
                        "ON MOVIE.OBJECT_ID = REND_ATT.OBJECT_ID\n" +
                    "LEFT JOIN ATTRIBUTES POSTER_ATT\n" +
                        "ON MOVIE.OBJECT_ID = POSTER_ATT.OBJECT_ID\n" +
            "WHERE " +
                    "MOVIE.OBJECT_ID = ? AND " +
                    "MOVIE.OBJECT_TYPE_ID = 1 AND " +
                    "DESC_ATT.ATTR_ID = 1 AND " +
                    "DUR_ATT.ATTR_ID = 2 AND " +
                    "IMDB_ATT.ATTR_ID = 3 AND " +
                    "PERIOD_ATT.ATTR_ID = 4 AND " +
                    "BASE_ATT.ATTR_ID = 5 AND " +
                    "RSTART_ATT.ATTR_ID = 6 AND " +
                    "REND_ATT.ATTR_ID = 7 AND " +
                    "POSTER_ATT.ATTR_ID = 8";

    static final String INSERT_MOVIE =
            "INSERT ALL\n" +
                    "INTO OBJECTS(OBJECT_ID, PARENT_ID, OBJECT_TYPE_ID, NAME, DESCRIPTION) VALUES (GET_OBJ_ID.nextval, NULL, 1, ?, NULL)\n" +
                    "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (1, GET_OBJ_ID.currval, ?, NULL) \n" + //DESCRIPTION
                    "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (2, GET_OBJ_ID.currval, ?, NULL) \n" + //DURATION
                    "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (3, GET_OBJ_ID.currval, ?, NULL) \n" + //IMDB
                    "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (4, GET_OBJ_ID.currval, ?, NULL) \n" + //PERIODICITY
                    "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (5, GET_OBJ_ID.currval, ?, NULL) \n" + //BASE_PRICE
                    "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (6, GET_OBJ_ID.currval, NULL, ?) \n" + //ROLLING_START
                    "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (7, GET_OBJ_ID.currval, NULL, ?) \n" + //ROLLING_END
                    "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (8, GET_OBJ_ID.currval, ?, NULL) \n" + //POSTER
                    "SELECT * FROM dual";

    static final String UPDATE_MOVIE_OBJECTS =
            "UPDATE OBJECTS \n" +
                "SET NAME = ? \n" +
                    "WHERE OBJECT_ID = ?";
    static final String UPDATE_MOVIE_ATTRIBUTES_DESCRIPTION =
            "UPDATE ATTRIBUTES \n" +
                "SET VALUE = ? \n" +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1";
    static final String UPDATE_MOVIE_ATTRIBUTES_DURATION =
            "UPDATE ATTRIBUTES \n" +
                "SET VALUE = ? \n" +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 2";
    static final String UPDATE_MOVIE_ATTRIBUTES_IMDB =
            "UPDATE ATTRIBUTES \n" +
                "SET VALUE = ? \n" +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 3";
    static final String UPDATE_MOVIE_ATTRIBUTES_PERIODICITY =
            "UPDATE ATTRIBUTES \n" +
                "SET VALUE = ? \n" +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 4";
    static final String UPDATE_MOVIE_ATTRIBUTES_BASE_PRICE =
            "UPDATE ATTRIBUTES \n" +
                "SET VALUE = ? \n" +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 5";
    static final String UPDATE_MOVIE_ATTRIBUTES_ROLLING_START =
            "UPDATE ATTRIBUTES \n" +
                "SET DATE_VALUE = ? \n" +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 6";
    static final String UPDATE_MOVIE_ATTRIBUTES_ROLLING_END =
            "UPDATE ATTRIBUTES \n" +
                "SET DATE_VALUE = ? \n" +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 7";
    static final String UPDATE_MOVIE_ATTRIBUTES_POSTER =
            "UPDATE ATTRIBUTES \n" +
                "SET VALUE = ? \n" +
                    "WHERE OBJECT_ID = ? AND ATTR_ID = 8";

    static final String DELETE_MOVIE =
            "DELETE " +
                "FROM OBJECTS \n" +
                    "WHERE OBJECT_ID = ?";

}
