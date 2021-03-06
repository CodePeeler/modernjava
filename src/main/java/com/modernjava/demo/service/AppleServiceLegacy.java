package com.modernjava.demo.service;

import java.util.List;

import com.modernjava.demo.model.Apple;

public interface AppleServiceLegacy {

	/*
	 * Old school java.
	 */
	public List<Apple> sortApples();

	/*
	 * Old school java.
	 */
	public List<Apple> filterApples(String filterType);

	/*
	 * The Java 8 way.
	 */
	public List<Apple> sortApplesJ8();

	/*
	 * The Java 8 way.
	 */
	public List<Apple> filterApplesJ8(String filterType);
}
