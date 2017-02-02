package com.netcracker.cinema.dao.impl.queries;

public interface TicketDaoQuery {

    String FIND_ALL_TICKETS =
            "SELECT  obj.object_id AS id, attr_code.value AS code, attr_email.value AS email,\n" +
                    "   attr_price.value AS price, attr_paid.value AS paid,\n" +
                    "   ref_seance.object_id AS seance, ref_place.object_id AS place\n" +
                    "FROM objtype otp\n" +
                    "   JOIN objects obj ON obj.object_type_id = otp.object_type_id\n" +
                    "   JOIN attributes attr_code  ON  attr_code.object_id = obj.object_id\n" +
                    "   JOIN attrtype  atype_code  ON   atype_code.attr_id = attr_code.attr_id\n" +
                    "   JOIN attributes attr_email ON attr_email.object_id = obj.object_id\n" +
                    "   JOIN attrtype  atype_email ON  atype_email.attr_id = attr_email.attr_id\n" +
                    "   JOIN attributes attr_price ON attr_price.object_id = obj.object_id\n" +
                    "   JOIN attrtype  atype_price ON  atype_price.attr_id = attr_price.attr_id\n" +
                    "   JOIN attributes attr_paid  ON attr_paid.object_id = obj.object_id\n" +
                    "   JOIN attrtype  atype_paid  ON  atype_paid.attr_id = attr_paid.attr_id\n" +
                    "   JOIN objreference ref_seance ON  ref_seance.reference = obj.object_id\n" +
                    "   JOIN attrtype  atype_seance  ON  atype_seance.attr_id = ref_seance.attr_id\n" +
                    "   JOIN objreference ref_place  ON   ref_place.reference = obj.object_id\n" +
                    "   JOIN attrtype  atype_place   ON   atype_place.attr_id = ref_place.attr_id\n" +
                    "WHERE otp.code = 'TICKET'\n" +
                    "   AND atype_code.code = 'CODE'\n" +
                    "   AND atype_email.code = 'EMAIL'\n" +
                    "   AND atype_price.code = 'PRICE'\n" +
                    "   AND atype_paid.code = 'IS_PAID'\n" +
                    "   AND atype_seance.code = 'SEANCE_REF'\n" +
                    "   AND atype_place.code = 'PLACE_REF'\n" +
                    "ORDER BY id";

    String FIND_TICKETS_BY_CODE = "SELECT\n" +
            "      obj.object_id AS id\n" +
            "      , attr_code.value AS code\n" +
            "      , attr_email.value AS email\n" +
            "      , attr_price.value AS price\n" +
            "      , attr_paid.value AS paid\n" +
            "      , ref_seance.object_id AS seance\n" +
            "      , ref_place.object_id AS place\n" +
            "FROM\n" +
            "      objects obj\n" +
            "      LEFT JOIN attributes attr_code\n" +
            "            ON attr_code.object_id = obj.object_id\n" +
            "      LEFT JOIN attributes attr_email\n" +
            "            ON attr_email.object_id = obj.object_id\n" +
            "      LEFT JOIN attributes attr_price\n" +
            "            ON attr_price.object_id = obj.object_id\n" +
            "      LEFT JOIN attributes attr_paid\n" +
            "            ON attr_paid.object_id = obj.object_id\n" +
            "      LEFT JOIN objreference ref_seance\n" +
            "            ON ref_seance.reference = obj.object_id\n" +
            "      LEFT JOIN objreference ref_place\n" +
            "            ON ref_place.reference = obj.object_id\n" +
            "WHERE\n" +
            "      attr_code.VALUE = ?\n" +
            "      AND obj.OBJECT_TYPE_ID = 6\n" +
            "      AND attr_code.ATTR_ID = 16\n" +
            "      AND attr_email.ATTR_ID = 17\n" +
            "      AND attr_price.ATTR_ID = 18\n" +
            "      AND attr_paid.ATTR_ID = 19\n" +
            "      AND ref_seance.ATTR_ID = 20\n" +
            "      AND ref_place.ATTR_ID = 21";

