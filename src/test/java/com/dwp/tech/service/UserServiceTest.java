package com.dwp.tech.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.dwp.tech.exception.InternalException;
import com.dwp.tech.model.User;

public class UserServiceTest {

	@Value("${user.api.url}")
	String userUrl;

	@Mock
	RestTemplate mockTemplate;

	@InjectMocks
	UserService userService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@DisplayName("Tests for valid response, with users")
	public void test_getUsers_Valid_response() {

		User user1 = new User(1, "A_FIRST", "A_LAST", "a@a.com", "123.123.123.123", 51.509865, -0.118092);
		User user2 = new User(1, "B_FIRST", "B_LAST", "b@b.com", "231.231.231.231", 52.509865, -0.128092);
		List<User> mockUserList = new ArrayList<User>();
		mockUserList.add(user1);
		mockUserList.add(user2);

		ResponseEntity<List<User>> usersResponse = new ResponseEntity<List<User>>(mockUserList, HttpStatus.OK);
		Mockito.when(mockTemplate.exchange(userUrl + "/users", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<User>>() {
				})).thenReturn(usersResponse);
		List<User> users = userService.getUsers();

		// asserting that response has both the users in it.
		assertThat(users).contains(user1, user2);
	}

	@Test
	@DisplayName("Tests for valid response, without users")
	public void test_getUsers_NoUserReturnedValid_response() {

		List<User> mockUserList = new ArrayList<User>();

		ResponseEntity<List<User>> usersResponse = new ResponseEntity<List<User>>(mockUserList, HttpStatus.OK);
		Mockito.when(mockTemplate.exchange(userUrl + "/users", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<User>>() {
				})).thenReturn(usersResponse);
		List<User> users = userService.getUsers();

		// asserting that response has both the users in it.
		assertThat(users).isEmpty();
	}

	@Test
	@DisplayName("Testing the exception and message if external User service is down")
	public void test_Exception_for_GetUsers() {

		Mockito.when(mockTemplate.exchange(userUrl + "/users", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<User>>() {
				})).thenReturn(null);

		Exception exception = assertThrows(InternalException.class, () -> {
			userService.getUsers();
		});

		String expectedMessage = "Error occured while call to User service, please try later";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	@DisplayName("Tests for users for a city ")
	public void test_getUsers_for_city() {

		User user1 = new User(1, "A_FIRST", "A_LAST", "a@a.com", "123.123.123.123", 51.509865, -0.118092);
		User user2 = new User(1, "B_FIRST", "B_LAST", "b@b.com", "231.231.231.231", 52.509865, -0.128092);
		List<User> mockUserList = new ArrayList<User>();
		mockUserList.add(user1);
		mockUserList.add(user2);
		String city = "London";

		ResponseEntity<List<User>> usersResponse = new ResponseEntity<List<User>>(mockUserList, HttpStatus.OK);
		Mockito.when(mockTemplate.exchange(userUrl + "/city/" + city + "/users", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<User>>() {
				})).thenReturn(usersResponse);
		List<User> users = userService.getUsersWithinCity(city);

		// asserting that response has both the users in it.
		assertThat(users).contains(user1, user2);
	}

	@Test
	@DisplayName("Tests for valid response, without users for a city")
	public void test_getUsers_No_User_For_City() {

		List<User> mockUserList = new ArrayList<User>();

		ResponseEntity<List<User>> usersResponse = new ResponseEntity<List<User>>(mockUserList, HttpStatus.OK);
		String city = "London";
		Mockito.when(mockTemplate.exchange(userUrl + "/city/" + city + "/users", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<User>>() {
				})).thenReturn(usersResponse);
		List<User> users = userService.getUsersWithinCity(city);

		// asserting that response has both the users in it.
		assertThat(users).isEmpty();
	}

	@Test
	@DisplayName("Testing the exception and message if external User service for city is down")
	public void test_Exception_for_getUsers_for_city() {
		String city = "London";
		Mockito.when(mockTemplate.exchange(userUrl + "/city/" + city + "/users", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<User>>() {
				})).thenReturn(null);

		Exception exception = assertThrows(InternalException.class, () -> {
			userService.getUsersWithinCity(city);
		});

		String expectedMessage = "Error occured while call to Users for city service, please try later";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

}
