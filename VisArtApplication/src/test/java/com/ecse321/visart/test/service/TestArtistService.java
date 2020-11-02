package com.ecse321.visart.test.service;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.User;
import com.ecse321.visart.repositories.ArtListingRepository;
import com.ecse321.visart.repositories.ArtistRepository;
import com.ecse321.visart.repositories.CustomerRepository;
import com.ecse321.visart.repositories.EntityRepository;
import com.ecse321.visart.repositories.TicketRepository;
import com.ecse321.visart.service.ArtistService;

@ExtendWith(MockitoExtension.class)
public class TestArtistService {

  private static final String CUSTOMER_ID = "customerId";

  private static final String USER_DESCRIPTION = "I'm a great artist";

  private static final String USER_ID = "userId";

  private static final String USER_EMAIL = "bob@gmail.com";

  private static final String USER_DISPLAY = "bobster1989";

  private static final String USER_USERNAME = "bobsterBobs";

  private static final String USER_PASSWORD = "cheesecake";

  private static final String USER_PROFILE_PIC = "https://thepic.link/g.jpg";

  @Mock
  ArtistRepository artistRepo;

  @Mock
  CustomerRepository customerRepo;

  @Mock
  ArtListingRepository artListingRepo;

  @Mock
  TicketRepository ticketRepo;

  @Mock
  EntityRepository entityRepo;

  @InjectMocks
  ArtistService service;

  Customer customer; // customer object of the artist
  Artist artist;
  Artist placeholderArtist; // artist to hold objects intermediately
  Map<String, ArtListing> listings;
  String[] listingIds = { "posting1", "posting2", "posting3" };

  @BeforeEach
  void setupBeforeEachTest() {
    // customer is set before each test, retrieved by any methods below
    customer = new Customer(CUSTOMER_ID, new User(USER_ID, USER_EMAIL, USER_DISPLAY, USER_USERNAME,
        USER_PASSWORD, USER_PROFILE_PIC, USER_DESCRIPTION));
    placeholderArtist = new Artist("phArtist",
        new Customer("phCustomer",
            new User("phUser", USER_EMAIL, "ph" + USER_DISPLAY, "ph" + USER_USERNAME,
                "ph" + USER_PASSWORD, "ph" + USER_PROFILE_PIC, "ph" + USER_DESCRIPTION)));
    listings = Arrays.stream(listingIds).map((id) -> {
      ArtListing al = new ArtListing();
      al.setIdCode(id);
      return al;
    }).collect(Collectors.toMap(al->al.getIdCode(), al->al));

    lenient().when(customerRepo.getCustomer(anyString()))
        .thenAnswer((InvocationOnMock invocation) -> {
          if (invocation.getArgument(0).equals(CUSTOMER_ID))
            return customer;
          else
            return null;
        });

    lenient().when(artistRepo.createArtist(anyString(), any()))
        .thenAnswer((InvocationOnMock invocation) -> {
          artist = new Artist(invocation.getArgument(0), invocation.getArgument(1));
          return artist;
        });

    lenient().when(artistRepo.getArtist(anyString(), anyBoolean(), anyBoolean()))
        .thenAnswer((InvocationOnMock invocation) -> {
          if (invocation.getArgument(0).equals(artist.getIdCode())) {
            return artist;
          } else {
            return null;
          }
        });
    lenient().when(artListingRepo.getArtListing(anyString())).thenAnswer((InvocationOnMock in)->{
      return listings.get(in.getArgument(0));
    });

  }

  @Test
  void testCreateArtistCustomer() {
    String error = "";
    try {
      Artist a0 = service.createArtist(null);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("Customer id code cannot be empty!", error);

    try {
      Artist a0 = service.createArtist("");
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("Customer id code cannot be empty!", error);

    try {
      Artist a1 = service.createArtist("not a real id");
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("Customer id must be valid id of existing customer!", error);

    try {
      Artist a2 = service.createArtist(CUSTOMER_ID);
      assertEquals(36, a2.getIdCode().length());
    } catch (IllegalArgumentException e) {
      fail();
    }

  }

  @Test
  void testAddRemoveArtListing() {
    Artist a1 = service.createArtist(CUSTOMER_ID);
    service.addListings(a1.getIdCode(), Arrays.asList(listingIds));
    assertEquals(3,artist.getPostedListings().size());
  }

  @Test
  void testAddRemoveSoldTickets() {
    
  }

  @Test
  void testGetArtist() {

  }

  @Test
  void deleteArtist() {

  }

  @Test
  void getAllArtists() {

  }
}
