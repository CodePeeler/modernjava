package com.modernjava.demo.service.Impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import com.modernjava.demo.model.Apple;
import com.modernjava.demo.model.AppleColor;
import com.modernjava.demo.service.AppleService;

@Service
public class AppleServiceImpl implements AppleService {

	/*
	 * This is how we use to sort apple lists.
	 */
	public String sortApples() {
		List<Apple> apples = addApples();
		print("BEFORE", apples);

		Collections.sort(apples, new Comparator<Apple>() {
			@Override
			public int compare(Apple a1, Apple a2) {
				return a1.getWeight()
					.compareTo(a2.getWeight());
			}
		});
		print("AFTER", apples);
		return "Apples sorted by weight";
	}

	/*
	 * This is the way... this is Java 8.
	 */
	public String sortApplesJ8() {
		List<Apple> apples = addApples();
		print("BEFORE", apples);

		apples.sort(Comparator.comparing(Apple::getWeight));

		print("AFTER", apples);
		return "Apples sorted by weight";
	}

	/*
	 * The following two methods can be simplified with
	 * the use of Java 8 Predicates. See filterApplesJ8
	 * below.
	 */
	public String filterApplesByColor(AppleColor appleColor) {
		List<Apple> apples = addApples();
		List<Apple> result = new ArrayList<>();

		for(Apple apple: apples) {
			if(appleColor.equals(apple.getColor())) {
				result.add(apple);
			}
		}

		print("Filter by Color", result);
		return "Apples filtered by color";
	}

	public String filterApplesByWeight() {
		List<Apple> apples = addApples();
		List<Apple> result = new ArrayList<>();

		for(Apple apple: apples) {
			if(apple.getWeight() > Apple.HEAVY_APPLE ) {
				result.add(apple);
			}
		}

		print("Filter by Weight", result);
		return "Apples filtered by minimum weight";
	}

	/*
	 * Reduce the redundancy of the above two methods
	 * with Java 8
	 */
	public String filterApplesJ8(Predicate<Apple> predicate) {
		List<Apple> apples = addApples();
		List<Apple> result = new ArrayList<>();

		for(Apple apple: apples) {
			if(predicate.test(apple)) {
				result.add(apple);
			}
		}

		print("JAVA 8 FILTERING", result);
		return "Apples have been filtered the J8 way!";
	}

	/*
	 * Toy data... how do you like them apples!
	 */
	private List<Apple> addApples() {
		List<Apple> apples = Arrays.asList(
			new Apple(AppleColor.RED, 38.1 ),
			new Apple(AppleColor.GREEN, 32.3),
			new Apple(AppleColor.GREEN, 36.7)
		);
		return apples;
	}

	/*
	 * Print to console helper.
	 */
	private void print(String heading, List<Apple> apples) {
		System.out.println("**** "+heading+" ****");
		
		apples.stream()
			.map(Apple::toString)
		.forEach(System.out::println);

	}
}
