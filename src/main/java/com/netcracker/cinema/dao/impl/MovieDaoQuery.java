package com.netcracker.cinema.dao.impl;

/**
 * Created by dimka on 18.11.2016.
 */
interface MovieDaoQuery {

    String FIND_ALL_SQL =
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

    String FIND_MOVIE_BY_ID =
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

//    static final String INSERT_MOVIE =
//            "INSERT ALL\n" +
//                    "INTO OBJECTS(OBJECT_ID, PARENT_ID, OBJECT_TYPE_ID, NAME, DESCRIPTION) VALUES (GET_OBJ_ID.nextval, NULL, 1, ?, NULL)\n" +
//                    "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (1, GET_OBJ_ID.currval, ?, NULL) \n" + //DESCRIPTION
//                    "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (2, GET_OBJ_ID.currval, ?, NULL) \n" + //DURATION
//                    "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (3, GET_OBJ_ID.currval, ?, NULL) \n" + //IMDB
//                    "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (4, GET_OBJ_ID.currval, ?, NULL) \n" + //PERIODICITY
//                    "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (5, GET_OBJ_ID.currval, ?, NULL) \n" + //BASE_PRICE
//                    "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (6, GET_OBJ_ID.currval, NULL, ?) \n" + //ROLLING_START
//                    "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (7, GET_OBJ_ID.currval, NULL, ?) \n" + //ROLLING_END
//                    "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (8, GET_OBJ_ID.currval, ?, NULL) \n" + //POSTER
//                    "SELECT * FROM dual";
//
//    static final String UPDATE_MOVIE_OBJECTS =
//            "UPDATE OBJECTS \n" +
//                "SET NAME = ? \n" +
//                    "WHERE OBJECT_ID = ?";
//    static final String UPDATE_MOVIE_ATTRIBUTES_DESCRIPTION =
//            "UPDATE ATTRIBUTES \n" +
//                "SET VALUE = ? \n" +
//                    "WHERE OBJECT_ID = ? AND ATTR_ID = 1";
//    static final String UPDATE_MOVIE_ATTRIBUTES_DURATION =
//            "UPDATE ATTRIBUTES \n" +
//                "SET VALUE = ? \n" +
//                    "WHERE OBJECT_ID = ? AND ATTR_ID = 2";
//    static final String UPDATE_MOVIE_ATTRIBUTES_IMDB =
//            "UPDATE ATTRIBUTES \n" +
//                "SET VALUE = ? \n" +
//                    "WHERE OBJECT_ID = ? AND ATTR_ID = 3";
//    static final String UPDATE_MOVIE_ATTRIBUTES_PERIODICITY =
//            "UPDATE ATTRIBUTES \n" +
//                "SET VALUE = ? \n" +
//                    "WHERE OBJECT_ID = ? AND ATTR_ID = 4";
//    static final String UPDATE_MOVIE_ATTRIBUTES_BASE_PRICE =
//            "UPDATE ATTRIBUTES \n" +
//                "SET VALUE = ? \n" +
//                    "WHERE OBJECT_ID = ? AND ATTR_ID = 5";
//    static final String UPDATE_MOVIE_ATTRIBUTES_ROLLING_START =
//            "UPDATE ATTRIBUTES \n" +
//                "SET DATE_VALUE = ? \n" +
//                    "WHERE OBJECT_ID = ? AND ATTR_ID = 6";
//    static final String UPDATE_MOVIE_ATTRIBUTES_ROLLING_END =
//            "UPDATE ATTRIBUTES \n" +
//                "SET DATE_VALUE = ? \n" +
//                    "WHERE OBJECT_ID = ? AND ATTR_ID = 7";
//    static final String UPDATE_MOVIE_ATTRIBUTES_POSTER =
//            "UPDATE ATTRIBUTES \n" +
//                "SET VALUE = ? \n" +
//                    "WHERE OBJECT_ID = ? AND ATTR_ID = 8";

    String DELETE_MOVIE =
            "DELETE " +
                "FROM OBJECTS \n" +
                    "WHERE OBJECT_ID = ?";

    String MERGE_MOVIE_OBJECT = "MERGE INTO OBJECTS object\n" +
            "USING (SELECT ? AS id, ? AS name FROM dual) obj\n" +
            "  ON(obj.id = object.OBJECT_ID)\n" +
            "WHEN MATCHED THEN\n" +
            "  UPDATE\n" +
            "    SET\n" +
            "      object.NAME = obj.name\n" +
            "    WHERE\n" +
            "      object.OBJECT_ID = obj.id\n" +
            "WHEN NOT MATCHED THEN\n" +
            "  INSERT(object.OBJECT_ID, object.PARENT_ID, object.OBJECT_TYPE_ID, object.NAME, object.DESCRIPTION)\n" +
            "  VALUES (GET_OBJ_ID.nextval, NULL, 1, obj.name, NULL)";

