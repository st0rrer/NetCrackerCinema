package com.netcracker.cinema.dao.impl.queries;

public interface HallDaoQuery {
    String FIND_ALL_SQL =
            "SELECT " +
                    "HALL.OBJECT_ID id" +
                    ", HALL.NAME name \n"+
            "FROM " +
                    "OBJECTS HALL\n" +
            "WHERE "+
                    "HALL.OBJECT_TYPE_ID = 2";

    String FIND_HALL_BY_ID =
            "SELECT " +
                    "HALL.OBJECT_ID id" +
                    ", HALL.NAME name\n" +
            "FROM " +
                    "OBJECTS HALL\n" +
            "WHERE "+
                    "HALL.OBJECT_ID = ?\n" +
                    "AND HALL.OBJECT_TYPE_ID = 2";

    String DELETE_HALL =
            "DELETE FROM OBJECTS \n" +
                    "WHERE \n" +
                    "OBJECT_ID = ? \n" +
                    "AND OBJECT_TYPE_ID = 2";

    String MERGE_HALL_OBJECT = "MERGE INTO OBJECTS object\n" +
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
            "  VALUES (GET_OBJ_ID.nextval, NULL, 2, obj.name, NULL)";
}
