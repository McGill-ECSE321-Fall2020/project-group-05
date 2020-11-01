package com.ecse321.visart.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import org.mockito.stubbing.Answer;
import com.ecse321.visart.model.ArtOrder;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.User;
import com.ecse321.visart.model.Ticket;
import com.ecse321.visart.repositories.EntityRepository;
import com.ecse321.visart.repositories.TicketRepository;
import com.ecse321.visart.service.TicketService;

@ExtendWith(MockitoExtension.class)
public class TestTicketService {

  @Mock
  private TicketRepository ticketRepo;

  @Mock
  private EntityRepository entityRepo;

  @InjectMocks
  private TicketService ticketService;
  
  private static final boolean TICKET_PAYMENT_BOOL = true;
  private static final double TICKET_AMOUNT = 420.69;
  private static final String TICKET_ID = "////ticketid////";
  private static final ArtOrder TICKET_ORDER = new ArtOrder();
  private static final User artistUser = new User("code1","r@mcgill.ca","riad","riadriad","pass","pic","desc");
  private static final User customerUser = new User("code2","ye@mcgill.ca","kanye","kanyekanye","passye","picye","descye");
  private static final Customer TICKET_CUSTOMER = new Customer("sheepcode",customerUser);
  private static final Customer artistCustomer = new Customer("artsycode",artistUser);
  private static final Artist TICKET_ARTIST = new Artist("artsycode2",artistCustomer);
  private static Ticket ticketTest = null;
  
  @BeforeEach
  public void setMockOutput() {
    // Mock the Repository methods, returning what we want to expect from the
    // database, instead of actually querying the database.

    lenient().when(ticketRepo.getTicket(anyString())).thenAnswer(
        (InvocationOnMock invocation) -> {
          if (invocation.getArgument(0).equals(TICKET_ID)) {
            Ticket myTicket = new Ticket(TICKET_PAYMENT_BOOL, TICKET_AMOUNT, TICKET_ID, TICKET_ORDER,TICKET_CUSTOMER,TICKET_ARTIST);
            return myTicket;
          } else {
            return null;
          }
        });
    // Whenever anything is saved, just return the parameter object
    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
    };

    lenient().when(ticketRepo.createTicket(anyBoolean(), anyDouble(), anyString(),any(),
    		any(),any())).thenAnswer((InvocationOnMock invocation) -> {
          boolean payment = (boolean)invocation.getArgument(0);
          double paymentPrice = (double)invocation.getArgument(1);
          String id = (String)invocation.getArgument(2);
          ArtOrder order = (ArtOrder)invocation.getArgument(3);
          Customer customer = (Customer)invocation.getArgument(4);
          Artist artist = (Artist)invocation.getArgument(5);
          Ticket ticket = new Ticket(payment, paymentPrice, id, order, customer, artist);
          return ticket;
        });
    
    lenient().doAnswer((InvocationOnMock invocation) -> {
        ticketTest = invocation.getArgument(0);
        return ticketTest;
      }).when(ticketRepo).updateTicket(any());

