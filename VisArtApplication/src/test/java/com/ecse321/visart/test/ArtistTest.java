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
import com.ecse321.visart.repositories.ArtistRepository;

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ArtistTest {

	@Autowired
	private ArtistRepository aRepo;
	String aEmailAddress;
	String aDisplayname;
	String aUsername;
	String aPassword;
	String ProfilePicLink;
	String aProfileDescription;
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
		Artist testArtist = aRepo.createArtist(l + "test" + 2, aEmailAddress, aDisplayname, aUsername, aPassword, ProfilePicLink, aProfileDescription);

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

		// Test if got Artist corresponds to entry
		assertNotNull(testArtist2);
		assertEquals(aEmailAddress, testArtist2.getCustomer().getUser().getEmailAddress());
		assertEquals(aUsername, testArtist2.getCustomer().getUser().getUsername());

		System.out.println("=================FIND===============");
		System.out.println(testArtist2);
		System.out.println("=================FIND===============");
	}

	@Test
	@Order(4)
	void testDelete() {

		aRepo.deleteArtist(testArtist2);
		assertEquals(null, aRepo.getArtist(l + "test" + 2));

		System.out.println("=================DELETED===============");
		

	}

}
