package com.ecse321.visart.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.ArtOrder;
import com.ecse321.visart.model.ArtPiece;
import com.ecse321.visart.model.ArtPiece.PieceLocation;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.User;
import com.ecse321.visart.model.ArtListing.PostVisibility;
import com.ecse321.visart.model.Ticket;
import com.ecse321.visart.repositories.ArtOrderRepository;
import com.ecse321.visart.repositories.ArtistRepository;
import com.ecse321.visart.repositories.CustomerRepository;
import com.ecse321.visart.repositories.EntityRepository;
import com.ecse321.visart.repositories.TicketRepository;
import com.ecse321.visart.service.TicketService;

@ExtendWith(MockitoExtension.class)
public class TestTicketService {

  @Mock
  private TicketRepository ticketRepo;

  @Mock
  private EntityRepository entityRepo;

  @Mock
  private ArtOrderRepository aoRepo;

  @Mock
  private ArtistRepository aRepo;

  @Mock
  private CustomerRepository cRepo;

  @InjectMocks
  private TicketService ticketService;

  private static final boolean TICKET_PAYMENT_BOOL = true;
  private static final double TICKET_AMOUNT = 420.69;
  private static final String TICKET_ID = "////ticketid////";
  private static final User artistUser = new User("code1", "r@mcgill.ca", "riad", "riadriad",
      "pass", "pic", "desc");
  private static final User customerUser = new User("code2", "ye@mcgill.ca", "kanye", "kanyekanye",
      "passye", "picye", "descye");
  private static final Customer TICKET_CUSTOMER = new Customer("customercode", customerUser);
  private static final Customer artistCustomer = new Customer("artsycode", artistUser);
  private static final Artist TICKET_ARTIST = new Artist("artistcode", artistCustomer);
  private static Ticket ticketTest = null;

  @BeforeEach
  public void setMockOutput() {
    // Mock the Repository methods, returning what we want to expect from the
    // database, instead of actually querying the database.

    lenient().when(ticketRepo.getTicket(anyString())).thenAnswer(
        (InvocationOnMock invocation) -> {
          if (invocation.getArgument(0).equals(TICKET_ID)) {
            User aUser2 = new User("a", "b", "c", "d", "e", "f", "g");
            Customer aCustomer2 = new Customer("customercode", aUser2);
            Artist aArtist2 = new Artist("artistcode", aCustomer2);
            ArtListing TEST_LISTING = new ArtListing(0.0,PostVisibility.Draft, "name",
                "listing", "mockcode",
                aArtist2);
            ArtPiece APTEST = new ArtPiece(PieceLocation.AtGallery, "locationtest",
                "123", TEST_LISTING);
            ArtOrder myOrder = new ArtOrder(false, PieceLocation.AtGallery, "str1", "str2",
                "ordercode", APTEST);
            Ticket myTicket = new Ticket(TICKET_PAYMENT_BOOL, TICKET_AMOUNT, TICKET_ID, myOrder,
                TICKET_CUSTOMER, TICKET_ARTIST);

            return myTicket;
          } else {
            return null;
          }
        });

    lenient().when(aoRepo.getArtOrder(anyString())).thenAnswer((InvocationOnMock invocation) -> {
      String id = invocation.getArgument(0);
      if (id.equals("ordercode")) {
        User aUser2 = new User("a", "b", "c", "d", "e", "f", "g");
        Customer aCustomer2 = new Customer("customercode", aUser2);
        Artist aArtist2 = new Artist("artistCode", aCustomer2);
        ArtListing TEST_LISTING = new ArtListing(0.0,PostVisibility.Draft, "name",
            "listing", "mockcode",
            aArtist2);
        ArtPiece APTEST = new ArtPiece(PieceLocation.AtGallery, "locationtest",
            "123", TEST_LISTING);
        ArtOrder myOrder = new ArtOrder(false, PieceLocation.AtGallery, "str1", "str2", "ordercode",
            APTEST);
        return myOrder;
      } else if (id.equals("ordercode2")) {
        User aUser3 = new User("a", "b", "c", "d", "e", "f", "g");
        Customer aCustomer3 = new Customer("customerCode2", aUser3);
        Artist aArtist3 = new Artist("artistCode2", aCustomer3);
        ArtListing TEST_LISTING2 = new ArtListing(0.0,PostVisibility.Draft, "name",
            "listing", "mockcode",
            aArtist3);
        ArtPiece APTEST2 = new ArtPiece(PieceLocation.AtGallery, "locationtest",
            "123", TEST_LISTING2);
        ArtOrder myOrder2 = new ArtOrder(false, PieceLocation.AtGallery, "str1NEW", "str2NEW",
            "ordercode2", APTEST2);
        return myOrder2;
      } else {

        return null;
      }

    });

    lenient().when(aRepo.getArtist(anyString())).thenAnswer((InvocationOnMock invocation) -> {
      String id = invocation.getArgument(0);
      if (id.equals("artistcode")) {
        return TICKET_ARTIST;
      } else if (id.equals("artistcode2")) {
        User aUser2 = new User("t", "b", "w", "d", "t", "f", "g");
        Customer aCustomer2 = new Customer("customercode2", aUser2);
        Artist aArtist2 = new Artist("artistcode2", aCustomer2);
        return aArtist2;
      } else {
        return null;
      }

    });

    lenient().when(cRepo.getCustomer(anyString())).thenAnswer((InvocationOnMock invocation) -> {
      String id = invocation.getArgument(0);
      if (id.equals("customercode")) {
        return TICKET_CUSTOMER;
      } else if (id.equals("customercode2")) {
        User aUser2 = new User("t", "b", "w", "d", "t", "f", "g");
        Customer aCustomer2 = new Customer("customercode2", aUser2);
        return aCustomer2;
      } else {
        return null;
      }

    });

    lenient().when(ticketRepo.createTicket(anyBoolean(), anyDouble(), anyString(), any(),
        any(), any())).thenAnswer((InvocationOnMock invocation) -> {
          boolean payment = (boolean) invocation.getArgument(0);
          double paymentPrice = (double) invocation.getArgument(1);
          ArtOrder order = (ArtOrder) invocation.getArgument(3);
          Customer customer = (Customer) invocation.getArgument(4);
          Artist artist = (Artist) invocation.getArgument(5);
          Ticket ticket = new Ticket(payment, paymentPrice, TICKET_ID, order, customer, artist);
          return ticket;
        });

    lenient().doAnswer((InvocationOnMock invocation) -> {
      ticketTest = invocation.getArgument(0);
      return ticketTest;
    }).when(ticketRepo).updateTicket(any());

    lenient().when(ticketRepo.deleteTicket(anyString()))
        .thenAnswer((InvocationOnMock invocation) -> {
          if (invocation.getArgument(0).equals(TICKET_ID)) {
            return true;
          } else {
            return false;
          }
        });

    // In the tests below, test the Service class's DATA VALIDATION on the
    // parameters that are given to it.

  }

