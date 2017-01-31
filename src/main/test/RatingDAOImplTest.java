/**
 * Created by gaya on 18.01.2017.
 */


import com.netcracker.cinema.model.Rating;
import com.netcracker.cinema.service.RatingService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class RatingDAOImplTest {
    RatingService ratingService;

    @Before
    public void setUp() {
        Locale.setDefault(Locale.ENGLISH);
        ApplicationContext context = new FileSystemXmlApplicationContext(
                "src/main/webapp/WEB-INF/applicationContext.xml");
        ratingService = (RatingService) context.getBean("ratingService");
    }

    @Test
    public void testGetRatingFindAll() {
        System.out.println(ratingService.findAll());
    }

    @Test
    public void testGetRatingDate() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsed1 = format.parse("2016-09-30");
        Date parsed2 = format.parse("2018-01-27");
        java.sql.Date sql1 = new java.sql.Date(parsed1.getTime());
        java.sql.Date sql2 = new java.sql.Date(parsed2.getTime());
        List<Rating> comp = ratingService.allRating(sql1, sql2);
        System.out.println(comp);
    }
}
