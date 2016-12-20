/*
Получить название ФИЛЬМА
в каком ЗАЛЕ оно показывается
сколько билетов было проданно по ЗОНЕ
Билеты находятся в файле populDB.sql
 */

SELECT
  movie.name                 movie_name,
  HALL.NAME                  HALL,
  ZONE.NAME                  ZONE,
  nvl(count(TICKET.NAME), 0) TICKET_BAY,
  sum(ticket_price.value)    ticket_price,
  rstart_att.date_value      start_date,
  rend_att.date_value        end_date
FROM
  objects MOVIE
  INNER JOIN attributes rstart_att ON movie.object_id = rstart_att.object_id
  INNER JOIN attributes rend_att ON movie.object_id = rend_att.object_id
  JOIN OBJREFERENCE MOVIE_SEANCE_REF ON MOVIE.OBJECT_ID = MOVIE_SEANCE_REF.OBJECT_ID
  , OBJECTS HALL
  JOIN OBJREFERENCE HALL_SEANCE_REF ON HALL.OBJECT_ID = HALL_SEANCE_REF.OBJECT_ID
   JOIN OBJREFERENCE HALL_PLACE_REF ON HALL.OBJECT_ID = HALL_PLACE_REF.OBJECT_ID
  , OBJECTS ZONE
  JOIN OBJREFERENCE ZONE_PLACE_REF ON ZONE.OBJECT_ID = ZONE_PLACE_REF.OBJECT_ID
  , OBJECTS PLACE
  JOIN OBJREFERENCE PLACE_TICKET_REF ON PLACE.OBJECT_ID = PLACE_TICKET_REF.OBJECT_ID
  , OBJECTS SEANCE
  JOIN OBJREFERENCE SEANCE_TICKET_REF ON SEANCE.OBJECT_ID = SEANCE_TICKET_REF.OBJECT_ID
  , OBJECTS TICKET
  INNER JOIN ATTRIBUTES ticket_price ON TICKET.OBJECT_ID = ticket_price.OBJECT_ID
  INNER JOIN ATTRIBUTES ticket_status ON TICKET.OBJECT_ID = ticket_status.OBJECT_ID

  --, OBJREFERENCE MOVIE_SEANCE_REF
  -- , OBJREFERENCE HALL_SEANCE_REF
  --, OBJREFERENCE ZONE_PLACE_REF
 -- , OBJREFERENCE HALL_PLACE_REF
-- , OBJREFERENCE SEANCE_TICKET_REF
--, OBJREFERENCE PLACE_TICKET_REF

WHERE
  HALL.OBJECT_TYPE_ID = 2
  AND ZONE.OBJECT_TYPE_ID = 3
  AND PLACE.OBJECT_TYPE_ID = 4
  AND SEANCE.OBJECT_TYPE_ID = 5
  AND TICKET.OBJECT_TYPE_ID = 6


  AND MOVIE_SEANCE_REF.REFERENCE = SEANCE.OBJECT_ID
  AND HALL_SEANCE_REF.REFERENCE = SEANCE.OBJECT_ID
  AND ZONE_PLACE_REF.REFERENCE = PLACE.OBJECT_ID
  AND HALL_PLACE_REF.REFERENCE = PLACE.OBJECT_ID
  AND SEANCE_TICKET_REF.REFERENCE = TICKET.OBJECT_ID
  AND PLACE_TICKET_REF.REFERENCE = TICKET.OBJECT_ID


  AND rstart_att.attr_id = 6
  AND rend_att.attr_id = 7
  AND ticket_price.attr_id = 18 --цена
  AND ticket_status.attr_id = 19 -- статус билета
  AND ticket_status.VALUE = 'TRUE' -- статус билета

GROUP BY ZONE.NAME
  , HALL.NAME
  , movie.name
  , rstart_att.date_value
  , rend_att.date_value;