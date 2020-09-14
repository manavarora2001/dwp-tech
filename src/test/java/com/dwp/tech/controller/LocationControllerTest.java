package com.dwp.tech.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dwp.tech.model.User;
import com.dwp.tech.service.LocationService;

@RunWith(SpringRunner.class)
@WebMvcTest(LocationController.class)
public class LocationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LocationService locationService;

	@Test
	@DisplayName("Test controller is valid")
	public void test_Controller_Success() throws Exception {

		Double latitude = 51.509865;
		Double logitude = -0.118092;

		User user1 = new User(1, "A_FIRST", "A_LAST", "a@a.com", "123.123.123.123", latitude, logitude);
		User user2 = new User(1, "B_FIRST", "B_LAST", "b@b.com", "231.231.231.231", latitude, logitude);

		List<User> mockUserList = new ArrayList<User>();
		mockUserList.add(user1);
		mockUserList.add(user2);

		Mockito.when(locationService.getUserWithinDistance(any(String.class), any(Double.class), any(Double.class),
				any(Double.class))).thenReturn(mockUserList);

		// perform GET request for the controller
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/location/users"))
				.andExpect(status().isOk())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(notNullValue())));

	}

	@Test
	@DisplayName("Test controller with valid request parameters")
	public void test_Controller_Valid_Param() throws Exception {

		Double latitude = 51.509865;
		Double logitude = -0.118092;

		User user1 = new User(1, "A_FIRST", "A_LAST", "a@a.com", "123.123.123.123", latitude, logitude);
		User user2 = new User(2, "B_FIRST", "B_LAST", "b@b.com", "231.231.231.231", latitude, logitude);

		List<User> mockUserList = new ArrayList<User>();
		mockUserList.add(user1);
		mockUserList.add(user2);

		Mockito.when(locationService.getUserWithinDistance(any(String.class), any(Double.class), any(Double.class),
				any(Double.class))).thenReturn(mockUserList);

		// perform GET request for the controller
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/location/users")
				.param("location", "london")
				.param("distance", "10"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(notNullValue())));
	}

	@Test
	@DisplayName("Test controller with in valid request parameters")
	public void test_Controller_Invalid_Param() throws Exception {

		Double latitude = 51.509865;
		Double logitude = -0.118092;

		User user1 = new User(1, "A_FIRST", "A_LAST", "a@a.com", "123.123.123.123", latitude, logitude);
		User user2 = new User(1, "B_FIRST", "B_LAST", "b@b.com", "231.231.231.231", latitude, logitude);

		List<User> mockUserList = new ArrayList<User>();
		mockUserList.add(user1);
		mockUserList.add(user2);

		// perform GET request for the controller
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/location/users").param("location", "london").param("distance", "-1"))
				.andDo(print())
				.andExpect(status().is5xxServerError())
				.andExpect(jsonPath("$.description").value("getUsersWithinDistance.distance: must be greater than or equal to 0"));		
	}

	@Test
	@DisplayName("Test controller with valid request parameters but where data not present in the users service")
	public void test_Controller_Valid_Location_No_Data() throws Exception {
		List<User> mockUserList = new ArrayList<User>();
		Mockito.when(locationService.getUserWithinDistance(any(String.class), any(Double.class), any(Double.class),
				any(Double.class))).thenReturn(mockUserList);

		// perform GET request for the controller
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/location/users").param("location", "dondon"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));

	}

}
