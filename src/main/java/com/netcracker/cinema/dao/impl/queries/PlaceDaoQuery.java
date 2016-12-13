package com.netcracker.cinema.dao.impl.queries;


public interface PlaceDaoQuery {
    String FIND_ALL_SQL =
            "SELECT " +
                    "PLACE.OBJECT_ID id" +
                    ", NUMBER_ATT.VALUE numm \n" +
                    ", ROW_ATT.VALUE roww \n" +
                    ", HALL_REF.OBJECT_ID hall \n" +
                    ", ZONE_REF.OBJECT_ID zonee \n" +
                    "FROM " +
                    "OBJECTS PLACE" +
                    "LEFT JOIN ATTRIBUTES NUMBER_ATT \n" +
                    "ON PLACE.OBJECT_ID = NUMBER_ATT.OBJECT_ID \n" +
                    "LEFT JOIN ATTRIBUTES ROW_ATT \n" +
                    "ON PLACE.OBJECT_ID = ROW_ATT.OBJECT_ID \n" +
                    "LEFT JOIN OBJREFERENCE HALL_REF \n" +
                    "ON PLACE.OBJECT_ID = HALL_REF.REFERENCE \n" +
                    "LEFT JOIN OBJREFERENCE ZONE_REF \n" +
                    "ON PLACE.OBJECT_ID = ZONE_REF.REFERENCE \n" +
                    "WHERE " +
                    "PLACE.OBJECT_TYPE_ID = 4 AND " +
					"NUMBER_ATT.ATTR_ID = 9 AND " +
                            "ROW_ATT.ATTR_ID = 10 AND " +
                            "HALL_REF.ATTR_ID = 11 AND " +
                            "ZONE_REF.ATTR_ID = 12";

    String FIND_PLACE_BY_ID =
            "SELECT " +
            "       PLACE.OBJECT_ID id" +
            "       , NUMBER_ATT.VALUE numm \n" +
            "       , ROW_ATT.VALUE roww \n" +
            "       , HALL_REF.OBJECT_ID hall \n" +
            "       , ZONE_REF.OBJECT_ID zonee \n" +
            "FROM " +
            "       OBJECTS PLACE \n" +
            "       LEFT JOIN ATTRIBUTES NUMBER_ATT \n" +
            "           ON PLACE.OBJECT_ID = NUMBER_ATT.OBJECT_ID \n" +
            "       LEFT JOIN ATTRIBUTES ROW_ATT \n" +
            "           ON PLACE.OBJECT_ID = ROW_ATT.OBJECT_ID \n" +
            "       LEFT JOIN OBJREFERENCE HALL_REF \n" +
            "           ON PLACE.OBJECT_ID = HALL_REF.REFERENCE \n" +
            "       LEFT JOIN OBJREFERENCE ZONE_REF \n" +
            "           ON PLACE.OBJECT_ID = ZONE_REF.REFERENCE \n" +
            "WHERE " +
            "       PLACE.OBJECT_ID = ? AND " +
            "       PLACE.OBJECT_TYPE_ID = 4 AND " +
            "       NUMBER_ATT.ATTR_ID = 9 AND " +
            "       ROW_ATT.ATTR_ID = 10 AND " +
            "       HALL_REF.ATTR_ID = 11 AND " +
            "       ZONE_REF.ATTR_ID = 12";

