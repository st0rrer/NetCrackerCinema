CREATE OR REPLACE FUNCTION get_rating_list(
    F_START_DATE IN DATE
  , F_END_DATE   IN DATE
)
  RETURN Rating_List PIPELINED AS
  BEGIN
    FOR i IN (
    SELECT
      movie_name,
      hall_name,
      zone_name,
      ticket_bay,
      price,
      start_date,
      end_date
    FROM rating_table
    WHERE start_date >= F_START_DATE
          AND end_date <= F_END_DATE
    )
    LOOP
      PIPE ROW (type_rating_objects(i.movie_name, i.hall_name, i.zone_name, i.ticket_bay, i.price, i.start_date,
                                    i.end_date));
    END LOOP;
    RETURN;
  END;
#
