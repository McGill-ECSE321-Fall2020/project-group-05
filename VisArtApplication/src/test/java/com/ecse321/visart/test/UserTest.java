package com.ecse321.visart.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ecse321.visart.model.User;
import com.ecse321.visart.repositories.UserRepository;

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserTest {

	@Autowired
	private UserRepository userRepo;

	private String email;

	private String displayName;

	private String username;

	private String password;
	
	private static String profilePic;
	  
	private static String profileDesc;

	static private User User2;
	

	// Pseudo-random ID for tested entities
	static Long l = System.currentTimeMillis();

	@Test
	@Order(1)
	void init() {
		// Attributes
		email = "blah";
		displayName = "Riad";
		username = "riadelm";
		password = "aNicePassword";
		profilePic = "Bezos.jpg";
	    profileDesc = "ACAB, billionaires shouldnt exist, capitalism suxx";
	}

	@Test
	@Order(2)
	void testCreate1() {
		// Create
		User testUser = userRepo.createUser("" + l, email, displayName, username, password, profilePic, profileDesc);

		// TEST if Manager was created
		assertNotNull(testUser);

		// Print Manager
		System.out.println("=================CREATE===============");
		System.out.println(testUser);
		System.out.println("=================CREATE===============");
	}

	
}
