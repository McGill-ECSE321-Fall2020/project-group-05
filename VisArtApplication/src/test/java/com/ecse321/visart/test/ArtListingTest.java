package com.ecse321.visart.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.Manager;
import com.ecse321.visart.model.User;
import com.ecse321.visart.model.ArtListing.PostVisibility;
import com.ecse321.visart.repositories.ArtListingRepository;
import com.ecse321.visart.repositories.ManagerRepository;
import com.ecse321.visart.repositories.ArtistRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ArtListingTest {

	Long l = System.currentTimeMillis();


	@Autowired
	private ArtListingRepository aListRepo;
	
	//Attributes
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
	User aUser = new User(""+l+1,aEmailAddress, aDisplayname, aUsername, aPassword);
	User aUser2 = new User(""+l+5,aEmailAddress2, aDisplayname2, aUsername2, aPassword2);
	
	Manager manager = new Manager("" + l, aUser); 
	Customer customer = new Customer(""+l+2,aUser2);
	Artist artist = new Artist(""+l+3, customer);

	@Test
	void testEntry() {
		

		//Create
		ArtListing artListingTest = aListRepo.createArtListing(ArtListing.PostVisibility.Public, "" +l+4, manager,artist);

		// Test if artListing was created
		assertNotNull(artListingTest);
		
		// Print Art Listing
		System.out.println("============================");
		System.out.println(artListingTest);
		System.out.println("============================");

	}

	@Test
	void testGet() {
		
		//Find Art Listing
		ArtListing artListingTest2 = aListRepo.getArtListing("" +l+4);
		
		//Test if Art Listing was retrieved
		assertNotNull(artListingTest2);
		assertEquals(artist, artListingTest2.getArtist());
		assertEquals(manager, artListingTest2.getManager());
		
		System.out.println("============================");
		System.out.println(artListingTest2);
		System.out.println("============================");
	}

}
