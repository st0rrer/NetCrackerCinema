package com.netcracker.cinema.dao.impl.queries;

/**
 * Created by Parpalak on 10.12.2016.
 */
public interface PriceDaoQuery {

    String FIND_ALL_PRICES =
            "SELECT \n" +
                    "  OBJ_PRICE.OBJECT_ID ID,\n" +
                    "  ATTR_PRICE.VALUE PRICE,\n" +
                    "  REF_SEANCE.OBJECT_ID SEANCE_ID,\n" +
                    "  REF_ZONE.OBJECT_ID ZONE_ID\n" +
                    "FROM OBJTYPE OBJ_TYPE \n" +
                    "  JOIN OBJECTS OBJ_PRICE\n" +
                    "    ON OBJ_TYPE.OBJECT_TYPE_ID = OBJ_PRICE.OBJECT_TYPE_ID\n" +
                    "  JOIN ATTRIBUTES ATTR_PRICE\n" +
                    "    ON OBJ_PRICE.OBJECT_ID = ATTR_PRICE.OBJECT_ID\n" +
                    "  JOIN ATTRTYPE ATYPE_PRICE\n" +
                    "    ON ATTR_PRICE.ATTR_ID = ATYPE_PRICE.ATTR_ID\n" +
                    "  JOIN OBJREFERENCE REF_SEANCE\n" +
                    "    ON OBJ_PRICE.OBJECT_ID = REF_SEANCE.REFERENCE\n" +
                    "  JOIN ATTRTYPE ATYPE_SEANCE\n" +
                    "    ON REF_SEANCE.ATTR_ID = ATYPE_SEANCE.ATTR_ID\n" +
                    "  JOIN OBJREFERENCE REF_ZONE\n" +
                    "    ON OBJ_PRICE.OBJECT_ID = REF_ZONE.REFERENCE\n" +
                    "  JOIN ATTRTYPE ATYPE_ZONE\n" +
                    "    ON REF_ZONE.ATTR_ID = ATYPE_ZONE.ATTR_ID\n" +
                    "WHERE\n" +
                    "  OBJ_TYPE.CODE = 'PRICE'\n" +
                    "  AND ATYPE_PRICE.CODE = 'PRICE'\n" +
                    "  AND ATYPE_SEANCE.CODE = 'SEANCE_REF'\n" +
                    "  AND ATYPE_ZONE.CODE = 'ZONE_REF'\n" +
                    "ORDER BY ID";

    String FIND_PRICE_BY_ID =
            "SELECT \n" +
                    "  OBJ_PRICE.OBJECT_ID ID,\n" +
                    "  ATTR_PRICE.VALUE PRICE,\n" +
                    "  REF_SEANCE.OBJECT_ID SEANCE_ID,\n" +
                    "  REF_ZONE.OBJECT_ID ZONE_ID\n" +
                    "FROM OBJTYPE OBJ_TYPE \n" +
                    "  JOIN OBJECTS OBJ_PRICE\n" +
                    "    ON OBJ_TYPE.OBJECT_TYPE_ID = OBJ_PRICE.OBJECT_TYPE_ID\n" +
                    "  JOIN ATTRIBUTES ATTR_PRICE\n" +
                    "    ON OBJ_PRICE.OBJECT_ID = ATTR_PRICE.OBJECT_ID\n" +
                    "  JOIN ATTRTYPE ATYPE_PRICE\n" +
                    "    ON ATTR_PRICE.ATTR_ID = ATYPE_PRICE.ATTR_ID\n" +
                    "  JOIN OBJREFERENCE REF_SEANCE\n" +
                    "    ON OBJ_PRICE.OBJECT_ID = REF_SEANCE.REFERENCE\n" +
                    "  JOIN ATTRTYPE ATYPE_SEANCE\n" +
                    "    ON REF_SEANCE.ATTR_ID = ATYPE_SEANCE.ATTR_ID\n" +
                    "  JOIN OBJREFERENCE REF_ZONE\n" +
                    "    ON OBJ_PRICE.OBJECT_ID = REF_ZONE.REFERENCE\n" +
                    "  JOIN ATTRTYPE ATYPE_ZONE\n" +
                    "    ON REF_ZONE.ATTR_ID = ATYPE_ZONE.ATTR_ID\n" +
                    "WHERE\n" +
                    "  OBJ_TYPE.CODE = 'PRICE'\n" +
                    "  AND ATYPE_PRICE.CODE = 'PRICE'\n" +
                    "  AND ATYPE_SEANCE.CODE = 'SEANCE_REF'\n" +
                    "  AND ATYPE_ZONE.CODE = 'ZONE_REF'\n" +
                    "  AND OBJ_PRICE.OBJECT_ID = ?\n" +
                    "ORDER BY ID";