    String FIND_TICKET_BY_SEANCE_AND_PLACE =
            " SELECT " +
                    " TICKET.OBJECT_ID id " +
                    ", CODE_ATTR.VALUE code " +
                    ", EMAIL_ATTR.VALUE email " +
                    ", PRICE_ATTR.VALUE price " +
                    ", IS_PAID_ATTR.VALUE paid " +
                    ", TICKET_SEANCE_REF.OBJECT_ID seance " +
                    ", TICKET_PLACE_REF.OBJECT_ID place" +
                    " FROM " +
                    " OBJECTS TICKET " +
                    " LEFT JOIN ATTRIBUTES CODE_ATTR" +
                    " ON  TICKET.OBJECT_ID = CODE_ATTR.OBJECT_ID " +
                    " LEFT JOIN ATTRIBUTES EMAIL_ATTR " +
                    " ON TICKET.OBJECT_ID = EMAIL_ATTR.OBJECT_ID " +
                    " LEFT JOIN ATTRIBUTES PRICE_ATTR " +
                    " ON TICKET.OBJECT_ID = PRICE_ATTR.OBJECT_ID " +
                    " LEFT JOIN ATTRIBUTES IS_PAID_ATTR " +
                    " ON TICKET.OBJECT_ID = IS_PAID_ATTR.OBJECT_ID " +
                    " LEFT JOIN OBJREFERENCE TICKET_SEANCE_REF " +
                    " ON TICKET.OBJECT_ID = TICKET_SEANCE_REF.REFERENCE " +
                    " LEFT JOIN OBJREFERENCE TICKET_PLACE_REF " +
                    " ON TICKET.OBJECT_ID = TICKET_PLACE_REF.REFERENCE " +
                    " WHERE " +
                    " TICKET.OBJECT_TYPE_ID = 6 AND " +
                    " CODE_ATTR.ATTR_ID = 16 AND " +
                    " EMAIL_ATTR.ATTR_ID = 17 AND " +
                    " PRICE_ATTR.ATTR_ID = 18 AND " +
                    " IS_PAID_ATTR.ATTR_ID = 19 AND " +
                    " TICKET_SEANCE_REF.ATTR_ID = 20 AND" +
                    " TICKET_PLACE_REF.ATTR_ID = 21 AND" +
                    " TICKET_SEANCE_REF.OBJECT_ID = ? AND " +
                    " TICKET_PLACE_REF.OBJECT_ID = ?";

    String FIND_TICKETS_BY_SEANCE =
            " SELECT " +
                    " TICKET.OBJECT_ID id " +
                    ", CODE_ATTR.VALUE code " +
                    ", EMAIL_ATTR.VALUE email " +
                    ", PRICE_ATTR.VALUE price " +
                    ", IS_PAID_ATTR.VALUE paid " +
                    ", TICKET_SEANCE_REF.OBJECT_ID seance " +
                    ", TICKET_PLACE_REF.OBJECT_ID place" +
                    " FROM " +
                    " OBJECTS TICKET " +
                    " LEFT JOIN ATTRIBUTES CODE_ATTR" +
                    " ON  TICKET.OBJECT_ID = CODE_ATTR.OBJECT_ID " +
                    " LEFT JOIN ATTRIBUTES EMAIL_ATTR " +
                    " ON TICKET.OBJECT_ID = EMAIL_ATTR.OBJECT_ID " +
                    " LEFT JOIN ATTRIBUTES PRICE_ATTR " +
                    " ON TICKET.OBJECT_ID = PRICE_ATTR.OBJECT_ID " +
                    " LEFT JOIN ATTRIBUTES IS_PAID_ATTR " +
                    " ON TICKET.OBJECT_ID = IS_PAID_ATTR.OBJECT_ID " +
                    " LEFT JOIN OBJREFERENCE TICKET_SEANCE_REF " +
                    " ON TICKET.OBJECT_ID = TICKET_SEANCE_REF.REFERENCE " +
                    " LEFT JOIN OBJREFERENCE TICKET_PLACE_REF " +
                    " ON TICKET.OBJECT_ID = TICKET_PLACE_REF.REFERENCE " +
                    " WHERE " +
                    " TICKET.OBJECT_TYPE_ID = 6 AND " +
                    " CODE_ATTR.ATTR_ID = 16 AND " +
                    " EMAIL_ATTR.ATTR_ID = 17 AND " +
                    " PRICE_ATTR.ATTR_ID = 18 AND " +
                    " IS_PAID_ATTR.ATTR_ID = 19 AND " +
                    " TICKET_SEANCE_REF.ATTR_ID = 20 AND" +
                    " TICKET_PLACE_REF.ATTR_ID = 21 AND" +
                    " TICKET_SEANCE_REF.OBJECT_ID = ?";