    lenient().when(ticketRepo.deleteTicket(anyString())).thenAnswer((InvocationOnMock invocation) -> {
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

    String id = "id";
    boolean payment = false;
    double paymentPrice = 420.0;
    User aUser = new User("a","b","c","d","e","f","g");
    User aUser2 = new User("h","i","j","k","l","m","n");
    Customer aCustomer = new Customer("customerCode", aUser);
    Customer aCustomer2 = new Customer("artistcustomerCode", aUser2);
    Artist aArtist = new Artist("artistCode", aCustomer2);
    ArtOrder aOrder = new ArtOrder();
    
    Ticket ticket = null;
    try {
      ticket = ticketService.createTicket(payment, paymentPrice, id, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      fail();
    }
    assertNotNull(ticket);
    assertEquals(id, ticket.getIdCode());
  }

  @Test
  public void testCreateNullTicketID() {
    String error = null;
    Ticket ticket = null;
    boolean payment = true;
    double paymentPrice = 120;
    String id = null;
    ArtOrder aOrder = new ArtOrder();
    User aUser = new User("a","b","c","d","e","f","g");
    User aUser2 = new User("h","i","j","k","l","m","n");
    Customer aCustomer = new Customer("customerCode", aUser);
    Customer aCustomer2 = new Customer("artistcustomerCode", aUser2);
    Artist aArtist = new Artist("artistCode", aCustomer2);
    try {
      ticket = ticketService.createTicket(payment, paymentPrice, id, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(ticket);
    assertEquals("Ticket id code cannot be empty!", error); // expected error message for service data
                                                          // validation.
  }
  
  @Test
  public void testCreateNullTicketOrder() {
    String error = null;
    Ticket ticket = null;
    boolean payment = true;
    double paymentPrice = 120;
    String id = "mockcode";
    ArtOrder aOrder = null;
    User aUser = new User("a","b","c","d","e","f","g");
    User aUser2 = new User("h","i","j","k","l","m","n");
    Customer aCustomer = new Customer("customerCode", aUser);
    Customer aCustomer2 = new Customer("artistcustomerCode", aUser2);
    Artist aArtist = new Artist("artistCode", aCustomer2);
    try {
      ticket = ticketService.createTicket(payment, paymentPrice, id, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(ticket);
    assertEquals("Ticket order cannot be empty!", error); // expected error message for service data
                                                          // validation.
  }
  
  @Test
  public void testCreateNullTicketCustomer() {
    String error = null;
    Ticket ticket = null;
    boolean payment = true;
    double paymentPrice = 120;
    String id = "mockcode";
    ArtOrder aOrder = new ArtOrder();
    //User aUser = new User("a","b","c","d","e","f","g");
    User aUser2 = new User("h","i","j","k","l","m","n");
    Customer aCustomer = null;
    Customer aCustomer2 = new Customer("artistcustomerCode", aUser2);
    Artist aArtist = new Artist("artistCode", aCustomer2);
    try {
      ticket = ticketService.createTicket(payment, paymentPrice, id, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(ticket);
    assertEquals("Ticket customer cannot be empty!", error); // expected error message for service data
                                                          // validation.
  }
  
  @Test
  public void testCreateNullTicketArtist() {
    String error = null;
    Ticket ticket = null;
    boolean payment = true;
    double paymentPrice = 120;
    String id = "mockcode";
    ArtOrder aOrder = new ArtOrder();
    User aUser = new User("a","b","c","d","e","f","g");
    //User aUser2 = new User("h","i","j","k","l","m","n");
    Customer aCustomer = new Customer("customerCode", aUser);
    //Customer aCustomer2 = new Customer("artistcustomerCode", aUser2);
    Artist aArtist = null;
    try {
      ticket = ticketService.createTicket(payment, paymentPrice, id, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(ticket);
    assertEquals("Ticket artist cannot be empty!", error); // expected error message for service data
                                                          // validation.
  }
  
  @Test
  public void testCreateNullTicketPaymentNegative() {
    String error = null;
    Ticket ticket = null;
    boolean payment = true;
    double paymentPrice = -120;
    String id = "mockcode";
    ArtOrder aOrder = new ArtOrder();
    User aUser = new User("a","b","c","d","e","f","g");
    User aUser2 = new User("h","i","j","k","l","m","n");
    Customer aCustomer = new Customer("customerCode", aUser);
    Customer aCustomer2 = new Customer("artistcustomerCode", aUser2);
    Artist aArtist = new Artist("artistCode", aCustomer2);
    try {
      ticket = ticketService.createTicket(payment, paymentPrice, id, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(ticket);
    assertEquals("Ticket payment amount cannot be negative!", error); // expected error message for service data
                                                          // validation.
  }
  
  @Test
  public void testCreateNullTicketSameArtistCustomer() {
    String error = null;
    Ticket ticket = null;
    boolean payment = true;
    double paymentPrice = -120;
    String id = "mockcode";
    ArtOrder aOrder = new ArtOrder();
    User aUser = new User("a","b","c","d","e","f","g");
    User aUser2 = new User("h","i","j","k","l","m","n");
    Customer aCustomer = new Customer("customerCode", aUser);
    Customer aCustomer2 = new Customer("artistcustomerCode", aUser2);
    Artist aArtist = new Artist("artistCode", aCustomer);
    try {
      ticket = ticketService.createTicket(payment, paymentPrice, id, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(ticket);
    assertEquals("Ticket customer and artist cannot be the same!", error); // expected error message for service data
                                                          // validation.
  }
  
  @Test
  public void testCreateNullTicketAll() {
    String error = null;
    Ticket ticket = null;
    boolean payment = false;
    double paymentPrice = 0;
    String id = null;
    ArtOrder aOrder = null;
    Customer aCustomer = null;
    Artist aArtist = null;
    try {
      ticket = ticketService.createTicket(payment, paymentPrice, id, aOrder, aCustomer, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(ticket);
    //assertEquals("Ticket id code cannot be empty!", error); // expected error message for service data
                                                          // validation.
  }
  
}




