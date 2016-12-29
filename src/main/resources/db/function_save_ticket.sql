-- Created by Titarenko on 09.12.2016.

CREATE OR REPLACE FUNCTION Save_Ticket
  ( ticket_id_in IN NUMBER
  , code_in IN VARCHAR2
  , email_in IN VARCHAR2
  , price_in IN VARCHAR2
  , paid_in IN VARCHAR2
  ,	seance_id_in IN NUMBER
  ,	place_id_in IN NUMBER
  )
RETURN NUMBER
IS
  PRAGMA AUTONOMOUS_TRANSACTION;
  ticket_id objects.OBJECT_ID%TYPE;
  tick_obj_typ objtype.OBJECT_TYPE_ID%TYPE := 6; -- object_type_id for code = 'TICKET'
  code_atr_id attrtype.ATTR_ID%TYPE   := 16;     -- attr_id for code = 'CODE'
  mail_atr_id attrtype.ATTR_ID%TYPE   := 17;     -- attr_id for code = 'EMAIL'
  price_atr_id attrtype.ATTR_ID%TYPE  := 18;     -- attr_id for code = 'PRICE'
  paid_atr_id attrtype.ATTR_ID%TYPE   := 19;     -- attr_id for code = 'IS_PAID'
  seance_atr_id attrtype.ATTR_ID%TYPE := 20;     -- attr_id for code = 'SEANCE_REF'
  place_atr_id attrtype.ATTR_ID%TYPE  := 21;     -- attr_id for code = 'PLACE_REF'
BEGIN

SELECT object_id INTO ticket_id FROM objects WHERE object_id = ticket_id_in;

UPDATE objects SET name = 'Ticket' || code_in WHERE object_id = ticket_id;
UPDATE ATTRIBUTES SET value = code_in WHERE object_id = ticket_id AND attr_id = code_atr_id;
UPDATE ATTRIBUTES SET value = email_in WHERE object_id = ticket_id AND attr_id = mail_atr_id;
UPDATE ATTRIBUTES SET value = price_in WHERE object_id = ticket_id AND attr_id = price_atr_id;
UPDATE ATTRIBUTES SET value = paid_in WHERE object_id = ticket_id AND attr_id = paid_atr_id;
UPDATE OBJREFERENCE SET object_id = seance_id_in WHERE reference = ticket_id AND attr_id = seance_atr_id;
UPDATE OBJREFERENCE SET object_id = place_id_in WHERE reference = ticket_id AND attr_id = place_atr_id;

COMMIT;
RETURN ticket_id;

EXCEPTION WHEN NO_DATA_FOUND THEN
ticket_id := get_obj_id.NEXTVAL;
  INSERT ALL
    INTO OBJECTS VALUES (ticket_id, NULL, tick_obj_typ, 'Ticket' || code_in, NULL)
    INTO ATTRIBUTES VALUES(code_atr_id, ticket_id, code_in, NULL)
    INTO ATTRIBUTES VALUES(mail_atr_id, ticket_id, email_in, NULL)
    INTO ATTRIBUTES VALUES(price_atr_id, ticket_id, price_in, NULL)
    INTO ATTRIBUTES VALUES(paid_atr_id, ticket_id, paid_in, NULL)
    INTO OBJREFERENCE VALUES(seance_atr_id, ticket_id, seance_id_in)
    INTO OBJREFERENCE VALUES(place_atr_id, ticket_id, place_id_in)
  SELECT * FROM dual;
COMMIT;
RETURN ticket_id;

END;
#