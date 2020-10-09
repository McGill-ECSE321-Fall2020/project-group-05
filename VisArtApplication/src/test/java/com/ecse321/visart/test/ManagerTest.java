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

import com.ecse321.visart.model.Manager;
import com.ecse321.visart.repositories.ManagerRepository;

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ManagerTest {

	@Autowired
	private ManagerRepository managerRepo;

	private String email;

	private String displayName;

	private String username;

	private String password;

	static private Manager testManager2;

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
	}

	@Test
	@Order(2)
	void testCreate1() {
		// Create
		Manager testManager = managerRepo.createManager("" + l, email, displayName, username, password);

		// TEST if Manager was created
		assertNotNull(testManager);

		// Print Manager
		System.out.println("=================CREATE===============");
		System.out.println(testManager);
		System.out.println("=================CREATE===============");
	}

	@Test
	@Order(3)
	void testGet1() {
		// Find manager
		testManager2 = managerRepo.getManager("" + l);

		// TEST if Manager was retrieved
		assertNotNull(testManager2);
		assertEquals(email, testManager2.getUser().getEmailAddress());
		assertEquals(displayName, testManager2.getUser().getDisplayname());
		assertEquals(username, testManager2.getUser().getUsername());
		assertEquals(password, testManager2.getUser().getPassword());

		System.out.println("=================FIND===============");
		System.out.println(testManager2);
		System.out.println("=================FIND===============");
	}

	@Test
	@Order(4)
	void testUpdate1() {
		// Find manager
		testManager2 = managerRepo.getManager("" + l);
		
		//Update Manager
		testManager2.getUser().setDisplayname("newDisplayName");
		managerRepo.updateManager(testManager2);
		

		// TEST if Manager was retrieved and if properly updated
		assertNotNull(testManager2);
		assertEquals(email, testManager2.getUser().getEmailAddress());
		assertEquals("newDisplayName", testManager2.getUser().getDisplayname());
		assertEquals(username, testManager2.getUser().getUsername());
		assertEquals(password, testManager2.getUser().getPassword());

		System.out.println("=================UPDATE===============");
		System.out.println(testManager2);
		System.out.println("=================UPDATE===============");
	}

	@Test
	@Order(5)
	void testDelete1() {
		managerRepo.deleteManager(testManager2);
		assertEquals(null, managerRepo.getManager("" + l));
	}

}
