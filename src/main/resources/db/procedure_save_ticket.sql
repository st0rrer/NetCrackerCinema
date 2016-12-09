-- Created by Titarenko on 09.12.2016.

CREATE OR REPLACE PROCEDURE Save_Ticket
	( ticket_id_in IN NUMBER
  , code_in IN VARCHAR2
  , email_in IN VARCHAR2
  , price_in IN VARCHAR2
  , paid_in IN VARCHAR2
  ,	seance_id_in IN NUMBER
  ,	place_id_in IN NUMBER
	)
IS
  ticket_id objects.OBJECT_ID%TYPE;
  code_atr_id attrtype.ATTR_ID%TYPE;
  mail_atr_id attrtype.ATTR_ID%TYPE;
  price_atr_id attrtype.ATTR_ID%TYPE;
  paid_atr_id attrtype.ATTR_ID%TYPE;
  sc_atr_id attrtype.ATTR_ID%TYPE;
  place_atr_id attrtype.ATTR_ID%TYPE;  
BEGIN

SELECT attr_id INTO code_atr_id FROM attrtype WHERE code = 'CODE';
SELECT attr_id INTO mail_atr_id FROM attrtype WHERE code = 'EMAIL';
SELECT atyp.attr_id INTO price_atr_id
  FROM attrtype atyp JOIN OBJTYPE ON OBJTYPE.object_type_id = atyp.object_type_id
  WHERE OBJTYPE.code = 'TICKET' AND atyp.code = 'PRICE';
SELECT attr_id INTO paid_atr_id FROM attrtype WHERE code = 'IS_PAID';
SELECT atyp.ATTR_ID INTO sc_atr_id
  FROM ATTRTYPE atyp
  JOIN OBJTYPE obj_typ ON obj_typ.OBJECT_TYPE_ID = atyp.OBJECT_TYPE_ID
  WHERE obj_typ.CODE = 'TICKET'
  AND atyp.CODE = 'SEANCE_REF';
SELECT atyp.ATTR_ID INTO place_atr_id
  FROM attrtype atyp
  JOIN OBJTYPE obj_typ ON obj_typ.OBJECT_TYPE_ID = atyp.OBJECT_TYPE_ID
  WHERE obj_typ.CODE = 'TICKET'
  AND atyp.CODE = 'PLACE_REF';

SELECT object_id INTO ticket_id FROM objects WHERE object_id = ticket_id_in;

UPDATE objects SET name = 'Ticket' || code_in WHERE object_id = ticket_id;
UPDATE ATTRIBUTES SET value = code_in WHERE object_id = ticket_id AND attr_id = code_atr_id;
UPDATE ATTRIBUTES SET value = email_in WHERE object_id = ticket_id AND attr_id = mail_atr_id;
UPDATE ATTRIBUTES SET value = price_in WHERE object_id = ticket_id AND attr_id = price_atr_id;
UPDATE ATTRIBUTES SET value = paid_in WHERE object_id = ticket_id AND attr_id = paid_atr_id;
UPDATE OBJREFERENCE SET object_id = seance_id_in WHERE reference = ticket_id AND attr_id = sc_atr_id;
UPDATE OBJREFERENCE SET object_id = place_id_in WHERE reference = ticket_id AND attr_id = place_atr_id;

EXCEPTION WHEN NO_DATA_FOUND THEN
ticket_id := get_obj_id.NEXTVAL;
  INSERT ALL
    INTO OBJECTS VALUES (ticket_id, NULL, (
      SELECT object_type_id FROM objtype WHERE code = 'TICKET'), 'Ticket' || code_in, NULL)
    INTO ATTRIBUTES VALUES(code_atr_id, ticket_id, code_in, NULL)
    INTO ATTRIBUTES VALUES(mail_atr_id, ticket_id, email_in, NULL)
    INTO ATTRIBUTES VALUES(price_atr_id, ticket_id, price_in, NULL)
    INTO ATTRIBUTES VALUES(paid_atr_id, ticket_id, paid_in, NULL)
    INTO OBJREFERENCE VALUES(sc_atr_id, ticket_id, seance_id_in)    
    INTO OBJREFERENCE VALUES(place_atr_id, ticket_id, place_id_in)
  SELECT * FROM dual;
END;
/