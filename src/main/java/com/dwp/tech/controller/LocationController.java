package com.dwp.tech.controller;

import java.util.List;

import javax.validation.constraints.PositiveOrZero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dwp.tech.exception.InvalidRequest;
import com.dwp.tech.model.Locations;
import com.dwp.tech.model.User;
import com.dwp.tech.service.LocationService;
import com.dwp.tech.utility.LocationUtils;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/location/")
@Slf4j
@Validated
public class LocationController {

	@Autowired
	LocationService locationService;


	/**
	 * Returns Users whose current coordinates matches the location and the distance passed
	 * @param location
	 * @param distance
	 * @return
	 */
	@GetMapping(value = "users")
	public List<User> getUsersWithinDistance(@RequestParam(defaultValue = "LONDON") String location,
			@RequestParam(defaultValue = "50.0") @PositiveOrZero Double distance) {

		try {
			log.info(String.format("LocationController: location: %s , Distance: %s", location, distance));
			Locations cityLocation = getLocation(location); 
			
			String locationName = cityLocation != null ? cityLocation.getName() : location;
			double locationLat = cityLocation != null ? cityLocation.getCordinates().getLatitude() : 0.0;
			double locationLong = cityLocation != null ? cityLocation.getCordinates().getLongitude() : 0.0;
					
			return locationService.getUserWithinDistance(locationName, locationLat,locationLong, distance);
		} catch (Exception ex) {
			throw new InvalidRequest("Please enter valid city and distance");
		}
	}
	
	/**
	 * Gets the valid location for the location passed in the parameter
	 * @param location
	 * @return
	 */
	private Locations getLocation(String location) {
		Locations loc = null;
		if(LocationUtils.validLocation(location)) {
			loc= Locations.valueOf(location.toUpperCase());
		}
		return loc;
	}

}
