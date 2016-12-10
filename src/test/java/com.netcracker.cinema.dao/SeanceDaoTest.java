package com.netcracker.cinema.dao;

import com.netcracker.cinema.dao.filter.impl.SeanceFilter;
import com.netcracker.cinema.model.Seance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:seance-service-test-context.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SeanceDaoTest {

    @Autowired
    private SeanceDao seanceDao;

    @Test
    public void saveNewSeance() throws Exception {
        Seance expected = getOneSeance();

        seanceDao.save(expected);

        Seance actual = seanceDao.getById(expected.getId());

        assertSeanceEquals(expected, actual);
    }

    @Test
    public void saveExistingSeance() throws Exception {
        Seance expected = getOneSeance();
        seanceDao.save(expected);
        expected.setHallId(2);

        seanceDao.save(expected);
        Seance actual = seanceDao.getById(expected.getId());

        assertSeanceEquals(expected, actual);
    }

    @Test
    public void findExistingSeanceById() throws Exception {
        Seance expected = getOneSeance();
        seanceDao.save(expected);

        Seance actual = seanceDao.getById(expected.getId());

        assertSeanceEquals(expected, actual);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void findNotExistingSeanceById() throws Exception {
        Seance actual = seanceDao.getById(1000);
    }

    @Test
    public void findAllSeances() throws Exception {
        Seance expected1 = getOneSeance();

        Seance expected2 = getAnotherSeance();

        seanceDao.save(expected1);
        seanceDao.save(expected2);

        List<Seance> actualSeances = seanceDao.findAll();

        assertSeanceEquals(expected1, actualSeances.get(0));
        assertSeanceEquals(expected2, actualSeances.get(1));
    }

    @Test
    public void findAllSeancesNull() throws Exception {
        List<Seance> actual = seanceDao.findAll();
        assertEquals(0, actual.size());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteExistingSeance() throws Exception {
        Seance expected = getOneSeance();

        seanceDao.save(expected);
        seanceDao.delete(expected);

        Seance actual = seanceDao.getById(expected.getId());
    }

    @Test
    public void deleteNotExistingSeance() throws Exception {
        Seance expected = getOneSeance();
        expected.setId(301);

        seanceDao.delete(expected);
    }

    @Test
    public void pagination() throws Exception {
        List<Seance> expected = new ArrayList<>(100);

        for (int i = 0; i < 100; i++) {
            Seance seance = getOneSeance();
            seanceDao.save(seance);
            expected.add(seance);
        }

        Paginator<Seance> paginator = seanceDao.getPaginator(10);
        for(int i = 1; i <= 10; i++) {
            List<Seance> seances = paginator.getPage(i);
            assertEquals("Problem with size of returned seances", seances.size(), 10);
        }
        paginator.setPageSize(100);
        List<Seance> actual = paginator.getPage(1);
        for(int i = 0; i < expected.size(); i++) {
            assertSeanceEquals(expected.get(i), actual.get(i));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void paginationInvalidPage() throws Exception {
        seanceDao.getPaginator(-1);
    }

    @Test
    public void getAvailablePages() throws Exception {
        for (int i = 0; i < 99; i++) {
            Seance seance = getOneSeance();
            seanceDao.save(seance);
        }

        Paginator<Seance> paginator = seanceDao.getPaginator(10);
        long availablePages = paginator.availablePages();
        assertEquals("Problem with available pages", 10, availablePages);
    }

    @Test
    public void paginationWithFilter() throws Exception {
        for(int i = 0; i < 100; i++) {
            seanceDao.save(getOneSeance());
        }
        seanceDao.save(getAnotherSeance());

        Paginator<Seance> seancePaginator = seanceDao.getPaginator(0, new SeanceFilter().forHallId(2));

        assertEquals(1, seancePaginator.getPage(1).size());
        assertSeanceEquals(seancePaginator.getPage(1).get(0), getAnotherSeance());
    }

    @Test
    public void paginationActualSeance() throws Exception {

        Date currentDate = new Date();

        for(int i = 0; i < 100; i++) {
            seanceDao.save(getSeanceFromPast());
        }

        for(int i = 0; i < 99; i++) {
            seanceDao.save(getSeanceInFuture());
        }

        Paginator<Seance> paginator = seanceDao.getPaginator(10, new SeanceFilter().actual());
        List<Seance> seances = paginator.getPage(1);
        for(int i = 0; i < seances.size(); i++) {
            assertThat("seance is not actual", currentDate.getTime(), lessThan(seances.get(0).getSeanceDate().getTime()));
        }
        long availablePages = paginator.availablePages();
        assertEquals(10, availablePages);

    }

    private static void assertSeanceEquals(Seance expected, Seance actual) {
        assertEquals("Problem with id", expected.getId(), expected.getId());
        assertThat("Problem with seanceDate", expected.getSeanceDate().getTime() - actual.getSeanceDate().getTime(), is(lessThanOrEqualTo(2000L)));
        assertEquals("Problem with movieId", expected.getMovieId(), actual.getMovieId());
        assertEquals("Problem with hallId", expected.getHallId(), actual.getHallId());
    }

    private Seance getOneSeance() {
        Seance seance = new Seance();
        seance.setMovieId(1000);
        seance.setHallId(1);
        seance.setSeanceDate(new Date());
        return seance;
    }

    private Seance getAnotherSeance() {
        Seance seance = getOneSeance();
        seance.setHallId(2);
        return seance;
    }

    private Seance getSeanceInFuture() {
        Seance seance = getOneSeance();
        seance.setSeanceDate(new Date(new Date().getTime() + 1000000L));
        return seance;
    }

    private Seance getSeanceFromPast() {
        Seance seance = getOneSeance();
        seance.setSeanceDate(new Date(new Date().getTime() - 1000000L));
        return seance;
    }
}
