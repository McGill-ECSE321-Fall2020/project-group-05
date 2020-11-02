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
import com.ecse321.visart.model.Tag;
import com.ecse321.visart.model.User;
import com.ecse321.visart.model.Ticket;
import com.ecse321.visart.repositories.ArtListingRepository;
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
    
    lenient().when(aoRepo.getArtOrder(anyString())).thenAnswer((InvocationOnMock invocation) -> {
    	String id = invocation.getArgument(0);
    	return TICKET_ORDER;
    });
    
    lenient().when(aRepo.getArtist(anyString())).thenAnswer((InvocationOnMock invocation) -> {
    	String id = invocation.getArgument(0);
    	return TICKET_ARTIST;
    });
    
    lenient().when(cRepo.getCustomer(anyString())).thenAnswer((InvocationOnMock invocation) -> {
    	String id = invocation.getArgument(0);
    	return TICKET_CUSTOMER;
    });

    lenient().when(ticketRepo.createTicket(anyBoolean(), anyDouble(), anyString(),any(),
    		any(),any())).thenAnswer((InvocationOnMock invocation) -> {
          boolean payment = (boolean)invocation.getArgument(0);
          double paymentPrice = (double)invocation.getArgument(1);
          String id = (String)invocation.getArgument(2);
          ArtOrder order = (ArtOrder)invocation.getArgument(3);
          Customer customer = (Customer)invocation.getArgument(4);
          Artist artist = (Artist)invocation.getArgument(5);
          Ticket ticket = new Ticket(payment, paymentPrice, TICKET_ID, order, customer, artist);
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
    assertEquals("Ticket customer cannot be empty!", error); // expected error message for service data
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
    assertEquals("Ticket artist cannot be empty!", error); // expected error message for service data
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
    assertEquals("Ticket payment amount cannot be negative!", error); // expected error message for service data
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
    assertEquals("Ticket customer and artist cannot be the same!", error); // expected error message for service data
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
    assertNull(ticket);
    //assertEquals("Ticket id code cannot be empty!", error); // expected error message for service data
                                                          // validation.
  }
  
  @Test
  public void testUpdateTicket() {
	  
  }
  
  @Test
  public void testGetTicket() {
    boolean payment = false;
	double paymentPrice = 420.0;
	String aOrder = "ordercode2";
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
  
}