    String DELETE_PRICE =
            "DELETE " +
                    " FROM OBJECTS " +
                    " WHERE " +
                    " OBJECT_ID = ? AND " +
                    " OBJECT_TYPE_ID = 7";

    String MERGE_PRICE_OBJECT =
            "MERGE INTO OBJECTS object \n" +
                    "  USING (SELECT ? AS id, 'Price' AS name FROM dual) obj \n" +
                    "      ON(obj.id = object.OBJECT_ID)\n" +
                    "  WHEN MATCHED THEN\n" +
                    "      UPDATE\n" +
                    "          SET\n" +
                    "          object.NAME = obj.name\n" +
                    "      WHERE\n" +
                    "          object.OBJECT_ID = obj.id\n" +
                    "  WHEN NOT MATCHED THEN\n" +
                    "      INSERT(object.OBJECT_ID, object.PARENT_ID, object.OBJECT_TYPE_ID, object.NAME, object.DESCRIPTION)\n" +
                    "      VALUES (GET_OBJ_ID.nextval, NULL, 7, obj.name, NULL)";

    String MERGE_PRICE_ATTRIBUTE =
            "MERGE INTO ATTRIBUTES attr\n" +
                    "  USING (SELECT obj.OBJECT_ID AS obj_id, ? AS price FROM OBJECTS obj RIGHT JOIN dual ON obj.OBJECT_ID  = ?) object\n" +
                    "      ON(object.obj_id = attr.OBJECT_ID AND attr.ATTR_ID = 22)\n" +
                    "  WHEN MATCHED THEN\n" +
                    "      UPDATE\n" +
                    "          SET\n" +
                    "          attr.value = object.price\n" +
                    "      WHERE\n" +
                    "          attr.attr_id = 22\n" +
                    "          and attr.OBJECT_ID = object.obj_id\n" +
                    "  WHEN NOT MATCHED THEN\n" +
                    "      INSERT(attr.ATTR_ID, attr.OBJECT_ID, attr.VALUE, attr.DATE_VALUE)\n" +
                    "      VALUES(22, NVL(object.obj_id, GET_OBJ_ID.currval), object.price, NULL)";

    String MERGE_PRICE_ATTRIBUTE_ZONE =
            "MERGE INTO OBJREFERENCE objref\n" +
                    "  USING (SELECT obj.OBJECT_ID AS obj_id, ? AS zone_ref FROM OBJECTS obj RIGHT JOIN dual ON obj.OBJECT_ID  = ?) object\n" +
                    "      ON(object.obj_id = objref.reference AND objref.ATTR_ID = 23)\n" +
                    "  WHEN MATCHED THEN\n" +
                    "      UPDATE\n" +
                    "          SET\n" +
                    "          objref.object_id = object.zone_ref\n" +
                    "      WHERE\n" +
                    "          objref.attr_id = 23\n" +
                    "          and objref.reference = object.obj_id\n" +
                    "  WHEN NOT MATCHED THEN\n" +
                    "      INSERT(objref.ATTR_ID, objref.REFERENCE, objref.OBJECT_ID)\n" +
                    "      VALUES(23, NVL(object.obj_id, GET_OBJ_ID.currval), object.zone_ref)";

    String MERGE_PRICE_ATTRIBUTE_SEANCE =
            "MERGE INTO OBJREFERENCE objref\n" +
                    "  USING (SELECT obj.OBJECT_ID AS obj_id, ? AS seance_ref FROM OBJECTS obj RIGHT JOIN dual ON obj.OBJECT_ID  = ?) object\n" +
                    "      ON(object.obj_id = objref.reference AND objref.ATTR_ID = 24)\n" +
                    "  WHEN MATCHED THEN\n" +
                    "      UPDATE\n" +
                    "          SET\n" +
                    "          objref.object_id = object.seance_ref\n" +
                    "      WHERE\n" +
                    "          objref.attr_id = 24\n" +
                    "          and objref.reference = object.obj_id\n" +
                    "  WHEN NOT MATCHED THEN\n" +
                    "      INSERT(objref.ATTR_ID, objref.REFERENCE, objref.OBJECT_ID)\n" +
                    "      VALUES(24, NVL(object.obj_id, GET_OBJ_ID.currval), object.seance_ref)";

    String SELECT_ID_FOR_INSERTED_PRICE =
            " SELECT GET_OBJ_ID.CURRVAL FROM DUAL ";

