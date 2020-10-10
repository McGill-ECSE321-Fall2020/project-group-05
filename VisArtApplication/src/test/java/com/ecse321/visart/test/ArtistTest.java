package com.ecse321.visart.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.repositories.ArtistRepository;
import com.ecse321.visart.repositories.CustomerRepository;

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ArtistTest {

	@Autowired
	private ArtistRepository aRepo;

	@Autowired
	private CustomerRepository cRepo;

	String aEmailAddress;
	String aDisplayname;
	String aUsername;
	String aPassword;
	String ProfilePicLink;
	String aProfileDescription;

	static private Customer customer;
	static private String id;
	static private Artist testArtist2;

	// Pseudo-random ID for tested entities
	static Long l = System.currentTimeMillis();

	@Test
	@Order(1)
	void init() {
		aEmailAddress = "timcook@gmail.com";
		aDisplayname = "Tim Cook";
		aUsername = "timcook56";
		aPassword = "apple123";
		ProfilePicLink = "www.apple.com";
		aProfileDescription = "I love cakes";
	}

	@Test
	@Order(2)
	void testCreate() {

		// Create
		Artist testArtist = aRepo.createArtist(l + "test" + 2, aEmailAddress, aDisplayname, aUsername, aPassword,
				ProfilePicLink, aProfileDescription);

		// Test if Artist was created
		assertNotNull(testArtist);

		// Print Artist
		System.out.println("=================CREATE===============");
		System.out.println(testArtist);
		System.out.println("=================CREATE===============");

	}

	@Test
	@Order(3)
	void testGet() {

		// Getting
		testArtist2 = aRepo.getArtist(l + "test" + 2);

		// Test if retrieved Artist corresponds to created artist
		assertNotNull(testArtist2);
		assertEquals(aEmailAddress, testArtist2.getCustomer().getUser().getEmailAddress());
		assertEquals(aPassword, testArtist2.getCustomer().getUser().getPassword());
		assertEquals(aUsername, testArtist2.getCustomer().getUser().getUsername());
		assertEquals(aDisplayname, testArtist2.getCustomer().getUser().getDisplayname());

		// Print Artist
		System.out.println("=================FIND===============");
		System.out.println(testArtist2);
		System.out.println("=================FIND===============");
	}

	@Test
	@Order(4)
	void testUpdate() {
		Artist artist = aRepo.getArtist(l + "test" + 2);
		assertEquals(aDisplayname, artist.getCustomer().getUser().getDisplayname());
		artist.getCustomer().getUser().setDisplayname("posh");
		aRepo.updateArtist(artist);
		cRepo.updateCustomer(artist.getCustomer());
		Artist retrieved = aRepo.getArtist(l + "test" + 2);
		assertEquals("posh", retrieved.getCustomer().getUser().getDisplayname());
	}

	@Test
	@Order(5)
	void testDelete() {

		// Delete
		aRepo.deleteArtist(testArtist2);

		// Delete all creation
		cRepo.deleteCustomer(testArtist2.getCustomer());

		// Test if artist have been successfully deleted
		assertEquals(null, aRepo.getArtist(l + "test" + 2));

		System.out.println("=================DELETED===============");

	}

	@Test
	@Order(6)
	void init1() {
		id = l + "test_artist";
		customer = cRepo.createCustomer(id, "test@email", "displayname01", "myUsername", "what", "link.com", "hi i am");
	}

	@Test
	@Order(7)
	void testCreate1() {
		Artist artist = aRepo.createArtist(id, customer);
		assertNotNull(artist);
		assertEquals(customer.getUser().getDisplayname(), artist.getCustomer().getUser().getDisplayname());
	}

	@Test
	@Order(8)
	void testGet1() {
		Artist artist = aRepo.getArtist(id);
		assertEquals(customer.getUser().getIdCode(), artist.getCustomer().getUser().getIdCode());
		assertEquals(customer.getUser().getDisplayname(), artist.getCustomer().getUser().getDisplayname());
	}

	@Test
	@Order(9)
	void testUpdate1() {
		Artist artist = aRepo.getArtist(id);
		artist.getCustomer().getUser().setDisplayname("chicken");
		assertEquals("chicken", artist.getCustomer().getUser().getDisplayname());
		aRepo.updateArtist(artist);
		cRepo.updateCustomer(artist.getCustomer());

		Artist retrieved = aRepo.getArtist(id);
		assertEquals("chicken", retrieved.getCustomer().getUser().getDisplayname());
	}

	@Test
	@Order(10)
	void testDelete1() {
		Artist artist = aRepo.getArtist(id);

		// Deleting all creation
		aRepo.deleteArtist(artist);
		cRepo.deleteCustomer(customer);

		// Test if all creation have been deleted
		assertNull(aRepo.getArtist(id));
		assertNull(cRepo.getCustomer(l + "test_artist"));
	}
}
