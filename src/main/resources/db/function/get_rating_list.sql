CREATE OR REPLACE FUNCTION get_rating_list(
    F_START_DATE IN DATE
  , F_END_DATE   IN DATE
)
  RETURN Rating_List PIPELINED AS
  BEGIN
    FOR i IN (
    SELECT
      movie_name,
      HALL,
      ZONE,
      TICKET_SOLD,
      TICKET_PRICE,
      START_DATE,
      END_DATE
    FROM rating_table
    WHERE START_DATE >= F_START_DATE
          AND END_DATE <= F_END_DATE
    )
    LOOP
      PIPE ROW (type_rating_objects(i.movie_name, i.HALL, i.ZONE, i.TICKET_SOLD,
                                    i.TICKET_PRICE, i.START_DATE,
                                    i.END_DATE));
    END LOOP;
    RETURN;
  END;
#