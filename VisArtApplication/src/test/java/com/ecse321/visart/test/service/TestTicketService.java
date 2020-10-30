package com.ecse321.visart.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.ArtOrder;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.Tag;
import com.ecse321.visart.model.User;
import com.ecse321.visart.model.ArtListing.PostVisibility;
import com.ecse321.visart.model.Tag.TagType;
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
  private TicketService tagService;
  
  private static final boolean TICKET_PAYMENT_BOOL = true;
  private static final double TICKET_AMOUNT = 420.69;
  private static final String TICKET_CODE = "////ticketid////";
  private static final ArtOrder TICKET_ORDER = new ArtOrder();
  private static final User artistUser = new User("code1","r@mcgill.ca","riad","riadriad","pass","pic","desc");
  private static final User customerUser = new User("code2","ye@mcgill.ca","kanye","kanyekanye","passye","picye","descye");
  private static final Customer TICKET_CUSTOMER = new Customer("sheepcode",customerUser);
  private static final Customer artistCustomer = new Customer("artsycode",artistUser);
  private static final Artist TICKET_ARTIST = new Artist("artsycode2",artistCustomer);
  
  
  @BeforeEach
  public void setMockOutput() {
    // Mock the Repository methods, returning what we want to expect from the
    // database, instead of actually querying the database.

    lenient().when(ticketRepo.getTicket(anyString())).thenAnswer(
        (InvocationOnMock invocation) -> {
          if (invocation.getArgument(0).equals(TICKET_CODE)) {
            Ticket myTicket = new Ticket(TICKET_PAYMENT_BOOL, TICKET_AMOUNT, TICKET_CODE, TICKET_ORDER,TICKET_CUSTOMER,TICKET_ARTIST);
            return myTicket;
          } else {
            return null;
          }
        });
    // Whenever anything is saved, just return the parameter object
    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
    };

    lenient().when(ticketRepo.createTicket(false, 0, anyString(),any(),
    		any(),any())).thenAnswer((InvocationOnMock invocation) -> {
          String id = invocation.getArgument(2);
          TagType type = (TagType)invocation.getArgument(0);
          ArtListing listing = (ArtListing)invocation.getArgument(3);
          Tag tag = new Tag(type, id, id, listing);
          return tag;
        });

    // In the tests below, test the Service class's DATA VALIDATION on the
    // parameters that are given to it.
   
  }

  @Test
  public void testCreateTicket() {
    // assertEquals(0, service.getAllUsers().size());

    String keyword = "key";
    TagType type = TagType.Category;
    User aUser = new User("a","b","c","d","e","f","g");
    Customer aCustomer = new Customer("customerCode", aUser);
    Artist aArtist = new Artist("artistCode", aCustomer);
    ArtListing listing = new ArtListing(PostVisibility.Draft, "name", "listing", "mockcode",
  	      aArtist);
    
    Tag tag = null;
    try {
      tag = tagService.createTag(type, keyword, keyword, listing);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      fail();
    }
    assertNotNull(tag);
    assertEquals(keyword, tag.getIdCode());
  }

  @Test
  public void testCreateNullTag() {
    String error = null;
	String keyword = null;
	TagType type = null;
    Tag tag = null;
    ArtListing listing = null;
    try {
      tag = tagService.createTag(type, keyword, keyword, listing);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(tag);
    assertEquals("Tag id code cannot be empty!", error); // expected error message for service data
                                                          // validation.
  }
}




