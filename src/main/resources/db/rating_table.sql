DROP TABLE rating_table;
DROP TYPE type_rating_objects FORCE;
DROP TYPE Rating_List FORCE;



CREATE TABLE rating_table (
  movie_name VARCHAR2(500),
  hall_name  VARCHAR2(500),
  zone_name  VARCHAR2(500),
  ticket_bay NUMBER,
  price      NUMBER,
  start_date DATE,
  end_date   DATE
);

-- объявление типа
CREATE TYPE type_rating_objects IS OBJECT (
  movie_name VARCHAR2(500),
  hall_name  VARCHAR2(500),
  zone_name  VARCHAR2(500),
  ticket_bay NUMBER,
  price      NUMBER,
  start_date DATE,
  end_date   DATE
);

-- объявление типа
CREATE TYPE Rating_List IS TABLE OF type_rating_objects;

INSERT INTO rating_table (movie_name,
                          hall_name,
                          zone_name,
                          ticket_bay,
                          price,
                          start_date,
                          end_date
)   SELECT
    movie.name                 movie_name,
    HALL.NAME                  HALL,
    ZONE.NAME                  ZONE,
    nvl(count(TICKET.NAME), 0) TICKET_SOLD,
    sum(ticket_price.value)    ticket_price,
    rstart_att.date_value      start_date,
    rend_att.date_value        end_date
  -- start with tickets
  FROM objects ticket
    -- get place for the ticket
    JOIN objreference ticket_place ON ticket_place.reference = ticket.object_id AND ticket_place.attr_id = 21
    INNER JOIN ATTRIBUTES ticket_price ON TICKET.OBJECT_ID = ticket_price.OBJECT_ID
                                          AND ticket_price.attr_id = 18
    --цена
    INNER JOIN ATTRIBUTES ticket_status ON TICKET.OBJECT_ID = ticket_status.OBJECT_ID
                                           AND ticket_status.attr_id = 19 -- статус билета
                                           AND ticket_status.VALUE = 'TRUE'
    -- статус билета
    JOIN objects place ON place.object_id = ticket_place.object_id
    -- get hall by place
    JOIN objreference place_hall ON place_hall.reference = place.object_id AND place_hall.attr_id = 11
    JOIN objects hall ON hall.object_id = place_hall.object_id
    -- get zone by place
    JOIN objreference place_zone ON place_zone.reference = place.object_id AND place_zone.attr_id = 12
    JOIN objects zone ON zone.object_id = place_zone.object_id
    -- get seance by ticket
    JOIN objreference ticket_seance ON ticket_seance.reference = ticket.object_id AND ticket_seance.attr_id = 20
    JOIN objects seance ON seance.object_id = ticket_seance.object_id
    -- get movie by seance
    JOIN objreference seance_movie ON seance_movie.reference = seance.object_id AND seance_movie.attr_id = 14
    JOIN objects movie ON movie.object_id = seance_movie.object_id
    INNER JOIN attributes rstart_att ON movie.object_id = rstart_att.object_id
                                        AND rstart_att.attr_id = 6
    INNER JOIN attributes rend_att ON movie.object_id = rend_att.object_id
                                      AND rend_att.attr_id = 7

  WHERE 1 = 1
        AND ticket.object_type_id = 6 /*TICKET*/
        AND place.object_type_id = 4 /*PLACE*/
        AND hall.object_type_id = 2 /*HALL*/
        AND zone.object_type_id = 3 /*ZONE*/
        AND seance.object_type_id = 5 /*SEANCE*/
        AND movie.object_type_id = 1 /*MOVIE*/
        AND rstart_att.date_value >= ?
        AND rend_att.date_value <= ?

  GROUP BY ZONE.NAME
    , HALL.NAME
    , movie.name
    , rstart_att.date_value
    , rend_att.date_value;























