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

    String SAVE_TICKET = "{call Save_Ticket(?, ?, ?, ?, ?, ?, ?)}";

    String SOLD_TICKETS = "SELECT Sold_Tickets(?, ?, ?) FROM dual";
}