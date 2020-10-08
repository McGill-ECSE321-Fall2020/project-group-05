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
import com.ecse321.visart.model.ArtListing.PostVisibility;
import com.ecse321.visart.repositories.ArtListingRepository;
import com.ecse321.visart.repositories.ManagerRepository;
import com.ecse321.visart.repositories.ArtistRepository;
import com.ecse321.visart.model.Artist;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ArtListingTest {

	Long l = System.currentTimeMillis();


	@Autowired
	private ManagerRepository managerRepo;
	
	@Autowired
	private ArtistRepository artRepo;
	
	@Autowired
	private ArtListingRepository aListRepo;

	// Create manager and artist instance 

	

	@Test
	void testEntry() {
		
	Manager manager = managerRepo.createManager("" + l, "timcook@gmail.com", "Tim Cook", "timcook56", "apple123");
	Artist artist = artRepo.createArtist("" + 1, "steve@gmail.com", "Steve Jobs", "steve56", "apple1234");
		
		

		//Create
		ArtListing artListingTest = aListRepo.createArtListing(ArtListing.PostVisibility.Public, "" +l+2, manager,artist);

		// Test if artListing was created
		assertNotNull(artListingTest);
		
		// Print Art Listing
		System.out.println("============================");
		System.out.println(artListingTest);
		System.out.println("============================");

//////GET TEST
		
		//Find Art Listing
		ArtListing artListingTest2 = aListRepo.getArtListing("" +l+ 2);
		
		//Test if Art Listing was retrieved
		assertNotNull(artListingTest2);
		assertEquals(artist, artListingTest2.getArtist());
		assertEquals(manager, artListingTest2.getManager());
		
		System.out.println("============================");
		System.out.println(artListingTest2);
		System.out.println("============================");
	}

}
