package com.ecse321.visart.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Manager;
import com.ecse321.visart.model.ArtListing.PostVisibility;
import com.ecse321.visart.repositories.ArtListingRepository;
import com.ecse321.visart.repositories.ManagerRepository;
import com.ecse321.visart.repositories.ArtistRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ArtListingTest {

	Long l = System.currentTimeMillis();
	Long f = System.currentTimeMillis();
	Long g = System.currentTimeMillis();

	@Autowired
	private ManagerRepository managerRepo;
	private ArtistRepository artRepo;
	private ArtListingRepository aListRepo;

	// Create manager and artist instance 
	Manager manager = managerRepo.createManager("" + l, "timcook@gmail.com", "Tim Cook", "timcook56", "apple123");
	Artist artist = artRepo.createArtist("" + f, "steve@gmail.com", "Steve Jobs", "steve56", "apple1234");

	@Test
	void testEntry() {

		//Create
		ArtListing artListingTest = aListRepo.createArtListing(ArtListing.PostVisibility.Public, "" + g, manager,artist);

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
		ArtListing artListingTest2 = aListRepo.getArtListing("" + g);
		
		//Test if Art Listing was retrieved
		assertNotNull(artListingTest2);
		assertEquals(artist, artListingTest2.getArtist());
		assertEquals(manager, artListingTest2.getManager());
		
		System.out.println("============================");
		System.out.println(artListingTest2);
		System.out.println("============================");
	}

}