    String FIND_PLACE_BY_HALL =
            "SELECT " +
                    "PLACE.OBJECT_ID id" +
                    ", NUMBER_ATT.VALUE numm \n" +
                    ", ROW_ATT.VALUE roww \n" +
                    ", HALL_REF.OBJECT_ID hall \n" +
                    ", ZONE_REF.OBJECT_ID zonee \n" +
                    "FROM " +
                    "OBJECTS PLACE" +
                    "LEFT JOIN ATTRIBUTES NUMBER_ATT \n" +
                    "ON PLACE.OBJECT_ID = NUMBER_ATT.OBJECT_ID \n" +
                    "LEFT JOIN ATTRIBUTES ROW_ATT \n" +
                    "ON PLACE.OBJECT_ID = ROW_ATT.OBJECT_ID \n" +
                    "LEFT JOIN OBJREFERENCE HALL_REF \n" +
                    "ON PLACE.OBJECT_ID = HALL_REF.REFERENCE \n" +
                    "LEFT JOIN OBJREFERENCE ZONE_REF \n" +
                    "ON PLACE.OBJECT_ID = ZONE_REF.REFERENCE \n" +
                    "WHERE " +
                    "PLACE.OBJECT_TYPE_ID = 4 AND " +
                    "NUMBER_ATT.ATTR_ID = 9 AND " +
                    "ROW_ATT.ATTR_ID = 10 AND " +
                    "HALL_REF.ATTR_ID = 11 AND " +
                    "ZONE_REF.ATTR_ID = 12"+
                    "HALL_REF.OBJECT_ID = ?";

    String FIND_PLACE_BY_ZONE =
            "SELECT " +
                    "PLACE.OBJECT_ID id" +
                    ", NUMBER_ATT.VALUE numm \n" +
                    ", ROW_ATT.VALUE roww \n" +
                    ", HALL_REF.OBJECT_ID hall \n" +
                    ", ZONE_REF.OBJECT_ID zonee \n" +
                    "FROM " +
                    "OBJECTS PLACE" +
                    "LEFT JOIN ATTRIBUTES NUMBER_ATT \n" +
                    "ON PLACE.OBJECT_ID = NUMBER_ATT.OBJECT_ID \n" +
                    "LEFT JOIN ATTRIBUTES ROW_ATT \n" +
                    "ON PLACE.OBJECT_ID = ROW_ATT.OBJECT_ID \n" +
                    "LEFT JOIN OBJREFERENCE HALL_REF \n" +
                    "ON PLACE.OBJECT_ID = HALL_REF.REFERENCE \n" +
                    "LEFT JOIN OBJREFERENCE ZONE_REF \n" +
                    "ON PLACE.OBJECT_ID = ZONE_REF.REFERENCE \n" +
                    "WHERE " +
                    "PLACE.OBJECT_TYPE_ID = 4 AND " +
                    "NUMBER_ATT.ATTR_ID = 9 AND " +
                    "ROW_ATT.ATTR_ID = 10 AND " +
                    "HALL_REF.ATTR_ID = 11 AND " +
                    "ZONE_REF.ATTR_ID = 12"+
                    "ZONE_REF.OBJECT_ID = ?";


    String DELETE_PLACE =
            "DELETE FROM OBJECTS \n" +
                    "WHERE " +
                    "OBJECT_ID = ? \n" +
                    "AND OBJECT_TYPE_ID = 4";

    String MERGE_PLACE_OBJECT =
            " MERGE INTO OBJECTS object " +
                    " USING (SELECT ? AS id, 'Place' AS name FROM dual) obj " +
                    " ON(obj.id = object.OBJECT_ID) " +
                    " WHEN MATCHED THEN " +
                    " UPDATE " +
                    " SET " +
                    " object.NAME = obj.name " +
                    " WHERE " +
                    " object.OBJECT_ID = obj.id " +
                    " WHEN NOT MATCHED THEN " +
                    " INSERT(object.OBJECT_ID, object.PARENT_ID, object.OBJECT_TYPE_ID, object.NAME, object.DESCRIPTION) " +
                    " VALUES (GET_OBJ_ID.nextval, NULL, 4, obj.name, NULL)";

