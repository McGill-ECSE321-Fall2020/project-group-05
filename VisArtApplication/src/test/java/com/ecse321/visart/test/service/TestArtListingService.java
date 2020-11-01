package com.ecse321.visart.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.ArtPiece;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.Manager;
import com.ecse321.visart.model.User;
import com.ecse321.visart.model.ArtListing.PostVisibility;
import com.ecse321.visart.model.ArtPiece.PieceLocation;
import com.ecse321.visart.repositories.ArtListingRepository;
import com.ecse321.visart.repositories.ArtListingRepository;
import com.ecse321.visart.repositories.EntityRepository;
import com.ecse321.visart.service.ArtListingService;
import com.ecse321.visart.service.ArtListingService;

@ExtendWith(MockitoExtension.class)
public class TestArtListingService {

  @Mock
  private ArtListingRepository alRepo;

  @Mock
  private EntityRepository entityRepo;

  @InjectMocks
  private ArtListingService serviceAl;

  private static final String AL_KEY = "MockTestAL";
  private static ArtListing artListingTest = null;
  private static final PostVisibility postVisibility = PostVisibility.Public;
  private static final String aDescription = "description";
  private static final String aTitle = "title";
  private static final User aUser = new User("a", "b", "c", "d", "e", "f", "g");
  private static final Customer aCustomer = new Customer("customerCode", aUser);
  private static final Artist aArtist = new Artist("artistCode", aCustomer);

  @BeforeEach
  public void setMockOutput() {
    // Mock the Repository methods, returning what we want to expect from the
    // database, instead of actually querying the database.

    lenient().when(alRepo.getArtListing(anyString())).thenAnswer(
        (InvocationOnMock invocation) -> {
          if (invocation.getArgument(0).equals(AL_KEY)) {
            ArtListing artListing1 = new ArtListing(postVisibility, aDescription, aTitle,
                AL_KEY, aArtist);
            return artListing1;
          } else {

            return null;
          }
        });
    // Whenever anything is saved, just return the parameter object
    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
    };

    lenient().when(alRepo.createArtListing(Mockito.any(PostVisibility.class), anyString(),
        anyString(), anyString(), Mockito.any(Artist.class)))
        .thenAnswer((InvocationOnMock invocation) -> {
          PostVisibility postVisibility = invocation.getArgument(0);
          String description = invocation.getArgument(1);
          String title = invocation.getArgument(2);
          String id = invocation.getArgument(3);
          Artist artist = invocation.getArgument(4);
          ArtListing artListing2 = new ArtListing(postVisibility, description, title, id,
              artist);
          ;
          return artListing2;
        });

    lenient().doAnswer((InvocationOnMock invocation) -> {
      artListingTest = invocation.getArgument(0);
      return artListingTest;
    }).when(alRepo).updateArtListing(any());

    lenient().when(alRepo.deleteArtListing(anyString())).thenAnswer((InvocationOnMock invocation) -> {
      if (invocation.getArgument(0).equals(AL_KEY)) {
        return true;
      } else {
        return false;
      }
    });

