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
import com.ecse321.visart.model.ArtOrder;
import com.ecse321.visart.model.ArtPiece;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.Manager;
import com.ecse321.visart.model.User;
import com.ecse321.visart.model.ArtListing.PostVisibility;
import com.ecse321.visart.model.ArtPiece.PieceLocation;
import com.ecse321.visart.repositories.ArtListingRepository;
import com.ecse321.visart.repositories.ArtOrderRepository;
import com.ecse321.visart.repositories.EntityRepository;
import com.ecse321.visart.service.ArtListingService;
import com.ecse321.visart.service.ArtOrderService;

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
            ArtListing artOrder1 = new ArtListing(postVisibility, aDescription, aTitle,
                AL_KEY, aArtist);
            return artOrder1;
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
          Boolean delivery = invocation.getArgument(0);
          PieceLocation location = invocation.getArgument(1);
          String target = invocation.getArgument(2);
          String tracker = invocation.getArgument(3);
          String id = invocation.getArgument(4);
          ArtPiece artpiece = invocation.getArgument(5);
          ArtOrder artOrder2 = new ArtOrder(delivery, location, target,
              tracker, id, artpiece);
          ;
          return artOrder2;
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
  public void testCreateArtOrder() {

    ArtOrder artOrder = null;
    Boolean delivery = true;
    PieceLocation Piecelocation = PieceLocation.AtGallery;
    String target = "123";
    String tracker = "111122223333";
    String id = "1234";
    User aUser = new User("a", "b", "c", "d", "e", "f", "g");
    Customer aCustomer = new Customer("customerCode", aUser);
    Artist aArtist = new Artist("artistCode", aCustomer);
    ArtListing TEST_LISTING = new ArtListing(PostVisibility.Draft, "name",
        "listing", "mockcode",
        aArtist);
    ArtPiece AP_TEST = new ArtPiece(Piecelocation, "locationtest",
        "123", TEST_LISTING);

    try {
      artOrder = serviceAo.createArtOrder(delivery, Piecelocation,
          target, tracker, AP_TEST.getIdCode());
    } catch (IllegalArgumentException e) {

      fail();
    }

    assertNotNull(artOrder);
    assertEquals(artOrder.getIdCode(), id);

  }

  @Test
  public void testCreateArtOrderNullID() {

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
    ArtOrder artOrder = null;
    String idNull = null;
    String error = null;

    try {
      artOrder = serviceAo.createArtOrder(delivery, Piecelocation, target, tracker,
          AP_TEST.getIdCode());
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artOrder);
    assertEquals("User id code cannot be empty!", error); // expected error message for service data
                                                          // validation.
  }

  @Test
  public void testCreateArtOrderNullLocation() {
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
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.createArtOrder(delivery, Piecelocation, target, tracker,
          AP_TEST.getIdCode());
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artOrder);
    assertEquals("Target Location cannot be empty!", error); // expected error message for service
                                                             // data
    // validation.
  }

  @Test
  public void testCreateArtOrderNullAddress() {

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
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.createArtOrder(delivery, Piecelocation, target, tracker,
          AP_TEST.getIdCode());
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artOrder);
    assertEquals("Target Address cannot be empty!", error); // expected error message for service
                                                            // data
    // validation.
  }

  @Test
  public void testCreateArtOrderNullDelivery() {

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
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.createArtOrder(delivery, Piecelocation,
          target, tracker, AP_TEST.getIdCode());
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artOrder);
    assertEquals("Delivery Tracker cannot be empty!", error); // expected error message for service
                                                              // data
  } // validation.

  @Test
  public void testCreateArtOrderBadTracker() {

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
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.createArtOrder(delivery, Piecelocation,
          target, tracker,
          AP_TEST.getIdCode());
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artOrder);
    assertEquals("Delivery Tracker must contain 12 digits", error); // expected error message for
                                                                    // service data
  }

  @Test
  public void testCreateArtOrderNullPiece() {

    Boolean delivery = true;
    PieceLocation Piecelocation = PieceLocation.AtGallery;
    String target = "123";
    String tracker = "111122223333";
    String id = "1234";
    String error = null;
    ArtPiece AP_TEST = null;
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.createArtOrder(delivery, Piecelocation, target, tracker,
          AP_TEST.getIdCode());
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artOrder);
    assertEquals("Art Piece cannot be empty!", error); // expected error message for service data
  }

  @Test
  public void testGetOrder() {
    ArtOrder artOrder = null;
    Boolean delivery = true;
    PieceLocation Piecelocation = PieceLocation.AtGallery;
    String target = "123";
    String tracker = "111122223333";
    String id = AO_KEY;
    User aUser = new User("a", "b", "c", "d", "e", "f", "g");
    Customer aCustomer = new Customer("customerCode", aUser);
    Artist aArtist = new Artist("artistCode", aCustomer);
    ArtListing TEST_LISTING = new ArtListing(PostVisibility.Draft, "name",
        "listing", "mockcode",
        aArtist);
    ArtPiece AP_TEST = new ArtPiece(Piecelocation, "locationtest",
        "123", TEST_LISTING);

    try {
      artOrder = serviceAo.createArtOrder(delivery, Piecelocation,
          target, tracker, AP_TEST.getIdCode());
    } catch (IllegalArgumentException e) {

      fail();
    }

    // artOrder = null;

    try {
      artOrder = serviceAo.getArtOrder(id);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      fail();
    }

    assertNotNull(artOrder);

    assertEquals(id, artOrder.getIdCode());

  }

  @Test
  public void testGetNonExistingArtOrder() {
    ArtOrder artOrder = null;
    Boolean delivery = true;
    PieceLocation Piecelocation = PieceLocation.AtGallery;
    String target = "123";
    String tracker = "111122223333";
    String id = AO_KEY;
    String idFake = "fake";
    User aUser = new User("a", "b", "c", "d", "e", "f", "g");
    Customer aCustomer = new Customer("customerCode", aUser);
    Artist aArtist = new Artist("artistCode", aCustomer);
    ArtListing TEST_LISTING = new ArtListing(PostVisibility.Draft, "name",
        "listing", "mockcode",
        aArtist);
    ArtPiece AP_TEST = new ArtPiece(Piecelocation, "locationtest",
        "123", TEST_LISTING);

    try {
      artOrder = serviceAo.createArtOrder(delivery, Piecelocation,
          target, tracker, AP_TEST.getIdCode());
    } catch (IllegalArgumentException e) {

      fail();
    }

    // artOrder = null;

    try {
      artOrder = serviceAo.getArtOrder(idFake);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      fail();
    }

    assertNull(artOrder);

  }

  @Test
  public void testUpdateArtOrderNullID() {

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
    ArtOrder artOrder = null;
    String idNull = null;
    String error = null;

    try {
      artOrder = serviceAo.updateArtOrder(idNull, delivery, Piecelocation, target, tracker,
          AP_TEST.getIdCode());
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertNull(artOrder);
    assertEquals("User id code cannot be empty!", error); // expected error message for service data
                                                          // validation.
  }

  @Test
  public void testUpdateArtOrderNullLocation() {
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
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.updateArtOrder(id, delivery, Piecelocation, target, tracker,
          AP_TEST.getIdCode());
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artOrder);
    assertEquals("Target Location cannot be empty!", error); // expected error message for service
                                                             // data
    // validation.
  }

  @Test
  public void testUpdateArtOrderNullAddress() {

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
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.updateArtOrder(id, delivery, Piecelocation, target,
          tracker, AP_TEST.getIdCode());
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artOrder);
    assertEquals("Target Address cannot be empty!", error); // expected error message for service
                                                            // data
    // validation.
  }

  @Test
  public void testUpdateArtOrderNullDelivery() {

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
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.updateArtOrder(id, delivery,
          Piecelocation, target, tracker, AP_TEST.getIdCode());
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artOrder);
    assertEquals("Delivery Tracker cannot be empty!", error); // expected error message for service
                                                              // data
  } // validation.

  @Test
  public void updateArtOrderBadTracker() {

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
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.updateArtOrder(id, delivery,
          Piecelocation, target, tracker,
          AP_TEST.getIdCode());
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artOrder);
    assertEquals("Delivery Tracker must contain 12 digits", error); // expected error message for
                                                                    // service data
  }

  @Test
  public void testUpdateArtOrderNullPiece() {

    Boolean delivery = true;
    PieceLocation Piecelocation = PieceLocation.AtGallery;
    String target = "123";
    String tracker = "111122223333";
    String id = "1234";
    String error = null;
    ArtPiece AP_TEST = null;
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.updateArtOrder(id, delivery, Piecelocation, target, tracker,
          AP_TEST.getIdCode());
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artOrder);
    assertEquals("Art Piece cannot be empty!", error); // expected error message for service data
  }

  @Test
  public void testUpdateCreateArtOrderValidTracker() {

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
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.updateArtOrder(AO_KEY, delivery,
          Piecelocation, target, tracker,
          AP_TEST.getIdCode());
    } catch (IllegalArgumentException e) {

    }
    assertNotNull(artOrder);
    assertEquals(artOrder.getDeliveryTracker(), tracker); // expected error message for
                                                          // service data
  }

  @Test
  public void testUpdateCreateArtOrderValidTarget() {

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
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.updateArtOrder(AO_KEY, delivery,
          Piecelocation, target, tracker,
          AP_TEST.getIdCode());
    } catch (IllegalArgumentException e) {

    }
    assertNotNull(artOrder);
    assertEquals(artOrder.getTargetAddress(), target); // expected error message for
                                                       // service data
  }

  @Test
  public void testUpdateCreateArtOrderValidPieceLocation() {

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
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.updateArtOrder(AO_KEY, delivery,
          Piecelocation, target, tracker,
          AP_TEST.getIdCode());
    } catch (IllegalArgumentException e) {

    }
    assertNotNull(artOrder);
    assertEquals(artOrder.getTargetLocation(), Piecelocation); // expected error message for
                                                               // service data
  }

  @Test
  public void testUpdateCreateArtOrderValidDelivery() {

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
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.updateArtOrder(AO_KEY, delivery,
          Piecelocation, target, tracker,
          AP_TEST.getIdCode());
    } catch (IllegalArgumentException e) {

    }
    assertNotNull(artOrder);
    assertEquals(artOrder.getIsDelivered(), delivery); // expected error message for
                                                       // service data
  }

  @Test
  public void testDeleteArtOrder() {

    assertTrue(serviceAo.deleteArtOrder(AO_KEY));
    assertFalse(serviceAo.deleteArtOrder(""));
    // assertFalse(serviceAo.deleteArtOrder(null));

  }

}
