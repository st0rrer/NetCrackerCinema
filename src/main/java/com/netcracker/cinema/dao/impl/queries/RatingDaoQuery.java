package com.netcracker.cinema.dao.impl.queries;

public interface RatingDaoQuery {

    String ALL_RATINGS = "SELECT\n" +
            "  movie.name                 MOVIE_NAME,\n" +
            "  HALL.NAME                  HALL,\n" +
            "  ZONE.NAME                  ZONE,\n" +
            "  nvl(count(TICKET.NAME), 0) TICKET_SOLD,\n" +
            "  sum(ticket_price.value)    TICKET_PRICE,\n" +
            "  rstart_att.date_value      START_DATE,\n" +
            "  rend_att.date_value        END_DATE\n" +
            "-- start with tickets  \n" +
            "FROM objects ticket\n" +
            "  -- get place for the ticket  \n" +
            "  JOIN objreference ticket_place ON ticket_place.reference = ticket.object_id\n" +
            "                                    AND ticket_place.attr_id = 21\n" +
            "  INNER JOIN ATTRIBUTES ticket_price ON TICKET.OBJECT_ID = ticket_price.OBJECT_ID\n" +
            "                                        AND ticket_price.attr_id = 18\n" +
            "  --цена  \n" +
            "  INNER JOIN ATTRIBUTES ticket_status ON TICKET.OBJECT_ID = ticket_status.OBJECT_ID\n" +
            "                                         AND ticket_status.attr_id = 19 -- статус билета  \n" +
            "                                         AND ticket_status.VALUE = 'TRUE'\n" +
            "  JOIN objects place ON place.object_id = ticket_place.object_id\n" +
            "  -- get hall by place  \n" +
            "  JOIN objreference place_hall ON place_hall.reference = place.object_id\n" +
            "                                  AND place_hall.attr_id = 11\n" +
            "  JOIN objects hall ON hall.object_id = place_hall.object_id\n" +
            "  -- get zone by place  \n" +
            "  JOIN objreference place_zone ON place_zone.reference = place.object_id\n" +
            "                                  AND place_zone.attr_id = 12\n" +
            "  JOIN objects zone ON zone.object_id = place_zone.object_id\n" +
            "  -- get seance by ticket  \n" +
            "  JOIN objreference ticket_seance ON ticket_seance.reference = ticket.object_id\n" +
            "                                     AND ticket_seance.attr_id = 20\n" +
            "  JOIN objects seance ON seance.object_id = ticket_seance.object_id\n" +
            "  -- get movie by seance  \n" +
            "  JOIN objreference seance_movie ON seance_movie.reference = seance.object_id\n" +
            "                                    AND seance_movie.attr_id = 14\n" +
            "  JOIN objects movie ON movie.object_id = seance_movie.object_id\n" +
            "  INNER JOIN attributes rstart_att ON movie.object_id = rstart_att.object_id\n" +
            "                                      AND rstart_att.attr_id = 6\n" +
            "  INNER JOIN attributes rend_att ON movie.object_id = rend_att.object_id\n" +
            "                                    AND rend_att.attr_id = 7\n" +
            "\n" +
            "WHERE 1 = 1\n" +
            "      AND ticket.object_type_id = 6 /*TICKET*/\n" +
            "      AND place.object_type_id = 4 /*PLACE*/\n" +
            "      AND hall.object_type_id = 2 /*HALL*/\n" +
            "      AND zone.object_type_id = 3 /*ZONE*/\n" +
            "      AND seance.object_type_id = 5 /*SEANCE*/\n" +
            "      AND movie.object_type_id = 1 /*MOVIE*/\n" +
            "\n" +
            "GROUP BY ZONE.NAME\n" +
            "  , HALL.NAME\n" +
            "  , movie.name\n" +
            "  , rstart_att.date_value\n" +
            "  , rend_att.date_value";

    String CALL_ALL_RATINGS = "SELECT * FROM TABLE (get_rating_list(?,?)) ";

}