    // In the tests below, test the Service class's DATA VALIDATION on the
    // parameters that are given to it.
  }

  @Test
  public void testCreateArtListing() {

    ArtListing artListing = null;
    PostVisibility postVisibility = PostVisibility.Public;
    String description = "123";
    String title = "111122223333";
    String id = "1234";
    User aUser = new User("a", "b", "c", "d", "e", "f", "g");
    Customer aCustomer = new Customer("customerCode", aUser);
    Artist aArtist = new Artist("artistCode", aCustomer);

    try {
      artListing = serviceAl.createArtListing(postVisibility, description,
          title, aArtist);
    } catch (IllegalArgumentException e) {

      fail();
    }

    assertNotNull(artListing);

  }

  @Test
  public void testCreateArtListingNullVisibility() {
    PostVisibility Postvisibility = null;
    String aDescription = "123";
    String aTitle = "111122223333";
    User aUser = new User("a", "b", "c", "d", "e", "f", "g");
    Customer aCustomer = new Customer("customerCode", aUser);
    Artist aArtist = new Artist("artistCode", aCustomer);
    ArtListing artListing = null;
    String error = null;


    try {
      artListing = serviceAl.createArtListing(Postvisibility, aDescription, aTitle, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artListing);
    assertEquals("Post Visibility cannot be empty!", error); // expected error message for service
                                                             // data
    // validation.
  }

  @Test
  public void testCreateArtListingNullDescription() {

    PostVisibility Postvisibility = PostVisibility.Public;
    String aDescription = "";
    String aTitle = "111122223333";
    User aUser = new User("a", "b", "c", "d", "e", "f", "g");
    Customer aCustomer = new Customer("customerCode", aUser);
    Artist aArtist = new Artist("artistCode", aCustomer);
    ArtListing artListing = null;
    String error = null;


    try {
      artListing = serviceAl.createArtListing(Postvisibility, aDescription, aTitle, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artListing);
    assertEquals("Description cannot be empty!", error); // expected error message for service
                                                             // data
    // validation.
  }

  @Test
  public void testCreateArtListingNullTitle() {

    PostVisibility Postvisibility = PostVisibility.Public;
    String aDescription = "123";
    String aTitle = "";
    User aUser = new User("a", "b", "c", "d", "e", "f", "g");
    Customer aCustomer = new Customer("customerCode", aUser);
    Artist aArtist = new Artist("artistCode", aCustomer);
    ArtListing artListing = null;
    String error = null;


    try {
      artListing = serviceAl.createArtListing(Postvisibility, aDescription, aTitle, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artListing);
    assertEquals("Title cannot be empty!", error); // expected error message for service
                                                             // data
    // validation.                                         // data
  } // validation.

  @Test
  public void testCreateArtListingBadTracker() {

    PostVisibility Postvisibility = PostVisibility.Public;
    String aDescription = "123";
    String aTitle = "111122223333";
    Artist aArtist = null;
    ArtListing artListing = null;
    String error = null;


    try {
      artListing = serviceAl.createArtListing(Postvisibility, aDescription, aTitle, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artListing);
    assertEquals("Artist cannot be empty!", error); // expected error message for service
                                                             // data
    // validation.                                                         // service data
  }

  @Test
  public void testGetListing() {
    PostVisibility Postvisibility = PostVisibility.Public;
    String aDescription = "123";
    String aTitle = "111122223333";
    User aUser = new User("a", "b", "c", "d", "e", "f", "g");
    Customer aCustomer = new Customer("customerCode", aUser);
    Artist aArtist = new Artist("artistCode", aCustomer);
    ArtListing artListing = null;
    String error = null;


    try {
      artListing = serviceAl.createArtListing(Postvisibility, aDescription, aTitle, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }                                                      // data

    try {
      artListing = serviceAl.getArtListing(artListing.getIdCode());
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      fail();
    }

    assertNotNull(artListing);


  }

  @Test
  public void testGetNonExistingArtListing() {
    PostVisibility Postvisibility = PostVisibility.Public;
    String aDescription = "";
    String aTitle = "111122223333";
    User aUser = new User("a", "b", "c", "d", "e", "f", "g");
    Customer aCustomer = new Customer("customerCode", aUser);
    Artist aArtist = new Artist("artistCode", aCustomer);
    ArtListing artListing = null;
    String error = null;
    String idFake = "fake";


    try {
      artListing = serviceAl.createArtListing(Postvisibility, aDescription, aTitle, aArtist);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artListing);
    assertEquals("Description cannot be empty!", error); // expected error message for service
                                                             // data
    // validation.

    try {
      artListing = serviceAl.getArtListing(idFake);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      fail();
    }

    assertNull(artListing);

  }

  @Test
  public void testUpdateArtListingNullID() {

    Boolean delivery = true;
    PieceLocation Piecelocation = PieceLocation.AtGallery;
    String target = "123";
    String tracker = "111122223333";
    User aUser = new User("a", "b", "c", "d", "e", "f", "g");
    Customer aCustomer = new Customer("customerCode", aUser);
    Artist aArtist = new Artist("artistCode", aCustomer);
    ArtListing TEST_LISTING = new ArtListing(PostVisibility.Draft, "name",
        "listing", "mockcode",
        aArtist);
    ArtPiece AP_TEST = new ArtPiece(Piecelocation, "locationtest",
        "123", TEST_LISTING);
    ArtListing artListing = null;
    String idNull = null;
    String error = null;

    try {
      artListing = serviceAo.updateArtListing(idNull, delivery, Piecelocation, target, tracker,
          AP_TEST.getIdCode());
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertNull(artListing);
    assertEquals("User id code cannot be empty!", error); // expected error message for service data
                                                          // validation.
  }

  @Test
  public void testUpdateArtListingNullLocation() {
    Boolean delivery = true;
    PieceLocation Piecelocation = null;
    String target = "123";
    String tracker = "111122223333";
    String id = "1234";
    String error = null;
    User aUser = new User("a", "b", "c", "d", "e", "f", "g");
    Customer aCustomer = new Customer("customerCode", aUser);
    Artist aArtist = new Artist("artistCode", aCustomer);
    ArtListing TEST_LISTING = new ArtListing(PostVisibility.Draft, "name",
        "listing", "mockcode",
        aArtist);
    ArtPiece AP_TEST = new ArtPiece(Piecelocation, "locationtest",
        "123", TEST_LISTING);
    ArtListing artListing = null;

    try {
      artListing = serviceAo.updateArtListing(id, delivery, Piecelocation, target, tracker,
          AP_TEST.getIdCode());
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artListing);
    assertEquals("Target Location cannot be empty!", error); // expected error message for service
                                                             // data
    // validation.
  }

  @Test
  public void testUpdateArtListingNullAddress() {

    Boolean delivery = true;
    PieceLocation Piecelocation = PieceLocation.AtGallery;
    String target = null;
    String tracker = "111122223333";
    String id = "1234";
    String error = null;
    User aUser = new User("a", "b", "c", "d", "e", "f", "g");
    Customer aCustomer = new Customer("customerCode", aUser);
    Artist aArtist = new Artist("artistCode", aCustomer);
    ArtListing TEST_LISTING = new ArtListing(PostVisibility.Draft, "name",
        "listing", "mockcode",
        aArtist);
    ArtPiece AP_TEST = new ArtPiece(Piecelocation, "locationtest",
        "123", TEST_LISTING);
    ArtListing artListing = null;

    try {
      artListing = serviceAo.updateArtListing(id, delivery, Piecelocation, target,
          tracker, AP_TEST.getIdCode());
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artListing);
    assertEquals("Target Address cannot be empty!", error); // expected error message for service
                                                            // data
    // validation.
  }

  @Test
  public void testUpdateArtListingNullDelivery() {

    Boolean delivery = true;
    PieceLocation Piecelocation = PieceLocation.AtGallery;
    String target = "123";
    String tracker = null;
    String id = "1234";
    String error = null;
    User aUser = new User("a", "b", "c", "d", "e", "f", "g");
    Customer aCustomer = new Customer("customerCode", aUser);
    Artist aArtist = new Artist("artistCode", aCustomer);
    ArtListing TEST_LISTING = new ArtListing(PostVisibility.Draft, "name",
        "listing", "mockcode",
        aArtist);
    ArtPiece AP_TEST = new ArtPiece(Piecelocation, "locationtest",
        "123", TEST_LISTING);
    ArtListing artListing = null;

    try {
      artListing = serviceAo.updateArtListing(id, delivery,
          Piecelocation, target, tracker, AP_TEST.getIdCode());
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artListing);
    assertEquals("Delivery Tracker cannot be empty!", error); // expected error message for service
                                                              // data
  } // validation.

  @Test
  public void updateArtListingBadTracker() {

    Boolean delivery = true;
    PieceLocation Piecelocation = PieceLocation.AtGallery;
    String target = "123";
    String tracker = "ER24";
    String id = "1234";
    String error = null;
    User aUser = new User("a", "b", "c", "d", "e", "f", "g");
    Customer aCustomer = new Customer("customerCode", aUser);
    Artist aArtist = new Artist("artistCode", aCustomer);
    ArtListing TEST_LISTING = new ArtListing(PostVisibility.Draft, "name",
        "listing", "mockcode",
        aArtist);
    ArtPiece AP_TEST = new ArtPiece(Piecelocation, "locationtest",
        "123", TEST_LISTING);
    ArtListing artListing = null;

    try {
      artListing = serviceAo.updateArtListing(id, delivery,
          Piecelocation, target, tracker,
          AP_TEST.getIdCode());
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artListing);
    assertEquals("Delivery Tracker must contain 12 digits", error); // expected error message for
                                                                    // service data
  }

  @Test
  public void testUpdateArtListingNullPiece() {

    Boolean delivery = true;
    PieceLocation Piecelocation = PieceLocation.AtGallery;
    String target = "123";
    String tracker = "111122223333";
    String id = "1234";
    String error = null;
    ArtPiece AP_TEST = null;
    ArtListing artListing = null;

    try {
      artListing = serviceAo.updateArtListing(id, delivery, Piecelocation, target, tracker,
          AP_TEST.getIdCode());
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artListing);
    assertEquals("Art Piece cannot be empty!", error); // expected error message for service data
  }

  @Test
  public void testUpdateCreateArtListingValidTracker() {

    Boolean delivery = true;
    PieceLocation Piecelocation = PieceLocation.AtGallery;
    String target = "123";
    String tracker = "111122224444";
    User aUser = new User("a", "b", "c", "d", "e", "f", "g");
    Customer aCustomer = new Customer("customerCode", aUser);
    Artist aArtist = new Artist("artistCode", aCustomer);
    ArtListing TEST_LISTING = new ArtListing(PostVisibility.Draft, "name",
        "listing", "mockcode",
        aArtist);
    ArtPiece AP_TEST = new ArtPiece(Piecelocation, "locationtest",
        "123", TEST_LISTING);
    ArtListing artListing = null;

    try {
      artListing = serviceAo.updateArtListing(AO_KEY, delivery,
          Piecelocation, target, tracker,
          AP_TEST.getIdCode());
    } catch (IllegalArgumentException e) {

    }
    assertNotNull(artListing);
    assertEquals(artListing.getDeliveryTracker(), tracker); // expected error message for
                                                          // service data
  }

  @Test
  public void testUpdateCreateArtListingValidTarget() {

    Boolean delivery = true;
    PieceLocation Piecelocation = PieceLocation.AtGallery;
    String target = "123";
    String tracker = "111122224444";
    User aUser = new User("a", "b", "c", "d", "e", "f", "g");
    Customer aCustomer = new Customer("customerCode", aUser);
    Artist aArtist = new Artist("artistCode", aCustomer);
    ArtListing TEST_LISTING = new ArtListing(PostVisibility.Draft, "name",
        "listing", "mockcode",
        aArtist);
    ArtPiece AP_TEST = new ArtPiece(Piecelocation, "locationtest",
        "123", TEST_LISTING);
    ArtListing artListing = null;

    try {
      artListing = serviceAo.updateArtListing(AO_KEY, delivery,
          Piecelocation, target, tracker,
          AP_TEST.getIdCode());
    } catch (IllegalArgumentException e) {

    }
    assertNotNull(artListing);
    assertEquals(artListing.getTargetAddress(), target); // expected error message for
                                                       // service data
  }

  @Test
  public void testUpdateCreateArtListingValidPieceLocation() {

    Boolean delivery = true;
    PieceLocation Piecelocation = PieceLocation.AtGallery;
    String target = "123";
    String tracker = "111122224444";
    User aUser = new User("a", "b", "c", "d", "e", "f", "g");
    Customer aCustomer = new Customer("customerCode", aUser);
    Artist aArtist = new Artist("artistCode", aCustomer);
    ArtListing TEST_LISTING = new ArtListing(PostVisibility.Draft, "name",
        "listing", "mockcode",
        aArtist);
    ArtPiece AP_TEST = new ArtPiece(Piecelocation, "locationtest",
        "123", TEST_LISTING);
    ArtListing artListing = null;

    try {
      artListing = serviceAo.updateArtListing(AO_KEY, delivery,
          Piecelocation, target, tracker,
          AP_TEST.getIdCode());
    } catch (IllegalArgumentException e) {

    }
    assertNotNull(artListing);
    assertEquals(artListing.getTargetLocation(), Piecelocation); // expected error message for
                                                               // service data
  }

  @Test
  public void testUpdateCreateArtListingValidDelivery() {

    Boolean delivery = true;
    PieceLocation Piecelocation = PieceLocation.AtGallery;
    String target = "123";
    String tracker = "111122224444";
    User aUser = new User("a", "b", "c", "d", "e", "f", "g");
    Customer aCustomer = new Customer("customerCode", aUser);
    Artist aArtist = new Artist("artistCode", aCustomer);
    ArtListing TEST_LISTING = new ArtListing(PostVisibility.Draft, "name",
        "listing", "mockcode",
        aArtist);
    ArtPiece AP_TEST = new ArtPiece(Piecelocation, "locationtest",
        "123", TEST_LISTING);
    ArtListing artListing = null;

    try {
      artListing = serviceAo.updateArtListing(AO_KEY, delivery,
          Piecelocation, target, tracker,
          AP_TEST.getIdCode());
    } catch (IllegalArgumentException e) {

    }
    assertNotNull(artListing);
    assertEquals(artListing.getIsDelivered(), delivery); // expected error message for
                                                       // service data
  }

  @Test
  public void testDeleteArtListing() {

    assertTrue(serviceAo.deleteArtListing(AO_KEY));
    assertFalse(serviceAo.deleteArtListing(""));
    // assertFalse(serviceAo.deleteArtListing(null));

  }

}
