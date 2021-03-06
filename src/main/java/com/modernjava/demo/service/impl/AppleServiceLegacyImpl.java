package com.modernjava.demo.service.impl;

import static com.modernjava.demo.model.Apple.HEAVY_APPLE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modernjava.demo.model.Apple;
import com.modernjava.demo.model.AppleColor;
import com.modernjava.demo.repository.AppleRepository;
import com.modernjava.demo.service.AppleServiceLegacy;

@Service
public class AppleServiceLegacyImpl implements AppleServiceLegacy {
	@Autowired
	private AppleRepository appleRepository;

	private static final Logger logger = LoggerFactory
		.getLogger(AppleServiceLegacyImpl.class);

	/*
	 * Old school java, using an anonymous class to
	 * sort them apples.
	 */
	public List<Apple> sortApples() {
		List<Apple> apples = appleRepository.findAll();
		logger.info("BEFORE: {}", apples);

		Collections.sort(apples, new Comparator<Apple>() {
			@Override
			public int compare(Apple a1, Apple a2) {
				return a1.getWeight()
					.compareTo(a2.getWeight());
			}
		});
		logger.info("AFTER: {}", apples);
		return apples;
	}

	/*
	 * From Java 8 List comes with a sort method.
	 */
	public List<Apple> sortApplesJ8() {
		List<Apple> apples = appleRepository.findAll();
		logger.info("BEFORE: {}", apples);

		apples.sort(Comparator.comparing(Apple::getWeight));

		logger.info("AFTER: {}", apples);
		return apples;
	}

	public List<Apple> filterApples(String filterType) {
		if (filterType.equalsIgnoreCase("byColor")) {
			return filterApplesByColor(AppleColor.GREEN);

		} else if (filterType.equalsIgnoreCase("byWeight")) {
			return filterApplesByWeight();
		} else {
			return new ArrayList<>();
		}
	}

	/*
	 * The following two methods can be simplified with
	 * the use of Java 8 Predicates. See filterApplesJ8Helper
	 * below.
	 */
	private List<Apple> filterApplesByColor(AppleColor appleColor) {
		List<Apple> apples = appleRepository.findAll();
		List<Apple> result = new ArrayList<>();

		for(Apple apple: apples) {
			if(appleColor.equals(apple.getColor())) {
				result.add(apple);
			}
		}
		logger.info("Filter out red apples: {}", result);
		return result;
	}

	private List<Apple> filterApplesByWeight() {
		List<Apple> apples = appleRepository.findAll();
		List<Apple> result = new ArrayList<>();

		for(Apple apple: apples) {
			if(apple.getWeight() < HEAVY_APPLE) {
				result.add(apple);
			}
		}
		logger.info("Filter out heavy apples: {}", result);
		return result;
	}

	/*
	 * Use lambdas to pass anonymous function parameter.
	 * In this case the function acts as a predicate for
	 * the filterApplesJ8Helper
	 */
	public List<Apple> filterApplesJ8(String filterType) {
		List<Apple> apples = appleRepository.findAll();

		if (filterType.equalsIgnoreCase("byColor")) {
			return filterApplesJ8Helper(
				a -> AppleColor.GREEN.equals
					(a.getColor()), apples);
		/*
		 * An even better alternative is to use the Streams API and
		 * dispense with the need for any additional helper methods.
		 */
		} else if (filterType.equalsIgnoreCase("byWeight")) {
			return apples.stream()
					.filter(a -> a.getWeight() < HEAVY_APPLE)
					.collect(Collectors.toList());
		} else {
			return new ArrayList<>();
		}
	}

	/*
	 * Reduce the redundancy from filterApplesByColor and
	 * filterApplesByWeight with the following...
	 * the Java 8 way.
	 */
	private <T> List<T> filterApplesJ8Helper(
		Predicate<T> predicate, List<T> list) {

		List<T> result = new ArrayList<>();

		for(T t: list) {
			if(predicate.test(t)) {
				result.add(t);
			}
		}
		logger.info("JAVA 8 FILTERING: {}", result);
		return result;
	}
}
