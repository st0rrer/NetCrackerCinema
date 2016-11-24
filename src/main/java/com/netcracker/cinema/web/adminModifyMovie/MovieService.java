package com.netcracker.cinema.web.adminModifyMovie;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An in memory dummy "database" for the example purposes. In a typical Java app
 * this class would be replaced by e.g. EJB or a Spring based service class.
 * <p>
 * In demos/tutorials/examples, get a reference to this service class with
 * {@link MovieService#getInstance()}.
 */
public class MovieService {

	private static MovieService instance;
	private static final Logger LOGGER = Logger.getLogger(MovieService.class.getName());

	private final HashMap<Long, Movie> movies = new HashMap<>();
	private long nextId = 0;

	private MovieService() {
	}

	/**
	 * @return a reference to an example facade for Movie objects.
	 */
	public static MovieService getInstance() {
		if (instance == null) {
			instance = new MovieService();
			instance.ensureTestData();
		}
		return instance;
	}

	/**
	 * @return all available Movie objects.
	 */
	public synchronized List<Movie> findAll() {
		return findAll(null);
	}

	/**
	 * Finds all Movie's that match given filter.
	 *
	 * @param stringFilter
	 *            filter that returned objects should match or null/empty string
	 *            if all objects should be returned.
	 * @return list a Movie objects
	 */
	public synchronized List<Movie> findAll(String stringFilter) {
		ArrayList<Movie> arrayList = new ArrayList<>();
		for (Movie contact : movies.values()) {
			System.out.println(contact.toString());

			try {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(contact.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(MovieService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Movie>() {

			@Override
			public int compare(Movie o1, Movie o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		return arrayList;
	}

	/**
	 * Finds all Movie's that match given filter and limits the resultset.
	 *
	 * @param stringFilter
	 *            filter that returned objects should match or null/empty string
	 *            if all objects should be returned.
	 * @param start
	 *            the index of first result
	 * @param maxresults
	 *            maximum result count
	 * @return list a Movie objects
	 */
	public synchronized List<Movie> findAll(String stringFilter, int start, int maxresults) {
		ArrayList<Movie> arrayList = new ArrayList<>();
		for (Movie contact : movies.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(contact.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(MovieService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Movie>() {

			@Override
			public int compare(Movie o1, Movie o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		int end = start + maxresults;
		if (end > arrayList.size()) {
			end = arrayList.size();
		}
		return arrayList.subList(start, end);
	}

	/**
	 * @return the amount of all customers in the system
	 */
	public synchronized long count() {
		return movies.size();
	}

	/**
	 * Deletes a customer from a system
	 *
	 * @param value
	 *            the Movie to be deleted
	 */
	public synchronized void delete(Movie value) {
		movies.remove(value.getId());
	}

	/**
	 * Persists or updates customer in the system. Also assigns an identifier
	 * for new Movie instances.
	 *
	 * @param entry
	 */
	public synchronized void save(Movie entry) {
		if (entry == null) {
			LOGGER.log(Level.SEVERE,
					"Movie is null. Are you sure you have connected your form to the application as described in tutorial chapter 7?");
			return;
		}
		if (entry.getId() == null) {
			entry.setId(nextId++);
		}
		try {
			entry = (Movie) entry.clone();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		movies.put(entry.getId(), entry);
	}

	/**
	 * Sample data generation
	 */
	public void ensureTestData() {
		if (findAll().isEmpty()) {
			Dummy dummy = new Dummy();
			final String[] names = dummy.moviesExample();
			final Double[] prices = dummy.pricesExample();
			final Integer[] durations = dummy.durationsExample();
			final Integer[] imdb = dummy.imdbExample();
			Random r = new Random(0);
			for (int i = 0; i < names.length; i++) {
				Movie m = new Movie();
//				Image image = new Image();
//				image.setSource(new ExternalResource("http://www.maski.gov.tr/Maski.PNG"));
				m.setImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRpUYR0TnID7D32oYEhhJqlMAnaE1kuOwsXyVfMoDldeoELTA_Z");
				m.setMovie(names[i]);
				m.setBasePrice(prices[i]);
				m.setDuration(durations[i]);
				m.setImdb(imdb[i]);
				m.setPeriodicity(i);
				m.setTimeOut(i*5);
				Calendar cal = Calendar.getInstance();
				int daysOld = 0 - r.nextInt(365 * 15 + 365 * 60);
				cal.add(Calendar.DAY_OF_MONTH, daysOld);
				m.setRollingStart(cal.getTime());
				cal.add(Calendar.DAY_OF_MONTH, daysOld + 15);
				m.setRollingEnd(cal.getTime());
				save(m);
			}
		}
	}

}
