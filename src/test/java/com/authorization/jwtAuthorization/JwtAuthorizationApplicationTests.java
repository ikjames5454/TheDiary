package com.authorization.jwtAuthorization;

import com.authorization.jwtAuthorization.dto.CountryUserCount;
import com.authorization.jwtAuthorization.dto.RequestResponse;
import com.authorization.jwtAuthorization.dto.requestDto.CreateDiaryRequest;
import com.authorization.jwtAuthorization.entity.Users;
import com.authorization.jwtAuthorization.repository.UsersRepo;
import com.authorization.jwtAuthorization.service.UserManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtAuthorizationApplicationTests {

	@Autowired
	private UserManagementService userManagementService;

	@Autowired
	private UsersRepo usersRepo;
	private RequestResponse requestResponse;

	private RequestResponse loginResponse;

	@BeforeEach
	public void startWith(){

		requestResponse = new RequestResponse();
		loginResponse = new RequestResponse();

	}

	@BeforeEach
	public void tearDown(){
		usersRepo.deleteAll();
	}

	@Test
	public void registerUser() {
		requestResponse.setName("Moyin oluwakpo");
		requestResponse.setEmail("IkennaJames@gmail.com");
		requestResponse.setPassword("123Password");
		requestResponse.setState("Lagos");
		requestResponse.setCountry("Nigeria");
		requestResponse.setRole("USER");
		RequestResponse response = userManagementService.register(requestResponse);
		System.out.println(response);
        assertNotNull(response);

	}

	@Test
	public void login(){
		requestResponse.setName("Moyin oluwakpo");
		requestResponse.setEmail("IkennaJames@gmail.com");
		requestResponse.setPassword("123Password");
		requestResponse.setState("Lagos");
		requestResponse.setCountry("Nigeria");
		requestResponse.setRole("USER");
		RequestResponse response = userManagementService.register(requestResponse);
		System.out.println(response);
		assertNotNull(response);

		loginResponse.setEmail("IkennaJames@gmail.com");
		loginResponse.setPassword("123Password");
		RequestResponse login = userManagementService.login(loginResponse);
		System.out.println(login);
		assertNotNull(login);


	}

	@Test
	public void refreshToken(){
		requestResponse.setName("Moyin oluwakpo");
		requestResponse.setEmail("IkennaJames@gmail.com");
		requestResponse.setPassword("123Password");
		requestResponse.setState("Lagos");
		requestResponse.setCountry("Nigeria");
		requestResponse.setRole("USER");
		RequestResponse response = userManagementService.register(requestResponse);
		System.out.println(response);
		assertNotNull(response);

		loginResponse.setEmail("IkennaJames@gmail.com");
		loginResponse.setPassword("123Password");
		RequestResponse login = userManagementService.login(loginResponse);
		System.out.println(login);
		assertNotNull(login);

		RequestResponse responseToken =  userManagementService.refreshToken(login);
		System.out.println(responseToken);
		assertNotNull(responseToken);

	}

	@Test
	public void getAllUsers(){
		requestResponse.setName("Moyin oluwakpo");
		requestResponse.setEmail("IkennaJames@gmail.com");
		requestResponse.setPassword("123Password");
		requestResponse.setState("Lagos");
		requestResponse.setCountry("Nigeria");
		requestResponse.setRole("ADMIN");
		RequestResponse response = userManagementService.register(requestResponse);
		System.out.println(response);
		assertNotNull(response);

		requestResponse.setName("Ikenna james");
		requestResponse.setEmail("IkennaJames03@gmail.com");
		requestResponse.setPassword("123Password");
		requestResponse.setState("Lagos");
		requestResponse.setCountry("Nigeria");
		requestResponse.setRole("USER");
		RequestResponse responses = userManagementService.register(requestResponse);
		System.out.println(responses);
		assertNotNull(responses);

		loginResponse.setEmail("IkennaJames@gmail.com");
		loginResponse.setPassword("123Password");
		RequestResponse login = userManagementService.login(loginResponse);
		System.out.println(login);
		assertNotNull(login);

		RequestResponse getAllUsers = userManagementService.getAllUsers();
		System.out.println(getAllUsers);
		assertNotNull(getAllUsers);
	}

	@Test
	public void getUserId(){
		requestResponse.setName("Moyin oluwakpo");
		requestResponse.setEmail("IkennaJames@gmail.com");
		requestResponse.setPassword("123Password");
		requestResponse.setState("Lagos");
		requestResponse.setCountry("Nigeria");
		requestResponse.setRole("ADMIN");
		RequestResponse response = userManagementService.register(requestResponse);
		System.out.println(response);
		assertNotNull(response);

		requestResponse.setName("Ikenna james");
		requestResponse.setEmail("IkennaJames03@gmail.com");
		requestResponse.setPassword("123Password");
		requestResponse.setState("Lagos");
		requestResponse.setCountry("Nigeria");
		requestResponse.setRole("USER");
		RequestResponse responses = userManagementService.register(requestResponse);
		System.out.println(responses);
		assertNotNull(responses);
		Long userId = responses.getUsers().getId();
		System.out.println(userId);



		RequestResponse getUser = userManagementService.getUserById(userId);
		System.out.println(getUser);


	}

	@Test
	public void deleteUser(){
		requestResponse.setName("Moyin oluwakpo");
		requestResponse.setEmail("IkennaJames@gmail.com");
		requestResponse.setPassword("123Password");
		requestResponse.setState("Lagos");
		requestResponse.setCountry("Nigeria");
		requestResponse.setRole("ADMIN");
		RequestResponse response = userManagementService.register(requestResponse);
		System.out.println(response);
		assertNotNull(response);

		requestResponse.setName("Ikenna james");
		requestResponse.setEmail("IkennaJames03@gmail.com");
		requestResponse.setPassword("123Password");
		requestResponse.setState("Lagos");
		requestResponse.setCountry("Nigeria");
		requestResponse.setRole("USER");
		RequestResponse responses = userManagementService.register(requestResponse);
		System.out.println(responses);
		assertNotNull(responses);
		Long userId = responses.getUsers().getId();
		System.out.println(userId);

		RequestResponse getUser = userManagementService.deleteUser(userId);
		System.out.println(getUser);
		assertNull(getUser.getUsers());


	}

	@Test
	public void upDateUser(){
		requestResponse.setName("Moyin oluwakpo");
		requestResponse.setEmail("IkennaJames@gmail.com");
		requestResponse.setPassword("123Password");
		requestResponse.setState("Lagos");
		requestResponse.setCountry("Nigeria");
		requestResponse.setRole("USER");
		RequestResponse response = userManagementService.register(requestResponse);
		System.out.println(response);
		assertNotNull(response);
		Long userId = response.getUsers().getId();
		System.out.println(userId);
		Users currentUser = response.getUsers();
		System.out.println(currentUser);

		currentUser.setName("Moyin oluwakpo orente girl");
		currentUser.setEmail("MOyin@gmail.com");
		currentUser.setPassword("Password");
		currentUser.setState("Ogun");
		currentUser.setCountry("Nigeria");

		RequestResponse updateRes = userManagementService.updateUser(userId,currentUser);
		assertEquals(updateRes.getUsers().getName(),"Moyin oluwakpo orente girl");
		System.out.println(updateRes);
	}

	@Test
	public void viewProfile(){
		requestResponse.setName("Moyin oluwakpo");
		requestResponse.setEmail("IkennaJames@gmail.com");
		requestResponse.setPassword("123Password");
		requestResponse.setState("Lagos");
		requestResponse.setCountry("Nigeria");
		requestResponse.setRole("USER");
		RequestResponse response = userManagementService.register(requestResponse);
		System.out.println(response);
		assertNotNull(response);
		String userEmail = response.getUsers().getEmail();
		System.out.println(userEmail);
		Users currentUser = response.getUsers();
		System.out.println(currentUser);

		RequestResponse myProfile = userManagementService.getMyInfo(userEmail);
		System.out.println(myProfile);


	}

	@Test
	public void getUserByToken(){
		requestResponse.setName("Moyin oluwakpo");
		requestResponse.setEmail("IkennaJames@gmail.com");
		requestResponse.setPassword("123Password");
		requestResponse.setState("Lagos");
		requestResponse.setCountry("Nigeria");
		requestResponse.setRole("USER");
		RequestResponse response = userManagementService.register(requestResponse);
		System.out.println(response);
		assertNotNull(response);

		loginResponse.setEmail("IkennaJames@gmail.com");
		loginResponse.setPassword("123Password");
		RequestResponse login = userManagementService.login(loginResponse);
		System.out.println(login);
		assertNotNull(login);

		String jwtToken = login.getToken();
		System.out.println(jwtToken);

		RequestResponse user =userManagementService.getUserByJwtToken(jwtToken);
		System.out.println(user);

	}


	@Test
	public void numberOfRegisteredUser(){
		requestResponse.setName("Moyin oluwakpo");
		requestResponse.setEmail("IkennaJames@gmail.com");
		requestResponse.setPassword("123Password");
		requestResponse.setState("Lagos");
		requestResponse.setCountry("Nigeria");
		requestResponse.setRole("ADMIN");
		RequestResponse response = userManagementService.register(requestResponse);
		System.out.println(response);
		assertNotNull(response);

		requestResponse.setName("Ikenna james");
		requestResponse.setEmail("IkennaJames03@gmail.com");
		requestResponse.setPassword("123Password");
		requestResponse.setState("Lagos");
		requestResponse.setCountry("Nigeria");
		requestResponse.setRole("USER");
		RequestResponse responses = userManagementService.register(requestResponse);
		System.out.println(responses);
		assertNotNull(responses);
		Long userId = responses.getUsers().getId();
		System.out.println(userId);

		RequestResponse count = userManagementService.numberOfUsers();
		assertEquals(count.getCount(),2);
		System.out.println(count);
	}


   @Test
	public void canFindUsersByCountry(){
	   requestResponse.setName("Moyin oluwakpo");
	   requestResponse.setEmail("IkennaJames@gmail.com");
	   requestResponse.setPassword("123Password");
	   requestResponse.setState("Lagos");
	   requestResponse.setCountry("Nigeria");
	   requestResponse.setRole("ADMIN");
	   RequestResponse response = userManagementService.register(requestResponse);
	   System.out.println(response);
	   assertNotNull(response);

	   requestResponse.setName("Ikenna james");
	   requestResponse.setEmail("IkennaJames03@gmail.com");
	   requestResponse.setPassword("123Password");
	   requestResponse.setState("Lagos");
	   requestResponse.setCountry("Nigeria");
	   requestResponse.setRole("USER");
	   RequestResponse responses = userManagementService.register(requestResponse);
	   System.out.println(responses);
	   assertNotNull(responses);
	   Long userId = responses.getUsers().getId();
	   System.out.println(userId);


	   requestResponse.setName("monic monica");
	   requestResponse.setEmail("denzel@gmail.com");
	   requestResponse.setPassword("123Password");
	   requestResponse.setState("new jersey");
	   requestResponse.setCountry("U.S.A");
	   requestResponse.setRole("USER");
	   RequestResponse thirdResponse = userManagementService.register(requestResponse);
	   System.out.println(thirdResponse);
	   assertNotNull(thirdResponse);
	   Long theUserId = thirdResponse.getUsers().getId();
	   System.out.println(theUserId);

	   RequestResponse usersByCountry = userManagementService.findByCountry("NIGERIA");
	   System.out.println(usersByCountry);

	   assertNotNull(usersByCountry);
	   assertFalse(usersByCountry.getUsersList().isEmpty());

	   for (Users user : usersByCountry.getUsersList()) {
		   assertEquals("Nigeria", user.getCountry());
	   }

	   assertEquals(2, usersByCountry.getUsersList().size());

   }

	@Test
	public void canReturnListOfUsersByCountries(){
		requestResponse.setName("Moyin oluwakpo");
		requestResponse.setEmail("IkennaJames@gmail.com");
		requestResponse.setPassword("123Password");
		requestResponse.setState("Lagos");
		requestResponse.setCountry("Nigeria");
		requestResponse.setRole("ADMIN");
		RequestResponse response = userManagementService.register(requestResponse);
		System.out.println(response);
		assertNotNull(response);

		requestResponse.setName("Ikenna james");
		requestResponse.setEmail("IkennaJames03@gmail.com");
		requestResponse.setPassword("123Password");
		requestResponse.setState("Lagos");
		requestResponse.setCountry("Nigeria");
		requestResponse.setRole("USER");
		RequestResponse responses = userManagementService.register(requestResponse);
		System.out.println(responses);
		assertNotNull(responses);
		Long userId = responses.getUsers().getId();
		System.out.println(userId);


		requestResponse.setName("monic monica");
		requestResponse.setEmail("denzel@gmail.com");
		requestResponse.setPassword("123Password");
		requestResponse.setState("new jersey");
		requestResponse.setCountry("U.S.A");
		requestResponse.setRole("USER");
		RequestResponse thirdResponse = userManagementService.register(requestResponse);
		System.out.println(thirdResponse);
		assertNotNull(thirdResponse);
		Long theUserId = thirdResponse.getUsers().getId();
		System.out.println(theUserId);

		RequestResponse res = userManagementService.countUsersByCountry();
		System.out.println(res);

		for (CountryUserCount count : res.getCountryUserCount()) {
			if (count.getCountry().equals("Nigeria")) {
				assertEquals(2, count.getUserCount());
			} else if (count.getCountry().equals("U.S.A")) {
				assertEquals(1, count.getUserCount());
			}
		}
	}

	@Test
	public void canGetNumberOfUsersByCountryName(){
		requestResponse.setName("Moyin oluwakpo");
		requestResponse.setEmail("IkennaJames@gmail.com");
		requestResponse.setPassword("123Password");
		requestResponse.setState("Lagos");
		requestResponse.setCountry("Nigeria");
		requestResponse.setRole("ADMIN");
		RequestResponse response = userManagementService.register(requestResponse);
		System.out.println(response);
		assertNotNull(response);

		requestResponse.setName("Ikenna james");
		requestResponse.setEmail("IkennaJames03@gmail.com");
		requestResponse.setPassword("123Password");
		requestResponse.setState("Lagos");
		requestResponse.setCountry("Nigeria");
		requestResponse.setRole("USER");
		RequestResponse responses = userManagementService.register(requestResponse);
		System.out.println(responses);
		assertNotNull(responses);
		Long userId = responses.getUsers().getId();
		System.out.println(userId);


		requestResponse.setName("monic monica");
		requestResponse.setEmail("denzel@gmail.com");
		requestResponse.setPassword("123Password");
		requestResponse.setState("new jersey");
		requestResponse.setCountry("U.S.A");
		requestResponse.setRole("USER");
		RequestResponse thirdResponse = userManagementService.register(requestResponse);
		System.out.println(thirdResponse);
		assertNotNull(thirdResponse);
		Long theUserId = thirdResponse.getUsers().getId();
		System.out.println(theUserId);

		RequestResponse res = userManagementService.totalNumberOfUsersByCountry("NIGERIA");
		System.out.println(res);
		assertEquals(res.getCount(),2L);
	}


	}

