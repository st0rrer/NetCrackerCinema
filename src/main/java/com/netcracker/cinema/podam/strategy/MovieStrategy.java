package com.netcracker.cinema.podam.strategy;

import com.netcracker.cinema.model.Movie;
import uk.co.jemos.podam.api.AbstractRandomDataProviderStrategy;
import uk.co.jemos.podam.api.AttributeMetadata;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by gaya on 15.11.2016.
 */
public class MovieStrategy extends AbstractRandomDataProviderStrategy {

    private static final Random random = new Random(System.currentTimeMillis());

    public MovieStrategy() {
        super();
    }

    @Override
    public String getStringValue(AttributeMetadata attributeMetadata) {

        if ("name".equals(attributeMetadata.getAttributeName())) {
            if (Movie.class.equals(attributeMetadata.getPojoClass())) {
                return MovieStrategy.Title.randomMovies();
            }
        }

        if ("poster".equals(attributeMetadata.getAttributeName())) {
            if (Movie.class.equals(attributeMetadata.getPojoClass())) {
                return MovieStrategy.Poster.randomPoster();
            }
        }

        return super.getStringValue(attributeMetadata);
    }

    private enum Title {

        The_Wizard_Of_Oz, The_Third_Man, Citizen_Kane, All_About_Eve, The_Godfather, Inside_Out, Metropolis,
        Modern_Times;

        private static final List<MovieStrategy.Title> values = Collections.unmodifiableList(Arrays.asList(values()));
        private static final int size = values.size();
        private static final Random random = new Random();

        public static String randomMovies() {
            return values.get(random.nextInt(size)).toString();
        }
    }



    private enum Poster {

        The_Wizard_Of_Oz, The_Third_Man, Citizen_Kane, All_About_Eve, The_Godfather, Inside_Out, Metropolis,
        Modern_Times;

        private static final List<MovieStrategy.Poster> values = Collections.unmodifiableList(Arrays.asList(values()));
        private static final int size = values.size();
        private static final Random random = new Random();

        public static String randomPoster() {
            return values.get(random.nextInt(size)).toString();
        }
    }
}
