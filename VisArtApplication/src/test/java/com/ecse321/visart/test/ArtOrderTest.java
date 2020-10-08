package com.ecse321.visart.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.ArtOrder;
import com.ecse321.visart.model.ArtPiece;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.Manager;
import com.ecse321.visart.model.Ticket;
import com.ecse321.visart.model.ArtPiece.PieceLocation;
import com.ecse321.visart.repositories.ArtPieceRepository;
import com.ecse321.visart.repositories.CustomerRepository;
import com.ecse321.visart.repositories.ManagerRepository;
import com.ecse321.visart.repositories.ArtistRepository;
import com.ecse321.visart.repositories.ArtOrderRepository;
import com.ecse321.visart.repositories.TicketRepository;
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
  private CustomerRepository cRepo;

  @Autowired
  private ArtListingRepository alRepo;

  @Autowired
  private ArtistRepository aRepo;

  // ID CODE for tested object
  private static Long l;

  // Attributes for artOrder1
  String address = "123 STLOUIS";
  String tracker = "AZA123";

  // Attributes for artOrder2
  String address2 = "125 ALSTON";
  String tracker2 = "AZA124";

  private static ArtOrder artOrder4;

  private static ArtOrder artOrder2;

  private static Artist artist;

  private static ArtListing artListing;

  private static Artist artist2;

  private static ArtListing artListing2;

  private static ArtPiece artPiece;

  private static ArtPiece artPiece3;

  private static ArtPiece artPiece2;

  private static Customer customer;

  @Test
  @Order(1)
  void init() {
    // Set the static long value for the id ONCE
    l = System.currentTimeMillis();
    artist = aRepo.createArtist("test" + l + 1, "steve@gmail.com", "Steve Jobs", "steve56",
        "apple1234");
    artListing = alRepo.createArtListing(ArtListing.PostVisibility.Public, "t" + l + 2,
        artist);

    artist2 = aRepo.createArtist("testa" + l + 1, "steves@gmail.com", "Steves Jobs",
        "steves56", "apples1234");
    artListing2 = alRepo.createArtListing(ArtListing.PostVisibility.Public, "t" + l + 77,
        artist2);

    artPiece = apRepo.createArtPiece(PieceLocation.AtGallery, "676Alston", "AAA" + l + 2,
        artListing2);
    artPiece3 = apRepo.createArtPiece(PieceLocation.AtGallery, "699Alston", "AAA" + l + 45,
        artListing2);

    artPiece2 = apRepo.createArtPiece(PieceLocation.Offsite, "346STLOUIS", "ttt" + l + 3,
        artListing);
    customer = cRepo.createCustomer("tests" + l + 4, "KPP@GMAIL.COM", "KYARY", "KPP123",
        "japan56");

  }

  @Test
  @Order(2)
  void createArtOrder1() {
    artOrder2 = aoRepo.createArtOrder(false, ArtPiece.PieceLocation.Offsite, address2,
        tracker2, "tts" + l + 9, artPiece2);

    // Test if Art Order was created
    assertNotNull(artOrder2);

    // Print Art Order
    System.out.println("============================");
    System.out.println(artOrder2);
    System.out.println("============================");
  }

  @Test
  @Order(3)
  void getArtOrder1() {
    // Find artOrder2
    artOrder4 = aoRepo.getArtOrder("tts" + l + 9);

    // Test if Art Order was retrieved
    assertNotNull(artOrder4);
    assertEquals(address2, artOrder4.getTargetAddress());
    assertEquals(tracker2, artOrder4.getDeliveryTracker());

    // Print Art Order
    System.out.println("============================");
    System.out.println(artOrder4);
    System.out.println("============================");
  }
}
