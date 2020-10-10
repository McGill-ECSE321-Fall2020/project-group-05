/**
 * @author riad el mahmoudy
 */
package com.ecse321.visart.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class CustomerTest {
	@Autowired
	private CustomerRepository customerRepo;

	private static String email;

	private static String displayName;

	private static String username;

	private static String password;

	private static String profilePic;

	private static String profileDesc;

	static Customer testCustomer2;

	// Pseudo-random ID for tested entities
	static Long l = System.currentTimeMillis();

	@Test
	@Order(1)
	void init() {
		// Attributes
		email = "riad.elmahmoudy@mail.mcgill.ca";
		displayName = "Riad";
		username = "riadelm";
		password = "aNicePassword";
		profilePic = "Zucc.jpg";
		profileDesc = "Salvador Dali's son";
	}

	@Test
	@Order(2)
	void testCreate1() {
		Customer testCustomer = customerRepo.createCustomer("" + l, email, displayName, username, password, profilePic,
				profileDesc);

		assertNotNull(testCustomer);
		assertEquals("Riad", testCustomer.getUser().getDisplayname());

		System.out.println(testCustomer);
	}

	@Test
	@Order(3)
	void testGet1() {

		testCustomer2 = customerRepo.getCustomer("" + l);
		assertNotNull(testCustomer2);
		assertEquals(displayName, testCustomer2.getUser().getDisplayname());
		assertEquals(username, testCustomer2.getUser().getUsername());
		assertEquals(password, testCustomer2.getUser().getPassword());
		assertEquals(email, testCustomer2.getUser().getEmailAddress());
		assertEquals(profilePic, testCustomer2.getUser().getProfilePicLink());
		assertEquals(profileDesc, testCustomer2.getUser().getProfileDescription());

		System.out.println(testCustomer2);

	}

	@Test
	@Order(4)
	void testUpdate1() {
		// Find manager
		testCustomer2 = customerRepo.getCustomer("" + l);

		// Update Customer
		testCustomer2.getUser().setDisplayname("newDisplayNameCustomer");
		customerRepo.updateCustomer(testCustomer2);

		testCustomer2 = customerRepo.getCustomer("" + l);

		// TEST if Customer was retrieved and if properly updated
		assertNotNull(testCustomer2);
		assertEquals(email, testCustomer2.getUser().getEmailAddress());
		assertEquals("newDisplayNameCustomer", testCustomer2.getUser().getDisplayname());
		assertEquals(username, testCustomer2.getUser().getUsername());
		assertEquals(password, testCustomer2.getUser().getPassword());
		assertEquals(profilePic, testCustomer2.getUser().getProfilePicLink());
		assertEquals(profileDesc, testCustomer2.getUser().getProfileDescription());

		System.out.println("=================UPDATE===============");
		System.out.println(testCustomer2);
		System.out.println("=================UPDATE===============");
	}

	@Test
	@Order(5)
	void testDelete1() {
		customerRepo.deleteCustomer(testCustomer2);
		assertEquals(null, customerRepo.getCustomer("" + l));
	}

}
