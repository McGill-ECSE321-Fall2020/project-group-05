package com.ecse321.visart.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.Manager;
import com.ecse321.visart.model.User;
import com.ecse321.visart.model.ArtListing.PostVisibility;
import com.ecse321.visart.repositories.ArtListingRepository;
import com.ecse321.visart.repositories.ManagerRepository;
import com.ecse321.visart.repositories.ArtistRepository;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ArtListingTest {

	Long l = System.currentTimeMillis();

	@Autowired
	private ArtListingRepository aListRepo;
	
	@Autowired
	private ManagerRepository managerRepo;
	
	@Autowired
	private ArtistRepository artRepo;
	Manager manager;
	Artist artist;
	Customer customer;

	@Before
	void init() {
		
	String aEmailAddress = "timcook@gmail.com";
	String aDisplayname = "Tim Cook";
	String  aUsername = "timcook56";
	String aPassword = "apple123";
		
	//Attributes user 2
	String aEmailAddress2 = "timcook1@gmail.com";
	String aDisplayname2 = "Tim Cook1";
	String  aUsername2 = "timcook567";
	String aPassword2 = "apple1234";
		

	// Create manager and artist instance 
	manager = managerRepo.createManager("test"+l+56, "timcook@gmail.com", "Tim Cook", "timcook56", "apple123");
	artist = artRepo.createArtist("tester"+l+26, "steve@gmail.com", "Steve Jobs", "steve56", "apple1234");

		
		
	}
	
	

	@Test
	void testEntry() {
		
		init();

		//Create
		ArtListing artListingTest = aListRepo.createArtListing(ArtListing.PostVisibility.Public, "tt"+l+29, artist);

		// Test if artListing was created
		assertNotNull(artListingTest);
		
		// Print Art Listing
		System.out.println("============================");
		System.out.println(artListingTest);
		System.out.println("============================");

//////GET TEST
		
		//Find Art Listing
		ArtListing artListingTest2 = aListRepo.getArtListing("tt"+l+29);
		
		//Test if Art Listing was retrieved
		assertNotNull(artListingTest2);
		assertEquals(artist.getCustomer().getUser().getUsername(), artListingTest2.getArtist().getCustomer().getUser().getUsername());
		assertEquals(manager.getUser().getUsername(), artListingTest2.getManager().getUser().getUsername());
		
		System.out.println("============================");
		System.out.println(artListingTest2);
		System.out.println("============================");
	}

}
