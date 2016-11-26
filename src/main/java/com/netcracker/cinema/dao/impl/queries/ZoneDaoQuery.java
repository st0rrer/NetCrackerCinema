package com.netcracker.cinema.dao.impl.queries;

public interface ZoneDaoQuery {
    String FIND_ALL_SQL =
            "SELECT " +
                    "ZONE.OBJECT_ID id" +
                    ", ZONE.NAME name \n" +
            "FROM " +
                    "OBJECTS ZONE\n" +
            "WHERE " +
                    "ZONE.OBJECT_TYPE_ID = 3\n";

    String FIND_ZONE_BY_ID =
            "SELECT " +
                    "ZONE.OBJECT_ID id" +
                    ", ZONE.NAME name\n" +
            "FROM " +
                    "OBJECTS ZONE\n" +
            "WHERE " +
                    "ZONE.OBJECT_ID = ? " +
                    "AND ZONE.OBJECT_TYPE_ID = 3";

    String DELETE_ZONE =
            "DELETE FROM OBJECTS \n" +
                "WHERE " +
                    "OBJECT_ID = ? \n" +
                    "AND OBJECT_TYPE_ID = 3";

    String MERGE_ZONE_OBJECT = "MERGE INTO OBJECTS object\n" +
            "USING (SELECT ? AS id, ? AS name FROM dual) obj\n" +
            "  ON(obj.id = object.OBJECT_ID)\n" +
            "WHEN MATCHED THEN\n" +
            "  UPDATE\n" +
            "    SET\n"  +
            "      object.NAME = obj.name\n" +
            "    WHERE\n"+
            "      object.OBJECT_ID = obj.id\n" +
            "WHEN NOT MATCHED THEN\n" +
            "  INSERT(object.OBJECT_ID, object.PARENT_ID, object.OBJECT_TYPE_ID, object.NAME, object.DESCRIPTION)\n" +
            "  VALUES (GET_OBJ_ID.nextval, NULL, 3, obj.name, NULL)";
}