  @Test
  public void testCreateTicket() {
    // assertEquals(0, service.getAllUsers().size());

    String id = TICKET_ID;
    boolean payment = false;
    double paymentPrice = 420.0;
    String aOrder = "ordercode";
    String aArtist = "artistcode";
    String aCustomer = "customercode";
    Ticket ticket = null;
    try {
      ticket = ticketService.createTicket(payment, paymentPrice, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      fail();
    }
    assertNotNull(ticket);
    assertEquals(id, ticket.getIdCode());
  }

  @Test
  public void testCreateNullTicketOrder() {
    boolean payment = false;
    double paymentPrice = 420.0;
    String aOrder = null;
    String aArtist = "artistcode";
    String aCustomer = "customercode";
    Ticket ticket = null;
    String error = null;
    try {
      ticket = ticketService.createTicket(payment, paymentPrice, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(ticket);
    assertEquals("Ticket order cannot be empty!", error); // expected error message for service data
                                                          // validation.
  }

  @Test
  public void testCreateBadTicketOrder() {
    boolean payment = false;
    double paymentPrice = 420.0;
    String aOrder = "ordercodefake";
    String aArtist = "artistcode";
    String aCustomer = "customercode";
    Ticket ticket = null;
    String error = null;
    try {
      ticket = ticketService.createTicket(payment, paymentPrice, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(ticket);
    assertEquals("Ticket Order should exist!", error); // expected error message for service data
                                                       // validation.
  }

  @Test
  public void testCreateNullTicketCustomer() {
    boolean payment = false;
    double paymentPrice = 420.0;
    String aOrder = "ordercode";
    String aArtist = "artistcode";
    String aCustomer = null;
    Ticket ticket = null;
    String error = null;
    try {
      ticket = ticketService.createTicket(payment, paymentPrice, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(ticket);
    assertEquals("Ticket customer cannot be empty!", error); // expected error message for service
                                                             // data
    // validation.
  }

  @Test
  public void testCreateBadTicketArtist() {
    boolean payment = false;
    double paymentPrice = 420.0;
    String aOrder = "ordercode";
    String aArtist = "artistcodefake";
    String aCustomer = "customercode";
    Ticket ticket = null;
    String error = null;
    try {
      ticket = ticketService.createTicket(payment, paymentPrice, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(ticket);
    assertEquals("Ticket Artist should exist!", error); // expected error message for service data
                                                        // validation.
  }

  @Test
  public void testCreateBadTicketCustomer() {
    boolean payment = false;
    double paymentPrice = 420.0;
    String aOrder = "ordercode";
    String aArtist = "artistcode";
    String aCustomer = "customercodefake";
    Ticket ticket = null;
    String error = null;
    try {
      ticket = ticketService.createTicket(payment, paymentPrice, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(ticket);
    assertEquals("Ticket Customer should exist!", error); // expected error message for service data
                                                          // validation.
  }

  @Test
  public void testCreateNullTicketArtist() {
    boolean payment = false;
    double paymentPrice = 420.0;
    String aOrder = "ordercode";
    String aArtist = null;
    String aCustomer = "customercode";
    Ticket ticket = null;
    String error = null;
    try {
      ticket = ticketService.createTicket(payment, paymentPrice, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(ticket);
    assertEquals("Ticket artist cannot be empty!", error); // expected error message for service
                                                           // data
                                                           // validation.
  }

  @Test
  public void testCreateNullTicketPaymentNegative() {
    boolean payment = false;
    double paymentPrice = -420.0;
    String aOrder = "ordercode";
    String aArtist = "artistcode";
    String aCustomer = "customercode";
    Ticket ticket = null;
    String error = null;
    try {
      ticket = ticketService.createTicket(payment, paymentPrice, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(ticket);
    assertEquals("Ticket payment amount cannot be negative!", error); // expected error message for
                                                                      // service data
    // validation.
  }

  @Test
  public void testCreateNullTicketSameArtistCustomer() {
    boolean payment = false;
    double paymentPrice = 420.0;
    String aOrder = "ordercode";
    String aArtist = "customercode";
    String aCustomer = "customercode";
    Ticket ticket = null;
    String error = null;
    try {
      ticket = ticketService.createTicket(payment, paymentPrice, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(ticket);
    assertEquals("Ticket customer and artist cannot be the same!", error); // expected error message
                                                                           // for service data
    // validation.
  }

  @Test
  public void testCreateNullTicketAll() {
    boolean payment = false;
    double paymentPrice = 0;
    String aOrder = null;
    String aArtist = null;
    String aCustomer = null;
    Ticket ticket = null;
    String error = null;
    try {
      ticket = ticketService.createTicket(payment, paymentPrice, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNotNull(error);
    assertNull(ticket);
    // assertEquals("Ticket id code cannot be empty!", error); // expected error
    // message for service data
    // validation.
  }

  @Test
  public void testUpdateTicketbool() {
    boolean payment = true;
    double paymentPrice = 420.0;
    String aOrder = "ordercode";
    String aArtist = "artistcode";
    String aCustomer = "customercode";
    Ticket ticket = null;
    String error = null;
    String id = TICKET_ID;
    try {
      ticket = ticketService.updateTicket(payment, paymentPrice, id, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(error);
    assertEquals(ticket.getIsPaymentConfirmed(), payment); // expected error message for service
                                                           // data
                                                           // validation.
  }

  @Test
  public void testUpdateTicketAmount() {
    boolean payment = false;
    double paymentPrice = 444.0;
    String aOrder = "ordercode";
    String aArtist = "artistcode";
    String aCustomer = "customercode";
    Ticket ticket = null;
    String error = null;
    String id = TICKET_ID;
    try {
      ticket = ticketService.updateTicket(payment, paymentPrice, id, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(error);
    assertEquals(ticket.getPaymentAmount(), paymentPrice, 0.1); // expected error message for
                                                                // service data
    // validation.
  }

  @Test
  public void testUpdateTicketOrder() {
    boolean payment = false;
    double paymentPrice = 444.0;
    String aOrder = "ordercode2";
    String aArtist = "artistcode";
    String aCustomer = "customercode";
    Ticket ticket = null;
    String error = null;
    String id = TICKET_ID;
    try {
      ticket = ticketService.updateTicket(payment, paymentPrice, id, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(error);
    assertEquals(ticket.getOrder().getIdCode(), aOrder); // expected error message for service data
                                                         // validation.
  }

  @Test
  public void testUpdateTicketArtist() {
    boolean payment = false;
    double paymentPrice = 444.0;
    String aOrder = "ordercode";
    String aArtist = "artistcode2";
    String aCustomer = "customercode";
    Ticket ticket = null;
    String error = null;
    String id = TICKET_ID;
    try {
      ticket = ticketService.updateTicket(payment, paymentPrice, id, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(error);
    assertEquals(ticket.getArtist().getIdCode(), aArtist); // expected error message for service
                                                           // data
                                                           // validation.
  }

  @Test
  public void testUpdateTicketCustomer() {
    boolean payment = false;
    double paymentPrice = 444.0;
    String aOrder = "ordercode";
    String aArtist = "artistcode";
    String aCustomer = "customercode2";
    Ticket ticket = null;
    String error = null;
    String id = TICKET_ID;
    try {
      ticket = ticketService.updateTicket(payment, paymentPrice, id, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(error);
    assertEquals(ticket.getCustomer().getIdCode(), aCustomer); // expected error message for service
                                                               // data
    // validation.
  }

  @Test
  public void testupdateNullTicketOrder() {
    boolean payment = false;
    double paymentPrice = 420.0;
    String aOrder = null;
    String aArtist = "artistcode";
    String aCustomer = "customercode";
    Ticket ticket = null;
    String error = null;
    String id = TICKET_ID;
    try {
      ticket = ticketService.updateTicket(payment, paymentPrice, id, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNotNull(ticket);
    assertNull(error);
  }

  @Test
  public void testupdateBadTicketOrder() {
    boolean payment = false;
    double paymentPrice = 420.0;
    String aOrder = "ordercodefake";
    String aArtist = "artistcode";
    String aCustomer = "customercode";
    Ticket ticket = null;
    String error = null;
    String id = TICKET_ID;
    try {
      ticket = ticketService.updateTicket(payment, paymentPrice, id, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(ticket);
    assertEquals("Ticket Order should exist!", error); // expected error message for service data
                                                       // validation.
  }

  @Test
  public void testupdateNullTicketCustomer() {
    boolean payment = false;
    double paymentPrice = 420.0;
    String aOrder = "ordercode";
    String aArtist = "artistcode";
    String aCustomer = null;
    Ticket ticket = null;
    String error = null;
    String id = TICKET_ID;
    try {
      ticket = ticketService.updateTicket(payment, paymentPrice, id, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNotNull(ticket);
    assertNull(error);
  }

  @Test
  public void testupdateBadTicketArtist() {
    boolean payment = false;
    double paymentPrice = 420.0;
    String aOrder = "ordercode";
    String aArtist = "artistcodefake";
    String aCustomer = "customercode";
    Ticket ticket = null;
    String error = null;
    String id = TICKET_ID;
    try {
      ticket = ticketService.updateTicket(payment, paymentPrice, id, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(ticket);
    assertEquals("Ticket Artist should exist!", error); // expected error message for service data
                                                        // validation.
  }

  @Test
  public void testupdateBadTicketCustomer() {
    boolean payment = false;
    double paymentPrice = 420.0;
    String aOrder = "ordercode";
    String aArtist = "artistcode";
    String aCustomer = "customercodefake";
    Ticket ticket = null;
    String error = null;
    String id = TICKET_ID;
    try {
      ticket = ticketService.updateTicket(payment, paymentPrice, id, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(ticket);
    assertEquals("Ticket Customer should exist!", error); // expected error message for service data
                                                          // validation.
  }

  @Test
  public void testupdateNullTicketArtist() {
    boolean payment = false;
    double paymentPrice = 420.0;
    String aOrder = "ordercode";
    String aArtist = null;
    String aCustomer = "customercode";
    Ticket ticket = null;
    String error = null;
    String id = TICKET_ID;
    try {
      ticket = ticketService.updateTicket(payment, paymentPrice, id, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNotNull(ticket);
    assertNull(error);
  }

  @Test
  public void testupdateNullTicketPaymentNegative() {
    boolean payment = false;
    double paymentPrice = -420.0;
    String aOrder = "ordercode";
    String aArtist = "artistcode";
    String aCustomer = "customercode";
    Ticket ticket = null;
    String error = null;
    String id = TICKET_ID;
    try {
      ticket = ticketService.updateTicket(payment, paymentPrice, id, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(ticket);
    assertNotNull(error); // expected error message for
                                                                      // service data
    // validation.
  }

  @Test
  public void testNullTicketSameArtistCustomer() {
    boolean payment = false;
    double paymentPrice = 420.0;
    String aOrder = "ordercode";
    String aArtist = "customercode";
    String aCustomer = "customercode";
    Ticket ticket = null;
    String error = null;
    String id = TICKET_ID;
    try {
      ticket = ticketService.updateTicket(payment, paymentPrice, id, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(ticket);
    assertEquals("Ticket customer and artist cannot be the same!", error); // expected error message
                                                                           // for service data
    // validation.
  }

  @Test
  public void testGetTicket() {
    boolean payment = false;
    double paymentPrice = 420.0;
    String aOrder = "ordercode";
    String aArtist = "artistcode";
    String aCustomer = "customercode";
    Ticket ticket = null;
    Ticket ticket2 = null;
    String id = null;
    try {
      ticket = ticketService.createTicket(payment, paymentPrice, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      fail();
    }
    assertNotNull(ticket);
    id = ticket.getIdCode();

    try {
      ticket2 = ticketService.getTicket(id);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      fail();
    }
    assertNotNull(ticket2);
    assertEquals(id, ticket2.getIdCode());

  }

  @Test
  public void testDeleteTicket() {

    assertTrue(ticketService.deleteTicket(TICKET_ID));
    assertFalse(ticketService.deleteTicket(""));
    // assertFalse(serviceAo.deleteArtOrder(null));

  }

}
