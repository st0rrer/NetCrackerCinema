package com.netcracker.cinema.web.adminModifyMovie;

import com.vaadin.server.ExternalResource;

import java.io.Serializable;
import java.util.Date;

/**
 * A entity object, like in any other Java application. In a typical real world
 * application this could for example be a JPA entity.
 */
@SuppressWarnings("serial")
public class Movie implements Serializable, Cloneable {

	private Long id;

	private String image;

	private String movie = "";

	private Integer duration = 0;

	private Integer imdb = 0;

	private Double basePrice = 0.0;

	private Integer periodicity = 0;

	private Integer timeOut = 0;

	private Date rollingStart;

	private Date rollingEnd;

	private String description = "";

	private ExternalResource hello;

	public ExternalResource getHello() {
		return new ExternalResource(getImage());
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMovie() {
		return movie;
	}

	public void setMovie(String movie) {
		this.movie = movie;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getImdb() {
		return imdb;
	}

	public void setImdb(Integer imdb) {
		this.imdb = imdb;
	}

	public Double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}

	public Integer getPeriodicity() {
		return periodicity;
	}

	public void setPeriodicity(Integer periodicity) {
		this.periodicity = periodicity;
	}

	public Integer getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Integer timeOut) {
		this.timeOut = timeOut;
	}

	public Date getRollingStart() {
		return rollingStart;
	}

	public void setRollingStart(Date rollingSart) {
		this.rollingStart = rollingSart;
	}

	public Date getRollingEnd() {
		return rollingEnd;
	}

	public void setRollingEnd(Date rollingEnd) {
		this.rollingEnd = rollingEnd;
	}


	public boolean isPersisted() {
		return id != null;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (this.id == null) {
			return false;
		}

		if (obj instanceof Movie && obj.getClass().equals(getClass())) {
			return this.id.equals(((Movie) obj).id);
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 43 * hash + (id == null ? 0 : id.hashCode());
		return hash;
	}

	@Override
	public Movie clone() throws CloneNotSupportedException {
		return (Movie) super.clone();
	}

	@Override
	public String toString() {
		return movie;
	}
}