    String MERGE_MOVIE_ATTRIBUTES_DESCRIPTION = "MERGE INTO ATTRIBUTES attr\n" +
            "USING (SELECT obj.OBJECT_ID, ? AS description FROM OBJECTS obj RIGHT JOIN dual ON obj.OBJECT_ID  = ?) object\n" +
            "  ON (object.OBJECT_ID = attr.OBJECT_ID AND attr.ATTR_ID = 1)\n" +
            "WHEN MATCHED THEN\n" +
            "  UPDATE SET\n" +
            "    attr.VALUE = object.description\n" +
            "  WHERE\n" +
            "    attr.ATTR_ID = 1 AND\n" +
            "    attr.OBJECT_ID = object.OBJECT_ID\n" +
            "WHEN NOT MATCHED THEN\n" +
            "  INSERT(attr.ATTR_ID, attr.OBJECT_ID, attr.VALUE, attr.DATE_VALUE)\n" +
            "  VALUES(1, NVL(object.OBJECT_ID, GET_OBJ_ID.currval), object.description, NULL)";

    String MERGE_MOVIE_ATTRIBUTES_DURATION = "MERGE INTO ATTRIBUTES attr\n" +
            "USING (SELECT obj.OBJECT_ID, ? AS duration FROM OBJECTS obj RIGHT JOIN dual ON obj.OBJECT_ID  = ?) object\n" +
            "  ON (object.OBJECT_ID = attr.OBJECT_ID AND attr.ATTR_ID = 2)\n" +
            "WHEN MATCHED THEN\n" +
            "  UPDATE SET\n" +
            "    attr.VALUE = object.duration\n" +
            "  WHERE\n" +
            "    attr.ATTR_ID = 2 AND\n" +
            "    attr.OBJECT_ID = object.OBJECT_ID\n" +
            "WHEN NOT MATCHED THEN\n" +
            "  INSERT(attr.ATTR_ID, attr.OBJECT_ID, attr.VALUE, attr.DATE_VALUE)\n" +
            "  VALUES(2, NVL(object.OBJECT_ID, GET_OBJ_ID.currval), object.duration, NULL)";

    String MERGE_MOVIE_ATTRIBUTES_IMDB = "MERGE INTO ATTRIBUTES attr\n" +
            "USING (SELECT obj.OBJECT_ID, ? AS imdb FROM OBJECTS obj RIGHT JOIN dual ON obj.OBJECT_ID  = ?) object\n" +
            "  ON (object.OBJECT_ID = attr.OBJECT_ID AND attr.ATTR_ID = 3)\n" +
            "WHEN MATCHED THEN\n" +
            "  UPDATE SET\n" +
            "    attr.VALUE = object.imdb\n" +
            "  WHERE\n" +
            "    attr.ATTR_ID = 3 AND\n" +
            "    attr.OBJECT_ID = object.OBJECT_ID\n" +
            "WHEN NOT MATCHED THEN\n" +
            "  INSERT(attr.ATTR_ID, attr.OBJECT_ID, attr.VALUE, attr.DATE_VALUE)\n" +
            "  VALUES(3, NVL(object.OBJECT_ID, GET_OBJ_ID.currval), object.imdb, NULL)";

    String MERGE_MOVIE_ATTRIBUTES_PERIODICITY = "MERGE INTO ATTRIBUTES attr\n" +
            "USING (SELECT obj.OBJECT_ID, ? AS periodicity FROM OBJECTS obj RIGHT JOIN dual ON obj.OBJECT_ID  = ?) object\n" +
            "  ON (object.OBJECT_ID = attr.OBJECT_ID AND attr.ATTR_ID = 4)\n" +
            "WHEN MATCHED THEN\n" +
            "  UPDATE SET\n" +
            "    attr.VALUE = object.periodicity\n" +
            "  WHERE\n" +
            "    attr.ATTR_ID = 4 AND\n" +
            "    attr.OBJECT_ID = object.OBJECT_ID\n" +
            "WHEN NOT MATCHED THEN\n" +
            "  INSERT(attr.ATTR_ID, attr.OBJECT_ID, attr.VALUE, attr.DATE_VALUE)\n" +
            "  VALUES(4, NVL(object.OBJECT_ID, GET_OBJ_ID.currval), object.periodicity, NULL)";

