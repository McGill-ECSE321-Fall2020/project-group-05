package com.ecse321.visart.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.repositories.ArtListingRepository;
import com.ecse321.visart.repositories.ArtistRepository;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ArtListingTest {

  @Autowired
  private ArtListingRepository aListRepo;

  @Autowired
  private ArtistRepository artRepo;

  public static Artist artist;
  public static Customer customer;
  
  // Pseudo-random ID for tested entities
  static Long l = System.currentTimeMillis();

  @Test
  @Order(1)
  void init() {
    artist = artRepo.createArtist("tester" + l + 26, "steve@gmail.com", "Steve Jobs", "steve56",
        "apple1234");
  }

  @Test
  @Order(2)
  void testCreate1() {

    // Create
    ArtListing artListingTest = aListRepo.createArtListing(ArtListing.PostVisibility.Public,
        "tt" + l, artist);

    // Test if artListing was created
    assertNotNull(artListingTest);

    // Print Art Listing
    System.out.println("============================");
    System.out.println(artListingTest);
    System.out.println("============================");

  }

  @Test
  @Order(3)
  void testGet1() {
    // Find Art Listing
    ArtListing artListingTest2 = aListRepo.getArtListing("tt" + l);

    // Test if Art Listing was retrieved
    assertNotNull(artListingTest2);
    assertEquals(artist.getCustomer().getUser().getEmailAddress(),
        artListingTest2.getArtist().getCustomer().getUser().getEmailAddress());
    assertEquals(artist.getCustomer().getUser().getUsername(),
        artListingTest2.getArtist().getCustomer().getUser().getUsername());

    System.out.println("============================");
    System.out.println(artListingTest2);
    System.out.println("============================");
  }

}