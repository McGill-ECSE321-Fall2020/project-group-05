package com.ecse321.visart.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.User;
import com.ecse321.visart.model.ArtListing.PostVisibility;
import com.ecse321.visart.repositories.ArtListingRepository;
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
  private ArtistRepository artRepo;
  Artist artist;
  Customer customer;

  @Before
  void init() {

  
    artist = artRepo.createArtist("tester" + l + 26, "steve@gmail.com", "Steve Jobs", "steve56", "apple1234");

  }

  @Test
  void testEntry() {

    init();

    // Create
    ArtListing artListingTest = aListRepo.createArtListing(ArtListing.PostVisibility.Public, "tt" + l + 29, artist);

    // Test if artListing was created
    assertNotNull(artListingTest);

    // Print Art Listing
    System.out.println("============================");
    System.out.println(artListingTest);
    System.out.println("============================");

    ////// GET TEST

    // Find Art Listing
    ArtListing artListingTest2 = aListRepo.getArtListing("tt" + l + 29);

    // Test if Art Listing was retrieved
    assertNotNull(artListingTest2);
    assertEquals(artist.getCustomer().getUser().getEmailAddress(), artListingTest2.getArtist().getCustomer().getUser().getEmailAddress());
    assertEquals(artist.getCustomer().getUser().getUsername(), artListingTest2.getArtist().getCustomer().getUser().getUsername());
  

    System.out.println("============================");
    System.out.println(artListingTest2);
    System.out.println("============================");
  }

}