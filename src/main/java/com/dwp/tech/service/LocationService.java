package com.dwp.tech.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dwp.tech.model.Coordinates;
import com.dwp.tech.model.User;
import com.dwp.tech.utility.LocationUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Location Service 
 *
 *
 */
@Service
@Slf4j
public class LocationService {

	@Autowired
	UserService userService;
	
	@Autowired
	LocationUtils locationUtils;

	/**
	 * Returns the Users who are within the passed distance of the latitude, longitude 
	 * passed in addition to the users within city
	 *  
	 * @param cityName - city name to find the users
	 * @param latitude- latitude of the city
	 * @param logitude- longitude of the city
	 * @param distance- within radius of this distance from latitude and longitude passed find the users
	 * @return Users - list of the users
	 */
	public List<User> getUserWithinDistance(String cityName, Double latitude, Double logitude, Double distance) {

		Set<User> users = new HashSet<>();
		log.info(String.format("User location : %s", cityName));

		// Gets all the users within the location name passed
		users.addAll(userService.getUsersWithinCity(cityName));
		
		log.info(String.format("Finding user within %s of (%s,%s)", distance, latitude, logitude));

		Coordinates coords = new Coordinates(latitude, logitude);
		
		// Get all users
		List<User> allUsers = userService.getUsers();
		
		//if the location is valid then only call the remote service & calculate the distance between the coordinates.
		if (LocationUtils.validLocation(cityName)) {
			users.addAll(getUsersWithinRangeOfCoords(distance, coords, allUsers));
		}
		log.info(String.format("Total %s number of users returned for city: %s within %s miles", users.size(), cityName, distance));

		return new ArrayList<>(users);
	}

	/**
	 * Returns the users who lies within the range of the distance passed.
	 * 
	 * @param distance - Distance within which users should be considered
	 * @param cityCord
	 * @param allUsers
	 * @return
	 */
	private Set<User> getUsersWithinRangeOfCoords(double distance, Coordinates cityCord, List<User> allUsers) {

		Set<User> usersWithinRange = new HashSet<>();

		for (User user : allUsers) {

			// Calculate the distance between the user and the Cordinates provided
			Coordinates userCoords = new Coordinates(user.getLatitude(), user.getLongitude());

			// Apply Haversine formula
			BiFunction<Coordinates, Coordinates, Double> formula = locationUtils::calculateDistanceBetweenPoints;
			double distanceBtwCordinates = formula.apply(userCoords, cityCord);

			// If the user is within range add it to the list
			if (distanceBtwCordinates <= distance) {
				// user.setDistanceFromCoords(distanceFromCoords);
				usersWithinRange.add(user);
			}
		}

		log.info(String.format("%s users found within distance %s", usersWithinRange.size() , distance));

		return usersWithinRange;
	}

}
