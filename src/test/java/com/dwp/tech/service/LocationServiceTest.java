package com.dwp.tech.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dwp.tech.model.Coordinates;
import com.dwp.tech.model.Locations;
import com.dwp.tech.model.User;
import com.dwp.tech.utility.LocationUtils;


public class LocationServiceTest {

	@Mock
	UserService userService;
	
	@Mock
	LocationUtils distanceCalculatorUtils;
	
	@InjectMocks
	LocationService locationService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@DisplayName("Tests getUserWithinDistance() with valid results ")
	public void test_getUserWithinDistance_Valid() {
		String locationName = "LONDON";

		Double latitude = 51.509865;
		Double logitude = -0.118092;
		Double distance = 50.0;

		User user1 = new User(1, "A_FIRST", "A_LAST", "a@a.com", "123.123.123.123", latitude, logitude);
		User user2 = new User(1, "B_FIRST", "B_LAST", "b@b.com", "231.231.231.231", latitude, logitude);

		List<User> mockUserListWithinCity = new ArrayList<User>();
		mockUserListWithinCity.add(user1);
		when(userService.getUsersWithinCity(locationName)).thenReturn(mockUserListWithinCity);

		List<User> mockAllUserList = new ArrayList<User>();		
		mockAllUserList.add(user2);
		when(userService.getUsers()).thenReturn(mockAllUserList);

		List<User> users = locationService.getUserWithinDistance(locationName, latitude, logitude, distance);

		// asserting that response has both the users in it.
		assertThat(users).contains(user1, user2);
		//asserting unique users are present
		assertEquals(users.size(), 2);
	}
	
	@Test
	@DisplayName("Tests getUserWithinDistance() with valid duplicate users ")
	public void test_getUserWithinDistance_duplicate() {
		String locationName = "LONDON";

		Double latitude = 51.509865;
		Double logitude = -0.118092;
		Double distance = 50.0;

		User user1 = new User(1, "A_FIRST", "A_LAST", "a@a.com", "123.123.123.123", latitude, logitude);
		User user2 = new User(1, "B_FIRST", "B_LAST", "b@b.com", "231.231.231.231", latitude, logitude);

		List<User> mockUserListWithinCity = new ArrayList<User>();
		mockUserListWithinCity.add(user1);
		mockUserListWithinCity.add(user2);
		when(userService.getUsersWithinCity(locationName)).thenReturn(mockUserListWithinCity);

		List<User> mockAllUserList = new ArrayList<User>();
		mockAllUserList.add(user1);
		mockAllUserList.add(user2);
		when(userService.getUsers()).thenReturn(mockAllUserList);

		List<User> users = locationService.getUserWithinDistance(locationName, latitude, logitude, distance);

		// asserting that response has both the users in it.
		assertThat(users).contains(user1, user2);
		//asserting duplicate users are removed
		assertEquals(users.size(), 2);
	}
	
	@Test
	@DisplayName("Tests getUserWithinDistance() with distance more than passed distance")
	public void test_getUserWithinDistance_distancebtwCord_MoreThanDistance() {
		String locationName = Locations.LONDON.getName();

		Double latitude = 51.509865;
		Double logitude = -0.118092;
		Double distance = 50.0;

		User user1 = new User(1, "A_FIRST", "A_LAST", "a@a.com", "123.123.123.123", latitude, logitude);
		
		Coordinates codinateUser2 = new Coordinates(latitude,logitude);
		User user2 = new User(1, "B_FIRST", "B_LAST", "b@b.com", "231.231.231.231", codinateUser2.getLatitude(), codinateUser2.getLongitude());

		List<User> mockUserListWithinCity = new ArrayList<User>();
		mockUserListWithinCity.add(user1);
		when(userService.getUsersWithinCity(locationName)).thenReturn(mockUserListWithinCity);

		List<User> mockAllUserList = new ArrayList<User>();
		mockAllUserList.add(user2);
		when(userService.getUsers()).thenReturn(mockAllUserList);
		
		//Distance between cordinates is more than distance ie 50.0 
		when(distanceCalculatorUtils.calculateDistanceBetweenPoints(Locations.LONDON.getCordinates(),codinateUser2)).thenReturn(60.0);
		List<User> users = locationService.getUserWithinDistance(locationName, latitude, logitude, distance);

		// asserting that response has the users with less distance will be included.
		assertThat(users).contains(user1);
		//asserting number of users
		assertEquals(users.size(), 1);
	}
	
	@Test
	@DisplayName("Tests getUserWithinDistance() with distance less than passed distance")
	public void test_getUserWithinDistance_distancebtwCord_LessThanDistance() {
		String locationName = Locations.LONDON.getName();

		Double latitude = 51.509865;
		Double logitude = -0.118092;
		Double distance = 70.0;

		User user1 = new User(1, "A_FIRST", "A_LAST", "a@a.com", "123.123.123.123", latitude, logitude);
		
		Coordinates codinateUser2 = new Coordinates(latitude,logitude);
		User user2 = new User(1, "B_FIRST", "B_LAST", "b@b.com", "231.231.231.231", codinateUser2.getLatitude(), codinateUser2.getLongitude());

		List<User> mockUserListWithinCity = new ArrayList<User>();
		mockUserListWithinCity.add(user1);
		when(userService.getUsersWithinCity(locationName)).thenReturn(mockUserListWithinCity);

		List<User> mockAllUserList = new ArrayList<User>();
		mockAllUserList.add(user2);
		when(userService.getUsers()).thenReturn(mockAllUserList);
		
		//Distance between cordinates is more than distance ie 50.0 
		when(distanceCalculatorUtils.calculateDistanceBetweenPoints(Locations.LONDON.getCordinates(),codinateUser2)).thenReturn(60.0);
		List<User> users = locationService.getUserWithinDistance(locationName, latitude, logitude, distance);

		// asserting that response has the users with less distance will be included.
		assertThat(users).contains(user1,user2);
		//asserting number of users
		assertEquals(users.size(), 2);
	}
	
	@Test
	@DisplayName("Tests getUserWithinDistance() with invalid location")
	public void test_getUserWithin_invalid_location() {
		//String locationName = Locations.LONDON.getName();
		String locationName = "DONDON";

		Double latitude = 51.509865;
		Double logitude = -0.118092;
		Double distance = 70.0;

		Coordinates codinateUser2 = new Coordinates(latitude,logitude);
		User user2 = new User(2, "B_FIRST", "B_LAST", "b@b.com", "231.231.231.231", codinateUser2.getLatitude(), codinateUser2.getLongitude());

		//as city is in-valid so remore service doesn't return any user for the city
		List<User> mockUserListWithinCity = new ArrayList<User>();
		
		when(userService.getUsersWithinCity(locationName)).thenReturn(mockUserListWithinCity);

		List<User> mockAllUserList = new ArrayList<User>();
		mockAllUserList.add(user2);
		when(userService.getUsers()).thenReturn(mockAllUserList);

		List<User> users = locationService.getUserWithinDistance(locationName, latitude, logitude, distance);

		//asserting number of users
		assertEquals(users.size(), 0);
	}

}
