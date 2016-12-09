-- Created by Titarenko on 09.12.2016.

CREATE OR REPLACE FUNCTION Sold_Tickets
	( obj_name_in IN VARCHAR2
  ,	start_date_in IN DATE
  ,	end_date_in IN DATE
	)
RETURN INTEGER
IS
  entity OBJTYPE.CODE%TYPE;
	quantity INTEGER(6);
BEGIN
  SELECT OBJTYPE.CODE INTO entity 
    FROM OBJTYPE JOIN OBJECTS obj ON obj.OBJECT_TYPE_ID = OBJTYPE.OBJECT_TYPE_ID
    WHERE obj.NAME = obj_name_in;
IF entity = 'ZONE' THEN
  SELECT COUNT(tick_obj.NAME) INTO quantity
	FROM OBJTYPE tick_obj_typ
    JOIN ATTRTYPE tick_atyp    ON tick_atyp.OBJECT_TYPE_ID = tick_obj_typ.OBJECT_TYPE_ID
    JOIN ATTRIBUTES tick_attr  ON tick_attr.ATTR_ID = tick_atyp.ATTR_ID
    JOIN OBJECTS tick_obj      ON tick_obj.OBJECT_ID = tick_attr.OBJECT_ID
    JOIN OBJREFERENCE ref1     ON ref1.REFERENCE = tick_obj.OBJECT_ID
    JOIN OBJECTS sc_obj        ON sc_obj.OBJECT_ID = ref1.OBJECT_ID
    JOIN OBJTYPE sc_obj_typ    ON sc_obj_typ.OBJECT_TYPE_ID = sc_obj.OBJECT_TYPE_ID
    JOIN ATTRIBUTES sc_attr    ON sc_attr.OBJECT_ID = sc_obj.OBJECT_ID
    JOIN ATTRTYPE sc_atyp      ON sc_atyp.ATTR_ID = sc_attr.ATTR_ID
    JOIN OBJREFERENCE ref2     ON ref2.REFERENCE = tick_obj.OBJECT_ID
    JOIN OBJECTS place_obj     ON place_obj.OBJECT_ID = ref2.OBJECT_ID
    JOIN OBJTYPE place_obj_typ ON place_obj_typ.OBJECT_TYPE_ID = place_obj.OBJECT_TYPE_ID
    JOIN OBJREFERENCE ref3     ON ref3.REFERENCE = ref2.OBJECT_ID
    JOIN OBJECTS zone_obj      ON zone_obj.OBJECT_ID = ref3.OBJECT_ID
      WHERE tick_obj_typ.CODE = 'TICKET'
        AND tick_atyp.CODE = 'IS_PAID'
        AND tick_attr.VALUE = 'TRUE'
        AND sc_obj_typ.CODE = 'SEANCE'
        AND place_obj_typ.CODE = 'PLACE'
        AND sc_atyp.CODE = 'DATE'
        AND sc_attr.DATE_VALUE BETWEEN start_date_in
                                      AND end_date_in
        AND zone_obj.NAME = obj_name_in;
ELSIF entity = 'HALL' OR entity = 'MOVIE' THEN
  SELECT COUNT(tick_obj.NAME) INTO quantity
	FROM OBJTYPE tick_obj_typ
    JOIN ATTRTYPE tick_atyp   ON tick_atyp.OBJECT_TYPE_ID = tick_obj_typ.OBJECT_TYPE_ID
    JOIN ATTRIBUTES tick_atyp ON tick_atyp.ATTR_ID = tick_atyp.ATTR_ID
    JOIN OBJECTS tick_obj     ON tick_obj.OBJECT_ID = tick_atyp.OBJECT_ID
    JOIN OBJREFERENCE ref1    ON ref1.REFERENCE = tick_obj.OBJECT_ID
    JOIN OBJECTS sc_obj       ON sc_obj.OBJECT_ID = ref1.OBJECT_ID
    JOIN OBJTYPE sc_obj_typ   ON sc_obj_typ.OBJECT_TYPE_ID = sc_obj.OBJECT_TYPE_ID
    JOIN ATTRIBUTES sc_attr   ON sc_attr.OBJECT_ID = sc_obj.OBJECT_ID
    JOIN ATTRTYPE sc_atyp     ON sc_atyp.ATTR_ID = sc_attr.ATTR_ID
    JOIN OBJREFERENCE ref2    ON ref2.REFERENCE = ref1.OBJECT_ID
    JOIN OBJECTS hm_obj       ON hm_obj.OBJECT_ID = ref2.OBJECT_ID
    WHERE tick_obj_typ.CODE = 'TICKET'
      AND sc_obj_typ.CODE = 'SEANCE'
      AND tick_atyp.CODE = 'IS_PAID'
      AND tick_atyp.VALUE = 'TRUE'
      AND sc_atyp.CODE = 'DATE'
      AND sc_attr.DATE_VALUE BETWEEN start_date_in
                               AND end_date_in
      AND hm_obj.NAME = obj_name_in;
ELSE
  RETURN -1;
END IF;
	RETURN quantity;
EXCEPTION WHEN NO_DATA_FOUND THEN
  RETURN -1;
END;#