package com.netcracker.cinema.dao.filter.impl;

public interface SeanceFilterQuery {

    String QUERY =
            "SELECT " +
            "   SEANCE.OBJECT_ID ID " +
            "   , DATE_ATTR.DATE_VALUE START_DATE " +
            "   , SEANCE_MOVIE_REF.OBJECT_ID MOVIE_ID " +
            "   , SEANCE_HALL_REF.OBJECT_ID HALL_ID " +
            "   , MOVIE.NAME MOVIE_NAME" +
            "   ,row_number() OVER (ORDER BY %s%n" +
            "   ) rn " +
            " FROM " +
            "   OBJECTS SEANCE " +
            "   LEFT JOIN ATTRIBUTES DATE_ATTR" +
            "       ON  SEANCE.OBJECT_ID = DATE_ATTR.OBJECT_ID " +
            "   LEFT JOIN OBJREFERENCE SEANCE_MOVIE_REF " +
            "       ON SEANCE.OBJECT_ID = SEANCE_MOVIE_REF.REFERENCE " +
            "   LEFT JOIN OBJREFERENCE SEANCE_HALL_REF " +
            "       ON SEANCE.OBJECT_ID = SEANCE_HALL_REF.REFERENCE " +
            "   LEFT JOIN OBJECTS MOVIE " +
            "       ON MOVIE.OBJECT_ID = SEANCE_MOVIE_REF.OBJECT_ID " +
            " WHERE " +
            "   SEANCE.OBJECT_TYPE_ID = 5 AND " +
            "   DATE_ATTR.ATTR_ID = 13 AND " +
            "   SEANCE_MOVIE_REF.ATTR_ID = 14 AND " +
            "   SEANCE_HALL_REF.ATTR_ID = 15 %s%n ";

    String DEFAULT_ORDER_BY_COLUMN = "SEANCE.OBJECT_ID";
}
