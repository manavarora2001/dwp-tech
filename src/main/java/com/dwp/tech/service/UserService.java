package com.dwp.tech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dwp.tech.exception.InternalException;
import com.dwp.tech.model.User;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	@Value("${user.api.url}")
	private String userUrl;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Gets all the users by calling https://bpdts-test-app.herokuapp.com/users 
	 * @return
	 */
	public List<User> getUsers() {
		log.info("Getting all users");
		try {
			ResponseEntity<List<User>> usersResponse =
			        restTemplate.exchange(userUrl + "/users",
			                    HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
			            });
			log.info(String.format("%s all users returned from External User Service", usersResponse.getBody().size()));
			return usersResponse.getBody();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new InternalException("Error occured while call to User service, please try later");
		}
	}

	/**
	 * Gets users by calling https://bpdts-test-app.herokuapp.com/city/London/users
	 * 
	 * @param city
	 * @return
	 */
	public List<User> getUsersWithinCity(String city) {
		try {
			log.info("Getting Users from City [" + city + "]");
			ResponseEntity<List<User>> usersResponse =
			        restTemplate.exchange(userUrl +  "/city/" + city + "/users",
			                    HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
			            });
			log.info(String.format("%s users returned for city from External User Service: %s", usersResponse.getBody().size(), city));
			return usersResponse.getBody();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new InternalException("Error occured while call to Users for city service, please try later");
		}
	}

}
