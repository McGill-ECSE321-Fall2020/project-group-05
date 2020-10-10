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
import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.ArtOrder;
import com.ecse321.visart.model.ArtPiece;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.ArtPiece.PieceLocation;
import com.ecse321.visart.repositories.ArtPieceRepository;
import com.ecse321.visart.repositories.ArtistRepository;
import com.ecse321.visart.repositories.ArtOrderRepository;
import com.ecse321.visart.repositories.ArtListingRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class ArtOrderTest {

  @Autowired
  private ArtOrderRepository aoRepo;

  @Autowired
  private ArtPieceRepository apRepo;

  @Autowired
  private ArtListingRepository alRepo;

  @Autowired
  private ArtistRepository aRepo;

  // Attributes for artOrder1
  String address = "123 STLOUIS";
  String tracker = "BABA123";

  // Attributes for artOrder2
  String address2 = "125 ALSTON";
  String tracker2 = "AZA124";

 
  private static ArtOrder artOrder2;
  private static Artist artist;
  private static ArtListing artListing;
  private static ArtPiece artPiece2;

  // Pseudo-random ID for tested entities
  static Long l = System.currentTimeMillis();

  @Test
  @Order(1)
  void init() {
    artist = aRepo.createArtist("test" + l + 1, "steve@gmail.com", "Steve Jobs", "steve56",
        "apple1234",
        "www.ipod.com", "iphones");
    artListing = alRepo.createArtListing(ArtListing.PostVisibility.Public, "rainbows", "myartworks",
        "t" + l + 2,
        artist);

    artPiece2 = apRepo.createArtPiece(PieceLocation.Offsite, "346STLOUIS", "ttt" + l + 3,
        artListing);
  }

  @Test
  @Order(2)
  void testCreate1() {
    // Create
	 ArtOrder artOrder1 = aoRepo.createArtOrder(false, ArtPiece.PieceLocation.Offsite, address2,
        tracker2,
        "tts" + l + 9, artPiece2);

    // Test if Art Order was created
    assertNotNull(artOrder1);

    // Print Art Order
    System.out.println("=================CREATE===============");
    System.out.println(artOrder1);
    System.out.println("=================CREATE===============");
  }

  @Test
  @Order(3)
  void testGet1() {
    // Find artOrder2
    artOrder2 = aoRepo.getArtOrder("tts" + l + 9);

    // Test if Art Order was retrieved
    assertNotNull(artOrder2);
    assertEquals(address2, artOrder2.getTargetAddress());
    assertEquals(tracker2, artOrder2.getDeliveryTracker());

    // Print Art Order
    System.out.println("=================FIND===============");
    System.out.println(artOrder2);
    System.out.println("=================FIND===============");
  }

  @Test
  @Order(4)
  void testUpdate() {

    // Changing attribute of art order
    artOrder2.setDeliveryTracker("BABA123");

    // Update art order in database
    aoRepo.updateArtOrder(artOrder2);

    // Getting updated art order
    artOrder2 = aoRepo.getArtOrder("tts" + l + 9);

    // Test if art order updated successfully
    assertNotNull(artOrder2);
    assertEquals(address2, artOrder2.getTargetAddress());
    assertEquals(tracker, artOrder2.getDeliveryTracker());

    // Print art order
    System.out.println("=================UPDATE===============");
    System.out.println(artOrder2);
    System.out.println("=================UPDATE===============");
  }

  @Test
  @Order(5)
  void testDelete() {
    // Delete all creation
	
    aoRepo.deleteArtOrder(artOrder2);
    aRepo.deleteArtist(artist);
    alRepo.deleteArtListing(artListing);
    apRepo.deleteArtPiece(artPiece2);
    
    // Test if art order successfully deleted
    assertEquals(null, aoRepo.getArtOrder("tts" + l + 9));
    
    //Test if other creation has been successfully deleted
    assertEquals(null, aRepo.getArtist("test" + l + 1));
    assertEquals(null, alRepo.getArtListing("t" + l + 2));
    assertEquals(null, apRepo.getArtPiece("ttt" + l + 3));
    

    System.out.println("=================DELETED===============");

  }

}
