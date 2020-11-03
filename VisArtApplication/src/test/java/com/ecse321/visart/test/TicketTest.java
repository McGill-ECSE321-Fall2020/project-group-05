/**
 * @author riad el mahmoudy
 */
package com.ecse321.visart.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.ArtOrder;
import com.ecse321.visart.model.ArtPiece;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.Manager;
import com.ecse321.visart.model.Ticket;
import com.ecse321.visart.model.User;
import com.ecse321.visart.repositories.ArtListingRepository;
import com.ecse321.visart.repositories.ArtOrderRepository;
import com.ecse321.visart.repositories.ArtPieceRepository;
import com.ecse321.visart.repositories.ArtistRepository;
import com.ecse321.visart.repositories.CustomerRepository;
import com.ecse321.visart.repositories.ManagerRepository;
import com.ecse321.visart.repositories.TicketRepository;

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TicketTest {

  @Autowired
  private TicketRepository ticketRepo;

  @Autowired
  private CustomerRepository customerRepo;

  @Autowired
  private ArtistRepository artistRepo;

  @Autowired
  private ManagerRepository managerRepo;

  @Autowired
  private ArtListingRepository artlistingRepo;

  @Autowired
  private ArtPieceRepository artpieceRepo;

  @Autowired
  private ArtOrderRepository artorderRepo;

  // Attributes
  static Ticket testTicket2;
  String email;
  String displayName;
  String username;
  String password;
  String id = "test" + l;
  boolean isAPaymentConfirmed;
  double aPaymentAmount;
  boolean isDeliveredForOrder;
  String aTargetAddressForOrder;
  String aDeliveryTrackerForOrder;
  String aIdCodeForOrder;
  static User aUser;
  static User aUser2;
  static User aUser3;
  static Customer aCustomer;
  static Customer aCustomer2;
  static Artist anArtist;
  static Manager aManager;
  static ArtListing aListing;
  static ArtPiece anArtPiece;
  static ArtOrder aOrder;
  static String profilePic;
  static String profileDesc;
  static String listingDesc;
  static String title;

  // Pseudo-random ID for tested entities
  static Long l = System.currentTimeMillis();

  @Test
  @Order(1)
  void init() {
    // Attributes
    email = "riad.elmahmoudy@mail.mcgill.ca";
    displayName = "Riad";
    username = "riadelm";
    password = "aNicePassword";
    id = "test" + l;
    isAPaymentConfirmed = false;
    aPaymentAmount = 100.34;
    profilePic = "Bezos.jpg";
    profileDesc = "ACAB, billionaires shouldnt exist";
    listingDesc = "mona lisa copy";
    title = "fake monalisa";

    aCustomer = customerRepo.createCustomer(id + "1", email + "customer", displayName + "customer",
        username + "customer", password + "customer", profilePic + "customer",
        profileDesc + "customer");
    anArtist = artistRepo.createArtist(id + "2", email + "artist", displayName + "artist",
        username + "artist",
        password + "artist", profilePic + "artist", profileDesc + "artist");
    aManager = managerRepo.createManager(id + "3", email + "manager", displayName + "manager",
        username + "manager",
        password + "manager", profilePic + "manager", profileDesc + "manager");
    aListing = artlistingRepo.createArtListing(ArtListing.PostVisibility.Public, listingDesc, title,
        id + "4",
        anArtist);
    anArtPiece = artpieceRepo.createArtPiece(ArtPiece.PieceLocation.AtGallery, "UniversityStreet",
        id + 5,
        aListing);
    aOrder = artorderRepo.createArtOrder(false, ArtPiece.PieceLocation.AtGallery, "sherbrookeSt",
        "00Tracker",
        id + 9, anArtPiece);

  }

  @Test
  @Order(2)
  void testCreate1() {

    // Create Ticket firstWay
    Ticket testTicket = ticketRepo.createTicket(isAPaymentConfirmed, aPaymentAmount, id, aOrder,
        aCustomer,
        anArtist);

    // TEST if Ticket was created
    assertNotNull(testTicket);

    // Print Ticket
    System.out.println("=================CREATE===============");
    System.out.println(testTicket);
    System.out.println("=================CREATE===============");

  }

  @Test
  @Order(3)
  void testGet1() {
    // Find Ticket
    testTicket2 = ticketRepo.getTicket(id);

    // TEST if Manager was retrieved
    assertNotNull(testTicket2);

    // Print retrieved ticket
    System.out.println("=================FIND===============");
    System.out.println(testTicket2);
    System.out.println("=================FIND===============");

  }

  @Test
  @Order(4)
  void testDelete1() {
    ticketRepo.deleteTicket(testTicket2);
    artorderRepo.deleteArtOrder(aOrder);
    artpieceRepo.deleteArtPiece(anArtPiece);
    artlistingRepo.deleteArtListing(aListing);
    managerRepo.deleteManager(aManager);
    artistRepo.deleteArtist(anArtist);
    customerRepo.deleteCustomer(anArtist.getCustomer());
    customerRepo.deleteCustomer(aCustomer);

    assertEquals(null, ticketRepo.getTicket(id));
    assertEquals(null, customerRepo.getCustomer(id + "1"));
    assertEquals(null, artistRepo.getArtist(id + "2"));
    assertEquals(null, managerRepo.getManager(id + "3"));
    assertEquals(null, artlistingRepo.getArtListing(id + "4"));
    assertEquals(null, artpieceRepo.getArtPiece(id + 5));
    assertEquals(null, artorderRepo.getArtOrder(id + 9));

  }

}
