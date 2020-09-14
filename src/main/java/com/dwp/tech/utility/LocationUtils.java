package com.dwp.tech.utility;

import org.springframework.stereotype.Component;

import com.dwp.tech.model.Coordinates;
import com.dwp.tech.model.Locations;

@Component
public class LocationUtils {
	
	/**
	 * Uses the Haversine formula to calculate the distance
	 * 
	 * Haversine formula: a = sin²(Δφ/2) + cos φ1 ⋅ cos φ2 ⋅ sin²(Δλ/2) 
	 * 					  c = 2 atan2( √a, √(1−a) ) 
	 * 					  d = R ⋅ c                      (where φ is latitude, λ is longitude, R is earth’s radius)
	 * 
	 * 
	 * @param pos1
	 * @param pos2
	 * 
	 * @return - distance between the positions passed
	 */
    public double calculateDistanceBetweenPoints(Coordinates pos1, Coordinates pos2) {

        if ((pos1.getLatitude() == pos2.getLatitude()) && (pos1.getLongitude() == pos2.getLongitude())) {
            return 0;
        } else {

            double long1 = Math.toRadians(pos1.getLongitude());
            double lat1 = Math.toRadians(pos1.getLatitude());

            double long2 = Math.toRadians(pos2.getLongitude());
            double lat2 = Math.toRadians(pos2.getLatitude());

            // Haversine formula
            double longDist = long2 - long1;
            double latDist = lat2 - lat1;
            double a = Math.pow(Math.sin(latDist / 2), 2)
                    + Math.cos(lat1) * Math.cos(lat2)
                    * Math.pow(Math.sin(longDist / 2), 2);

            double c = 2* Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

            // calculate the result
            return c * DWPConstants.EARTH_RADIUS;
        }
    }

    /**
     * Checks of the location passed is present in the application , hence valid or not
     * 
     * @param location
     * @return
     */
	public static boolean validLocation(String location) {
		for (Locations loc : Locations.values()) {
			if (loc.name().equalsIgnoreCase(location)) {
				return true;
			}
		}
		return false;
	}

}