    String MERGE_MOVIE_ATTRIBUTES_BASE_PRICE = "MERGE INTO ATTRIBUTES attr\n" +
            "USING (SELECT obj.OBJECT_ID, ? AS base_price FROM OBJECTS obj RIGHT JOIN dual ON obj.OBJECT_ID  = ?) object\n" +
            "  ON (object.OBJECT_ID = attr.OBJECT_ID AND attr.ATTR_ID = 5)\n" +
            "WHEN MATCHED THEN\n" +
            "  UPDATE SET\n" +
            "    attr.VALUE = object.base_price\n" +
            "  WHERE\n" +
            "    attr.ATTR_ID = 5 AND\n" +
            "    attr.OBJECT_ID = object.OBJECT_ID\n" +
            "WHEN NOT MATCHED THEN\n" +
            "  INSERT(attr.ATTR_ID, attr.OBJECT_ID, attr.VALUE, attr.DATE_VALUE)\n" +
            "  VALUES(5, NVL(object.OBJECT_ID, GET_OBJ_ID.currval), object.base_price, NULL)";

    String MERGE_MOVIE_ATTRIBUTES_ROLLING_START = "MERGE INTO ATTRIBUTES attr\n" +
            "USING (SELECT obj.OBJECT_ID AS obj_id, ? AS rolling_start FROM OBJECTS obj RIGHT JOIN dual ON obj.OBJECT_ID  = ?) object\n" +
            "ON (object.obj_id = attr.OBJECT_ID AND attr.ATTR_ID = 6)\n" +
            "WHEN MATCHED THEN\n" +
            "UPDATE SET\n" +
            "  attr.DATE_VALUE = object.rolling_start\n" +
            "  WHERE\n" +
            "    attr.ATTR_ID = 6 AND\n" +
            "    attr.OBJECT_ID = object.obj_id\n" +
            "WHEN NOT MATCHED THEN\n" +
            "INSERT(attr.ATTR_ID, attr.OBJECT_ID, attr.VALUE, attr.DATE_VALUE)\n" +
            "VALUES(6, NVL(object.obj_id, GET_OBJ_ID.currval), NULL, object.rolling_start)";

    String MERGE_MOVIE_ATTRIBUTES_ROLLING_END = "MERGE INTO ATTRIBUTES attr\n" +
            "USING (SELECT obj.OBJECT_ID, ? AS rolling_end FROM OBJECTS obj RIGHT JOIN dual ON obj.OBJECT_ID  = ?) object\n" +
            "  ON (object.OBJECT_ID = attr.OBJECT_ID AND attr.ATTR_ID = 7)\n" +
            "WHEN MATCHED THEN\n" +
            "  UPDATE SET\n" +
            "    attr.DATE_VALUE = object.rolling_end\n" +
            "  WHERE\n" +
            "    attr.ATTR_ID = 7 AND\n" +
            "    attr.OBJECT_ID = object.OBJECT_ID\n" +
            "WHEN NOT MATCHED THEN\n" +
            "  INSERT(attr.ATTR_ID, attr.OBJECT_ID, attr.VALUE, attr.DATE_VALUE)\n" +
            "  VALUES(7, NVL(object.OBJECT_ID, GET_OBJ_ID.currval), NULL, object.rolling_end)";

    String MERGE_MOVIE_ATTRIBUTES_POSTER = "MERGE INTO ATTRIBUTES attr\n" +
            "USING (SELECT obj.OBJECT_ID, ? AS poster FROM OBJECTS obj RIGHT JOIN dual ON obj.OBJECT_ID  = ?) object\n" +
            "  ON (object.OBJECT_ID = attr.OBJECT_ID AND attr.ATTR_ID = 8)\n" +
            "WHEN MATCHED THEN\n" +
            "  UPDATE SET\n" +
            "    attr.VALUE = object.poster\n" +
            "  WHERE\n" +
            "    attr.ATTR_ID = 8 AND\n" +
            "    attr.OBJECT_ID = object.OBJECT_ID\n" +
            "WHEN NOT MATCHED THEN\n" +
            "  INSERT(attr.ATTR_ID, attr.OBJECT_ID, attr.VALUE, attr.DATE_VALUE)\n" +
            "  VALUES(8, NVL(object.OBJECT_ID, GET_OBJ_ID.currval), object.poster, NULL)";
}
