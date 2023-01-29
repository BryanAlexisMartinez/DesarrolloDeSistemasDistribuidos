package com.adee.samples.objectmapper.model;

import java.util.Date;

public class Info {

	private Country country;
	private Date now;

	public Info(Country country, Date now) {
		this.country = country;
		this.now = now;
	}

	public Info() {

	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}
}