    String FIND_TICKET_BY_ID =
            "SELECT  obj.object_id AS id, attr_code.value AS code, attr_email.value AS email,\n" +
                    "   attr_price.value AS price, attr_paid.value AS paid,\n" +
                    "   ref_seance.object_id AS seance, ref_place.object_id AS place\n" +
                    "FROM objects obj\n" +
                    "   JOIN attributes attr_code  ON  attr_code.object_id = obj.object_id\n" +
                    "   JOIN attrtype  atype_code  ON   atype_code.attr_id = attr_code.attr_id\n" +
                    "   JOIN attributes attr_email ON attr_email.object_id = obj.object_id\n" +
                    "   JOIN attrtype  atype_email ON  atype_email.attr_id = attr_email.attr_id\n" +
                    "   JOIN attributes attr_price ON attr_price.object_id = obj.object_id\n" +
                    "   JOIN attrtype  atype_price ON  atype_price.attr_id = attr_price.attr_id\n" +
                    "   JOIN attributes attr_paid  ON attr_paid.object_id = obj.object_id\n" +
                    "   JOIN attrtype  atype_paid  ON  atype_paid.attr_id = attr_paid.attr_id\n" +
                    "   JOIN objreference ref_seance ON  ref_seance.reference = obj.object_id\n" +
                    "   JOIN attrtype  atype_seance  ON  atype_seance.attr_id = ref_seance.attr_id\n" +
                    "   JOIN objreference ref_place  ON   ref_place.reference = obj.object_id\n" +
                    "   JOIN attrtype  atype_place   ON   atype_place.attr_id = ref_place.attr_id\n" +
                    "WHERE obj.object_id = ?\n" +
                    "   AND atype_code.code   = 'CODE'\n" +
                    "   AND atype_email.code  = 'EMAIL'\n" +
                    "   AND atype_price.code  = 'PRICE'\n" +
                    "   AND atype_paid.code   = 'IS_PAID'\n" +
                    "   AND atype_seance.code = 'SEANCE_REF'\n" +
                    "   AND atype_place.code  = 'PLACE_REF'";

    String FIND_CODE_TICKET = "SELECT code_number.nextval FROM DUAL";

    String DELETE_TICKET = "DELETE FROM objects WHERE object_id = ?";

    String DELETE_BLOCK_FOR_ONE_HOUR =
            "delete from objects obj_ticket where obj_ticket.object_id in (\n" +
                    "  select distinct obj_ticket.object_id\n" +
                    "  from objtype obj_type_seance, objtype obj_type_ticket,\n" +
                    "      objects obj_seance,\n" +
                    "      attributes attr_seance, objreference ref_seance, attributes attr_ticket,\n" +
                    "      attrtype atype_seance, attrtype atype_ticket\n" +
                    "  where obj_type_seance.code = 'SEANCE'\n" +
                    "      and obj_type_seance.object_type_id = obj_seance.object_type_id\n" +
                    "      and obj_seance.object_id = attr_seance.object_id\n" +
                    "      and attr_seance.attr_id = atype_seance.attr_id\n" +
                    "      and obj_type_ticket.code = 'TICKET'\n" +
                    "      and obj_type_ticket.object_type_id = obj_ticket.object_type_id\n" +
                    "      and obj_ticket.object_id = ref_seance.reference\n" +
                    "      and obj_seance.object_id = ref_seance.object_id\n" +
                    "      and obj_ticket.object_id = attr_ticket.object_id\n" +
                    "      and attr_ticket.attr_id = atype_ticket.attr_id\n" +
                    "      and atype_ticket.code = 'IS_PAID'\n" +
                    "      and attr_ticket.value = 'FALSE'\n" +
                    "      and obj_seance.object_id = ?)";

    String SAVE_TICKET = "SELECT Save_Ticket(?, ?, ?, ?, ?, ?, ?) FROM dual";

    String SOLD_TICKETS = "SELECT Sold_Tickets(?, ?, ?) FROM dual";

}