    String SELECT_PRICE_SEANCE_COL_ROW =
            "SELECT ATTR_PRICE.VALUE PRICE\n" +
                    "FROM OBJTYPE OBJ_TYPE_PRICE, OBJTYPE OBJ_TYPE_PLACE, OBJTYPE OBJ_TYPE_ZONE,\n" +
                    "     OBJECTS OBJ_PRICE, OBJECTS OBJ_PLACE, OBJECTS OBJ_ZONE,\n" +
                    "     ATTRIBUTES ATTR_PRICE,  ATTRIBUTES ATTR_PLACE_COL, ATTRIBUTES ATTR_PLACE_ROW,\n" +
                    "     OBJREFERENCE REF_ZONE, OBJREFERENCE REF_SEANCE, OBJREFERENCE REF_ZONE_PLACE,\n" +
                    "     ATTRTYPE AT_PRICE, ATTRTYPE AT_ZONE, ATTRTYPE AT_SEANCE, ATTRTYPE AT_ZONE_PLACE, ATTRTYPE AT_PLACE_COL, ATTRTYPE AT_PLACE_ROW\n" +
                    "WHERE\n" +
                    "  OBJ_TYPE_PRICE.CODE = 'PRICE'\n" +
                    "  AND OBJ_TYPE_PRICE.OBJECT_TYPE_ID = OBJ_PRICE.OBJECT_TYPE_ID\n" +
                    "  AND OBJ_PRICE.OBJECT_ID = ATTR_PRICE.OBJECT_ID\n" +
                    "  AND ATTR_PRICE.ATTR_ID = AT_PRICE.ATTR_ID\n" +
                    "  AND AT_PRICE.CODE = 'PRICE'\n" +
                    "  AND OBJ_PRICE.OBJECT_ID = REF_ZONE.REFERENCE\n" +
                    "  AND REF_ZONE.ATTR_ID = AT_ZONE.ATTR_ID\n" +
                    "  AND AT_ZONE.CODE = 'ZONE_REF'\n" +
                    "  AND OBJ_PRICE.OBJECT_ID = REF_SEANCE.REFERENCE\n" +
                    "  AND REF_SEANCE.ATTR_ID = AT_SEANCE.ATTR_ID\n" +
                    "  AND AT_SEANCE.CODE = 'SEANCE_REF'\n" +
                    "  \n" +
                    "  AND OBJ_TYPE_ZONE.CODE = 'ZONE'\n" +
                    "  AND OBJ_TYPE_ZONE.OBJECT_TYPE_ID = OBJ_ZONE.OBJECT_TYPE_ID\n" +
                    "  \n" +
                    "  AND OBJ_TYPE_PLACE.CODE = 'PLACE'\n" +
                    "  AND OBJ_TYPE_PLACE.OBJECT_TYPE_ID = OBJ_PLACE.OBJECT_TYPE_ID\n" +
                    "  AND OBJ_PLACE.OBJECT_ID = REF_ZONE_PLACE.REFERENCE\n" +
                    "  AND REF_ZONE_PLACE.ATTR_ID = AT_ZONE_PLACE.ATTR_ID\n" +
                    "  AND AT_ZONE_PLACE.CODE = 'ZONE_REF'\n" +
                    "  AND OBJ_PLACE.OBJECT_ID = ATTR_PLACE_COL.OBJECT_ID\n" +
                    "  AND ATTR_PLACE_COL.ATTR_ID = AT_PLACE_COL.ATTR_ID\n" +
                    "  AND AT_PLACE_COL.CODE = 'NUMBER'\n" +
                    "  AND OBJ_PLACE.OBJECT_ID = ATTR_PLACE_ROW.OBJECT_ID\n" +
                    "  AND ATTR_PLACE_ROW.ATTR_ID = AT_PLACE_ROW.ATTR_ID\n" +
                    "  AND AT_PLACE_ROW.CODE = 'ROW'\n" +
                    "  \n" +
                    "  AND REF_ZONE.OBJECT_ID = OBJ_ZONE.OBJECT_ID\n" +
                    "  AND OBJ_ZONE.OBJECT_ID = REF_ZONE_PLACE.OBJECT_ID\n" +
                    "  AND ATTR_PLACE_COL.VALUE = ?\n" +
                    "  AND ATTR_PLACE_ROW.VALUE = ?\n" +
                    "  AND REF_SEANCE.OBJECT_ID = ?";

    String SELECT_PRICE_BY_SEANCE_ZONE =
            "SELECT VALUE FROM ATTRIBUTES atr\n" +
                    "  JOIN OBJECTS obj ON obj.OBJECT_ID = atr.OBJECT_ID\n" +
                    "  JOIN OBJREFERENCE seance_ref ON seance_ref.REFERENCE = obj.OBJECT_ID\n" +
                    "  JOIN OBJREFERENCE zone_ref ON zone_ref.REFERENCE = obj.OBJECT_ID\n" +
                    "    WHERE atr.ATTR_ID = 22          -- for ATTR_ID with CODE = 'PRICE'\n" +
                    "      AND seance_ref.OBJECT_ID = ?\n" +
                    "      AND zone_ref.OBJECT_ID = ?";
}