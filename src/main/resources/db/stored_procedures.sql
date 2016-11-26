CREATE OR REPLACE PROCEDURE tickets_quantity_for_hall
	( quantity OUT INTEGER
  , hall_in IN VARCHAR2
  ,	start_date_in IN DATE
  ,	end_date_in IN DATE
	)
IS
BEGIN
  SELECT COUNT(tick_obj.NAME) INTO quantity
	FROM OBJTYPE tick_obj_type
     , OBJTYPE seance_obj_type
     , ATTRTYPE tick_atr
     , ATTRTYPE seance_atr
     , ATTRIBUTES tick_abt
     , ATTRIBUTES seance_abt
     , OBJECTS tick_obj
     , OBJECTS seance_obj
     , OBJECTS hall_obj
     , OBJREFERENCE ref1
     , OBJREFERENCE ref2
      WHERE tick_obj_type.CODE = 'TICKET'
        AND tick_atr.CODE = 'IS_PAID'
        AND tick_abt.VALUE = 'TRUE'
        AND seance_obj_type.CODE = 'SEANCE'
        AND seance_atr.CODE = 'DATE'
        AND seance_abt.DATE_VALUE BETWEEN start_date_in
                                      AND end_date_in
        AND hall_obj.NAME = INITCAP(hall_in)
        AND tick_atr.OBJECT_TYPE_ID = tick_obj_type.OBJECT_TYPE_ID
        AND tick_abt.ATTR_ID=tick_atr.ATTR_ID
        AND tick_obj.OBJECT_ID=tick_abt.OBJECT_ID
        AND seance_abt.ATTR_ID = seance_atr.ATTR_ID
        AND seance_abt.OBJECT_ID = seance_obj.OBJECT_ID
        AND ref1.REFERENCE = tick_obj.OBJECT_ID
        AND ref1.OBJECT_ID = seance_obj.OBJECT_ID
        AND seance_obj.OBJECT_TYPE_ID = seance_obj_type.OBJECT_TYPE_ID
        AND ref2.REFERENCE = ref1.OBJECT_ID
        AND ref2.OBJECT_ID = hall_obj.OBJECT_ID
        ;
END;
/


CREATE OR REPLACE PROCEDURE tickets_quantity_for_movie 
	( quantity OUT INTEGER
  , movie_in IN VARCHAR2
  ,	start_date_in IN DATE
  ,	end_date_in IN DATE
	)
IS
BEGIN
  SELECT COUNT(tick_obj.NAME) INTO quantity
	FROM OBJTYPE tick_obj_type
     , OBJTYPE seance_obj_type
     , ATTRTYPE tick_atr
     , ATTRTYPE seance_atr
     , ATTRIBUTES tick_abt
     , ATTRIBUTES seance_abt
     , OBJECTS tick_obj
     , OBJECTS seance_obj
     , OBJECTS mov_obj
     , OBJREFERENCE ref1
     , OBJREFERENCE ref2
      WHERE tick_obj_type.CODE = 'TICKET'
        AND tick_atr.CODE = 'IS_PAID'
        AND tick_abt.VALUE = 'TRUE'
        AND seance_obj_type.CODE = 'SEANCE'
        AND seance_atr.CODE = 'DATE'
        AND seance_abt.DATE_VALUE BETWEEN start_date_in
                                      AND end_date_in
        AND mov_obj.NAME = movie_in
        AND tick_atr.OBJECT_TYPE_ID = tick_obj_type.OBJECT_TYPE_ID
        AND tick_abt.ATTR_ID=tick_atr.ATTR_ID
        AND tick_obj.OBJECT_ID=tick_abt.OBJECT_ID
        AND seance_abt.ATTR_ID = seance_atr.ATTR_ID
        AND seance_abt.OBJECT_ID = seance_obj.OBJECT_ID
        AND ref1.REFERENCE = tick_obj.OBJECT_ID
        AND ref1.OBJECT_ID = seance_obj.OBJECT_ID
        AND seance_obj.OBJECT_TYPE_ID = seance_obj_type.OBJECT_TYPE_ID
        AND ref2.REFERENCE = ref1.OBJECT_ID
        AND ref2.OBJECT_ID = mov_obj.OBJECT_ID
        ;
END;
/


CREATE OR REPLACE PROCEDURE tickets_quantity_for_zone 
	( quantity OUT INTEGER
  , zone_in IN VARCHAR2
  ,	start_date_in IN DATE
  ,	end_date_in IN DATE
	)
IS
BEGIN
  SELECT COUNT(tick_obj.NAME) INTO quantity
	FROM OBJTYPE tick_obj_type
     , OBJTYPE seance_obj_type
     , OBJTYPE place_obj_type
     , ATTRTYPE tick_atr
     , ATTRTYPE seance_atr
     , ATTRIBUTES tick_abt
     , ATTRIBUTES seance_abt
     , OBJECTS tick_obj
     , OBJECTS seance_obj
     , OBJECTS place_obj
     , OBJECTS zone_obj
     , OBJREFERENCE ref1
     , OBJREFERENCE ref2
     , OBJREFERENCE ref3
      WHERE tick_obj_type.CODE = 'TICKET'
        AND tick_atr.CODE = 'IS_PAID'
        AND tick_abt.VALUE = 'TRUE'
        AND seance_obj_type.CODE = 'SEANCE'
        AND place_obj_type.CODE = 'PLACE'
        AND seance_atr.CODE = 'DATE'
        AND seance_abt.DATE_VALUE BETWEEN start_date_in
                                      AND end_date_in
        AND zone_obj.NAME = UPPER(zone_in)
        AND tick_atr.OBJECT_TYPE_ID = tick_obj_type.OBJECT_TYPE_ID
        AND tick_abt.ATTR_ID = tick_atr.ATTR_ID
        AND tick_obj.OBJECT_ID = tick_abt.OBJECT_ID
        AND seance_abt.ATTR_ID = seance_atr.ATTR_ID
        AND seance_abt.OBJECT_ID = seance_obj.OBJECT_ID
        AND ref1.REFERENCE = tick_obj.OBJECT_ID
        AND ref1.OBJECT_ID = seance_obj.OBJECT_ID
        AND seance_obj.OBJECT_TYPE_ID = seance_obj_type.OBJECT_TYPE_ID
        AND ref2.REFERENCE = tick_obj.OBJECT_ID
        AND ref2.OBJECT_ID = place_obj.OBJECT_ID
        AND place_obj.OBJECT_TYPE_ID = place_obj_type.OBJECT_TYPE_ID
        AND ref3.REFERENCE = ref2.OBJECT_ID
        AND ref3.OBJECT_ID = zone_obj.OBJECT_ID
        ;
END;
/


/*
SET SERVEROUTPUT ON
DECLARE
	date1 Date := TO_DATE('2016-11-13', 'yyyy-mm-dd');
	date2 Date := TO_DATE('2016-11-20', 'yyyy-mm-dd');
	quantity INTEGER;
BEGIN
	tickets_quantity_for_hall(quantity, 'one', date1, date2);
	DBMS_OUTPUT.PUT_LINE(quantity);
END;
/
*/
