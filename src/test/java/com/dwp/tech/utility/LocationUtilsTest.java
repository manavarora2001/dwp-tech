package com.dwp.tech.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.dwp.tech.model.Coordinates;
import com.dwp.tech.model.Locations;

public class LocationUtilsTest {
	
	@InjectMocks
	LocationUtils locationUtils;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	
	@Test
	@DisplayName("Tests the distance calculator for less than 50 miles")
	public void testDistance_lessthan_50miles() {
		
		//London Cordinates
		Coordinates cord1 = Locations.LONDON.getCordinates();
		
		//Cordinate of position less than 50 miles of london 
		Coordinates cord2 = new Coordinates(51.482836, -0.388206);
		
		double distance = locationUtils.calculateDistanceBetweenPoints(cord1,cord2);
		//assertEquals(11.7682182836405d, distance);
		assertEquals(11.7682182836405, distance, 0.001, "Distance almost equal");
	}
	
	
	@Test
	@DisplayName("Tests the distance calculator for more than 50 miles")
	public void testDistance_morethan_50miles() {
		
		//London Cordinates
		Coordinates cord1 = Locations.LONDON.getCordinates();
		
		//Cordinate of position more than 50 miles of london 
		Coordinates cord2 = new Coordinates(56.490671, -4.202646);
		
		double distance = locationUtils.calculateDistanceBetweenPoints(cord1,cord2);
		//assertEquals(11.7682182836405d, distance);
		assertEquals(381.875, distance, 0.001, "Distance almost equal");
	}
	
	
	@Test
	@DisplayName("Tests the distance calculator for more than 50 miles")
	public void testDistance_zero() {
		
		//London Cordinates
		Coordinates cord1 = Locations.LONDON.getCordinates();
		
		//Cordinate of position more than 50 miles of london 
		Coordinates cord2 = Locations.LONDON.getCordinates();
		
		double distance = locationUtils.calculateDistanceBetweenPoints(cord1,cord2);
		//assertEquals(11.7682182836405d, distance);
		assertEquals(0.0, distance, 0.001, "Distance almost equal");
	}
}
