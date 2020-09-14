package com.dwp.tech.model;

import lombok.Getter;

/**
 * Class to represent the locations used in the application
 */
@Getter
public enum Locations {

    //Location cordinates ( lat, long)
    LONDON("London", new Coordinates(51.509865, -0.118092)),
    //Other cities like below can be added later 
	BIRMINGHAM("Birmingham", new Coordinates(52.489471, -1.898575)),
	SHEFFIELD("Sheffield", new Coordinates(53.383331, -1.466667)),
	MANCHESTER("Manchester", new Coordinates(53.483959, -2.244644));

    private final String name;
    private final Coordinates cordinates;

    Locations(String name, Coordinates coordinates) {
        this.name = name;
        this.cordinates = coordinates;
    }

  
}
