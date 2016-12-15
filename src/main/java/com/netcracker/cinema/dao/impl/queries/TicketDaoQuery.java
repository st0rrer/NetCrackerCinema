package com.netcracker.cinema.dao.impl.queries;

/**
 * Created by Mr. Tytarenko on 01.12.2016.
 */
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

    String FIND_TICKET_BY_SEANCE_OR_PLACE =
            "SELECT  obj.object_id AS id, attr_code.value AS code, attr_email.value AS email,\n" +
                    "   attr_price.value AS price, attr_paid.value AS paid,\n" +
                    "   ref_seance.object_id AS seance, ref_place.object_id AS place\n" +
                    "FROM objreference ref\n" +
                    "   JOIN objects obj ON obj.object_id = ref.reference\n" +
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
                    "WHERE ref.object_id = ?\n" +
                    "   AND obj.object_id = ref.reference\n" +
                    "   AND atype_code.code   = 'CODE'\n" +
                    "   AND atype_email.code  = 'EMAIL'\n" +
                    "   AND atype_price.code  = 'PRICE'\n" +
                    "   AND atype_paid.code   = 'IS_PAID'\n" +
                    "   AND atype_seance.code = 'SEANCE_REF'\n" +
                    "   AND atype_place.code  = 'PLACE_REF'";

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

    String DELETE_TICKET = "DELETE FROM objects WHERE object_id = ?";

    String DELETE_BLOCK_FOR_ONE_HOUR =
            "delete from objects obj_ticket where obj_ticket.object_id in (\n" +
            "          select distinct obj_ticket.object_id\n" +
            "          from objtype obj_type_seance, objtype obj_type_ticket, \n" +
            "              objects obj_seance,\n" +
            "              attributes attr_seance, objreference ref_seance, attributes attr_ticket,\n" +
            "              attrtype atype_seance, attrtype atype_ticket\n" +
            "          where obj_type_seance.code = 'SEANCE'\n" +
            "              and obj_type_seance.object_type_id = obj_seance.object_type_id\n" +
            "              and obj_seance.object_id = attr_seance.object_id\n" +
            "              and attr_seance.attr_id = atype_seance.attr_id\n" +
            "              and obj_type_ticket.code = 'TICKET'\n" +
            "              and obj_type_ticket.object_type_id = obj_ticket.object_type_id\n" +
            "              and obj_ticket.object_id = ref_seance.reference\n" +
            "              and obj_seance.object_id = ref_seance.object_id\n" +
            "              and obj_ticket.object_id = attr_ticket.object_id\n" +
            "              and attr_ticket.attr_id = atype_ticket.attr_id\n" +
            "              and atype_seance.code = 'DATE'\n" +
            "              and atype_ticket.code = 'IS_PAID'\n" +
            "              and to_date(attr_seance.date_value, 'DD-MM-YYYY HH24:MI:SS') < to_date(sysdate - 1/24, 'DD-MM-YYYY HH24:MI:SS')\n" +
            "              and attr_ticket.value = 'FALSE')";

    String SAVE_TICKET = "SELECT Save_Ticket(?, ?, ?, ?, ?, ?, ?) FROM dual";

    String SOLD_TICKETS = "SELECT Sold_Tickets(?, ?, ?) FROM dual";

}