    String MERGE_PLACE_ATTRIBUTE_NUMBER =
            " MERGE INTO ATTRIBUTES attr " +
                    " USING (SELECT obj.OBJECT_ID AS obj_id, ? AS attr_number FROM OBJECTS obj RIGHT JOIN dual ON obj.OBJECT_ID  = ?) object " +
                    " ON (object.obj_id = attr.OBJECT_ID AND attr.ATTR_ID = 9) " +
                    " WHEN MATCHED THEN " +
                    " UPDATE " +
                    " SET attr.VALUE = object.attr_number " +
                    " WHERE " +
                    " attr.ATTR_ID = 9 AND " +
                    " attr.OBJECT_ID = object.obj_id " +
                    " WHEN NOT MATCHED THEN " +
                    " INSERT(attr.ATTR_ID, attr.OBJECT_ID, attr.VALUE, attr.DATE_VALUE) " +
                    " VALUES(9, NVL(object.obj_id, GET_OBJ_ID.currval),object.attr_number, NULL)";

    String MERGE_PLACE_ATTRIBUTE_ROW =
            " MERGE INTO ATTRIBUTES attr " +
                    " USING (SELECT obj.OBJECT_ID AS obj_id, ? AS attr_row FROM OBJECTS obj RIGHT JOIN dual ON obj.OBJECT_ID  = ?) object " +
                    " ON (object.obj_id = attr.OBJECT_ID AND attr.ATTR_ID = 10) " +
                    " WHEN MATCHED THEN " +
                    " UPDATE " +
                    " SET attr.VALUE = object.attr_row " +
                    " WHERE " +
                    " attr.ATTR_ID = 10 AND " +
                    " attr.OBJECT_ID = object.obj_id " +
                    " WHEN NOT MATCHED THEN " +
                    " INSERT(attr.ATTR_ID, attr.OBJECT_ID, attr.VALUE, attr.DATE_VALUE) " +
                    " VALUES(10, NVL(object.obj_id, GET_OBJ_ID.currval),object.attr_number, NULL)";

    String MERGE_PLACE_ATTRIBUTE_HALL_REF =
            " MERGE INTO OBJREFERENCE objref " +
                    " USING (SELECT obj.OBJECT_ID AS obj_id, ? AS hall_ref FROM OBJECTS obj RIGHT JOIN dual ON obj.OBJECT_ID  = ?) object " +
                    " ON (object.obj_id = objref.REFERENCE AND objref.ATTR_ID = 11) " +
                    " WHEN MATCHED THEN " +
                    " UPDATE " +
                    " SET objref.OBJECT_ID = object.hall_ref " +
                    " WHERE " +
                    " objref.ATTR_ID = 11 AND " +
                    " objref.REFERENCE = object.obj_id " +
                    " WHEN NOT MATCHED THEN " +
                    " INSERT(objref.ATTR_ID, objref.REFERENCE, objref.OBJECT_ID) " +
                    " VALUES(11, NVL(object.obj_id, GET_OBJ_ID.currval), object.hall_ref)";

    String MERGE_PLACE_ATTRIBUTE_ZONE_REF =
            " MERGE INTO OBJREFERENCE objref " +
                    " USING (SELECT obj.OBJECT_ID AS obj_id, ? AS zone_ref FROM OBJECTS obj RIGHT JOIN dual ON obj.OBJECT_ID  = ?) object " +
                    " ON (object.obj_id = objref.REFERENCE AND objref.ATTR_ID = 12) " +
                    " WHEN MATCHED THEN " +
                    " UPDATE " +
                    " SET objref.OBJECT_ID = object.zone_ref " +
                    " WHERE " +
                    " objref.ATTR_ID = 12 AND " +
                    " objref.REFERENCE = object.obj_id " +
                    " WHEN NOT MATCHED THEN " +
                    " INSERT(objref.ATTR_ID, objref.REFERENCE, objref.OBJECT_ID) " +
                    " VALUES(12, NVL(object.obj_id, GET_OBJ_ID.currval), object.zone_ref)";

    String SELECT_ID =
            " SELECT GET_OBJ_ID.CURRVAL FROM DUAL ";
}
